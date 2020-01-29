package dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.BalanceUtils;
import validations.Rules;
import validations.Validator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetailsRequest
{

    private UUID receiver_id;
    private UUID sender_id;
    private BigDecimal amount;

    public static List<Validator<TransferDetailsRequest>> RULES = Rules.<TransferDetailsRequest>newInstance()
        .addRule(req -> Objects.isNull(req.receiver_id), "Receiver Account Id should be present")
        .addRule(req -> Objects.isNull(req.sender_id), "Sender Account Id should be present")
        .addRule(req -> Objects.equals(req.receiver_id, req.sender_id), "Sender and receiver cannot be same")
        .addRule(req -> Objects.isNull(req.amount), "Amount cannot be null")
        .addRule(req -> BalanceUtils.isZero(req.amount), "Amount cannot be zero")
        .addRule(req -> BalanceUtils.isNegative(req.amount), "Amount cannot be negative")
        .getRules();
}
