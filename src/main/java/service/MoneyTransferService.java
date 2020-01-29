package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AccountRequest;
import dto.TransferDetailsRequest;
import exception.NotValidRequestException;
import exception.InsufficientFunds;
import exception.NotValidAccount;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import model.Account;
import model.Status;
import model.TransferDetails;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import repository.AccountRepository;
import repository.MoneyTransferRepository;
import spark.Request;
import spark.Response;

import static utils.RequestValidator.validateRequest;

public class MoneyTransferService extends AppUtil
{

    private AccountRepository accountRepository;
    private MoneyTransferRepository moneyTransferRepository;
    private DSLContext dslContext;


    public MoneyTransferService(DataSource dataSource)
    {
        this.dslContext = DSL.using(dataSource, SQLDialect.H2);
        this.accountRepository = new AccountRepository(dslContext);
        this.moneyTransferRepository = new MoneyTransferRepository(dslContext);
    }

    /*
         This Method is create an Account from a
         valid request

     */


    public String registerAccount(final Request request, final Response response)
    {
        ObjectMapper mapper = new ObjectMapper();
        AccountRequest accountRequest = null;
        try
        {
            accountRequest = mapper.readValue(request.body(), AccountRequest.class);
            validateRequest(accountRequest, AccountRequest.RULES);
            Account account = Account.builder()
                .balance(accountRequest.getBalance())
                .id(UUID.randomUUID())
                .name(accountRequest.getName())
                .build();
            accountRepository.saveAccount(account);

            response.status(201);

            return dataToJson(account);
        }
        catch (Exception ex)
        {
            response.status(400);

            return ex.getMessage();
        }
    }

    /*
        This Method is created to get account details
        for the given Account Id
     */


    public String getAccountById(final String id, final Response response)
    {

        try
        {
            UUID accountId = getUUID(id);
            Account account = accountRepository.findById(accountId);
            response.type("application/json");

            return dataToJson(account);
        }
        catch (NotValidRequestException notValidExp)
        {
            response.status(notValidExp.getStatus());

            return notValidExp.getMessage();
        }
        catch (NotValidAccount ex)
        {
            response.status(ex.getStatus());

            return ex.getMessage();
        }

    }

    /*
        This Method is created to get all transfer details
        made by the sender account id.
     */


    public String getAllById(final String id, final Response response)
    {
        try
        {
            List<TransferDetails> transferDetails = moneyTransferRepository.findAllTransfersBySource(getUUID(id));
            response.status(200);
            response.type("application/json");

            return dataToJson(transferDetails);
        }
        catch (Exception e)
        {
            response.status(400);

            return e.getMessage();
        }

    }

    /*
       This is created to save the transfer details
       between two given accounts
     */


    public String saveTransferDetails(final Request req, final Response res)
    {
        ObjectMapper mapper = new ObjectMapper();
        TransferDetailsRequest detailsRequest = null;
        try
        {
            detailsRequest = mapper.readValue(req.body(), TransferDetailsRequest.class);
            validateRequest(detailsRequest, TransferDetailsRequest.RULES);

            return dataToJson(saveTransfer(detailsRequest));
        }
        catch (InsufficientFunds inEx)
        {
            res.status(inEx.getStatus());

            return inEx.getMessage();
        }
        catch (Exception e)
        {
            res.status(400);

            return e.getMessage();
        }

    }


    private UUID getUUID(String id)
    {
        try
        {
            return UUID.fromString(id);
        }
        catch (Exception ex)
        {
            throw new NotValidRequestException(String.format("Account %s is not found", id));
        }
    }


    private TransferDetails saveTransfer(final TransferDetailsRequest detailsRequest)
    {
        checkValidAccounts(detailsRequest.getReceiver_id(), detailsRequest.getSender_id());
        TransferDetails transferDetails = TransferDetails.builder()
            .amount(detailsRequest.getAmount())
            .createdOn(OffsetDateTime.now())
            .id(UUID.randomUUID())
            .receiver_id(detailsRequest.getReceiver_id())
            .sender_id(detailsRequest.getSender_id())
            .status(Status.OPEN)
            .build();

        moneyTransferRepository.transaction(transferDetails);
        performTransfer(transferDetails);

        return moneyTransferRepository.findById(transferDetails.getId());
    }


    private void performTransfer(final TransferDetails transferDetails)
    {
        try
        {
            accountRepository.performTransfer(transferDetails);

            /*
                Once transaction is done,
                update the status based on the exception.

             */
            moneyTransferRepository.updateStatus(Status.DONE, transferDetails.getId());
        }
        catch (InsufficientFunds ex)
        {
            moneyTransferRepository.updateStatus(Status.DECLINED, transferDetails.getId());
            throw ex;
        }
    }


    private void checkValidAccounts(final UUID... Ids)
    {
        Arrays.stream(Ids).map(id -> accountRepository.findById(id));
    }

}
