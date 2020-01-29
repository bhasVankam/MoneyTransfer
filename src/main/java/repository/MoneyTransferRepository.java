package repository;

import com.bank.jooq.Tables;
import exception.NotValidAccount;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import model.Status;
import model.TransferDetails;
import org.jooq.DSLContext;

@AllArgsConstructor
public class MoneyTransferRepository
{
    private final DSLContext dslContext;


    public void transaction(final TransferDetails transferDetails)
    {
        dslContext.insertInto(Tables.TRANSFERDETAILS)
            .set(Tables.TRANSFERDETAILS.ID, transferDetails.getId())
            .set(Tables.TRANSFERDETAILS.AMOUNT, transferDetails.getAmount())
            .set(Tables.TRANSFERDETAILS.CREATED_ON, transferDetails.getCreatedOn())
            .set(Tables.TRANSFERDETAILS.SENDER_ID, transferDetails.getSender_id())
            .set(Tables.TRANSFERDETAILS.RECEIVER_ID, transferDetails.getReceiver_id())
            .set(Tables.TRANSFERDETAILS.STATUS, transferDetails.getStatus().toString())
            .execute();

    }


    public void updateStatus(final Status status, final UUID id)
    {
        int records = dslContext.update(Tables.TRANSFERDETAILS)
            .set(Tables.TRANSFERDETAILS.STATUS, status.toString())
            .where(Tables.TRANSFERDETAILS.ID.eq(id))
            .execute();

        if (records == 0)
        {
            throw new NotValidAccount(String.format("Account %s is not present" + id));
        }
    }


    public TransferDetails findById(final UUID id)
    {
        return dslContext.selectFrom(Tables.TRANSFERDETAILS)
            .where(Tables.TRANSFERDETAILS.ID.eq(id))
            .fetchOptional(record -> TransferDetails.builder()
                .amount(record.getAmount())
                .createdOn(record.getCreatedOn())
                .id(record.getId())
                .receiver_id(record.getReceiverId())
                .sender_id(record.getSenderId())
                .status(Status.valueOf(record.getStatus()))
                .build()
            )
            .orElseThrow(() -> new NotValidAccount(String.format("Transfer %s doesn't exists to update transfer", id)));
    }


    public List<TransferDetails> findAllTransfersBySource(UUID accountId)
    {
        return dslContext.selectFrom(Tables.TRANSFERDETAILS)
            .where(Tables.TRANSFERDETAILS.SENDER_ID.eq(accountId))
            .fetch(record -> TransferDetails.builder()
                .amount(record.getAmount())
                .createdOn(record.getCreatedOn())
                .id(record.getId())
                .receiver_id(record.getReceiverId())
                .sender_id(record.getSenderId())
                .status(Status.valueOf(record.getStatus()))
                .build()
            );
    }
}
