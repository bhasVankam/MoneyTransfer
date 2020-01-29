package repository;

import com.bank.jooq.Tables;
import exception.InsufficientFunds;
import exception.NotValidAccount;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.Account;
import model.TransferDetails;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import utils.BalanceUtils;

import static java.util.Arrays.asList;

public class AccountRepository
{

    private DSLContext dslContext;


    public AccountRepository(DSLContext dslContext)
    {
        this.dslContext = dslContext;
    }


    public void saveAccount(final Account account)
    {
        dslContext.insertInto(Tables.ACCOUNT)
            .set(Tables.ACCOUNT.ID, account.getId())
            .set(Tables.ACCOUNT.NAME, account.getName())
            .set(Tables.ACCOUNT.BALANCE, account.getBalance())
            .execute();

        System.out.println(String.format("The Account is Registered with %s id", account.getId()));
    }


    public Account findById(final UUID id)
    {

        System.out.println(String.format("The Account details of given %s id are displayed", id));

        return dslContext.selectFrom(Tables.ACCOUNT)
            .where(Tables.ACCOUNT.ID.eq(id))
            .fetchOptional(record -> Account.builder()
                .id(record.getId())
                .balance(record.getBalance())
                .name(record.getName())
                .build()
            )
            .orElseThrow(() -> new NotValidAccount(String.format("Account %s is not valid", id)));

    }

    /* To make the transaction thread Safe we need to perform locking on data base
       Even though it takes bit more time, We perform pessimistic locking as optimistic
       locking may some time cause issue while performing commit.

     */


    public void performTransfer(final TransferDetails transferDetails)
    {

        dslContext.transaction(configuration -> {
            DSLContext context = DSL.using(configuration);

            Map<UUID, Account> lockedAccounts = context.selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.ID.in(asList(transferDetails.getReceiver_id(), transferDetails.getSender_id()))).forUpdate()
                .fetch(record -> Account.builder()
                    .id(record.getId())
                    .balance(record.getBalance())
                    .name(record.getName())
                    .build()
                )
                .stream()
                .collect(Collectors.toMap(Account::getId, Function.identity()));

            updateAccount(
                context,
                lockedAccounts.get(transferDetails.getSender_id()),
                transferDetails.getAmount().negate()
            );
            updateAccount(
                context,
                lockedAccounts.get(transferDetails.getReceiver_id()),
                transferDetails.getAmount()
            );
        });

    }


    private void updateAccount(final DSLContext context, final Account account, BigDecimal amount)
    {
        BigDecimal balanceAfterTransfer = account.getBalance().add(amount);
        if (BalanceUtils.isNegative(balanceAfterTransfer))
        {
            throw new InsufficientFunds(String.format("Account %s dont have funds", account.getId()));
        }
        account.setBalance(balanceAfterTransfer);
        context.update(Tables.ACCOUNT)
            .set(Tables.ACCOUNT.BALANCE, account.getBalance())
            .where(Tables.ACCOUNT.ID.eq(account.getId()))
            .execute();
    }


}
