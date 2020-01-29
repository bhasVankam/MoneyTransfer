package repository;

import dto.AccountRequest;
import dto.TransferDetailsRequest;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.Status;
import model.TransferDetails;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

class RepositoryTest extends RepoTest
{

    private AccountRepository accountRepository = new AccountRepository(context);
    private MoneyTransferRepository moneyTransferRepository = new MoneyTransferRepository(context);
    UUID account1;
    UUID account2;


    @BeforeEach
    void setup()
    {
        account1 = UUID.fromString(registerAccount(BigDecimal.valueOf(10), "Bhas"));
        account2 = UUID.fromString(registerAccount(BigDecimal.valueOf(20), "kar"));
    }


    @Test
    void test_thread_safe_case()
    {

        TransferDetailsRequest detailsRequest = new TransferDetailsRequest(account1, account2, BigDecimal.valueOf(5));
        TransferDetailsRequest detailsRequest2 = new TransferDetailsRequest(account2, account1, BigDecimal.valueOf(2));
        TransferDetailsRequest detailsRequest3 = new TransferDetailsRequest(account1, account2, BigDecimal.valueOf(3));

        try
        {
            final ExecutorService executor = Executors.newScheduledThreadPool(3);
            Collection<Callable<TransferDetails>> tasks = new ArrayList<>();
            tasks.add(() -> transferRequest(account1, detailsRequest));
            tasks.add(() -> transferRequest(account2, detailsRequest2));
            tasks.add(() -> transferRequest(account1, detailsRequest3));
            executor.invokeAll(tasks);
        }
        catch (InterruptedException e)
        {
            e.getMessage();
        }

        assertThat(accountRepository.findById(account1).getBalance()).isEqualTo(BigDecimal.valueOf(16));
        assertThat(accountRepository.findById(account2).getBalance()).isEqualTo(BigDecimal.valueOf(14));

    }


    private String registerAccount(BigDecimal balance, String name)
    {
        AccountRequest request = new AccountRequest(balance, name);

        return given()
            .body(request)
            .when()
            .post("/account").prettyPeek()
            .then()
            .statusCode(HttpStatus.CREATED_201)
            .body("id", notNullValue())
            .body("balance", notNullValue())
            .body("name", notNullValue())
            .extract()
            .jsonPath()
            .get("id");

    }


    private TransferDetails transferRequest(UUID accountId, TransferDetailsRequest request)
    {
        return given()
            .body(request)
            .when()
            .post("/account/transfer").prettyPeek()
            .then()
            .statusCode(HttpStatus.OK_200)
            .extract().as(TransferDetails.class);
    }


    @Test
    void save_transfer_details()
    {

        UUID transferId = UUID.randomUUID();

        TransferDetails details = TransferDetails.builder()
            .amount(BigDecimal.TEN)
            .createdOn(OffsetDateTime.now())
            .id(transferId)
            .sender_id(account1)
            .receiver_id(account2)
            .status(Status.OPEN)
            .build();

        moneyTransferRepository.transaction(details);

        assertThat(moneyTransferRepository.findById(transferId).getAmount()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(moneyTransferRepository.findById(transferId).getSender_id()).isEqualTo(account1);
        assertThat(moneyTransferRepository.findById(transferId).getReceiver_id()).isEqualTo(account2);

    }

}
