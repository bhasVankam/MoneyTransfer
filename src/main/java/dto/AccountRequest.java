package dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.BalanceUtils;
import validations.Rules;
import validations.Validator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest
{
    private BigDecimal balance;
    private String name;

    public static List<Validator<AccountRequest>> RULES = Rules.<AccountRequest>newInstance()
        .addRule(req -> Objects.isNull(req.balance), "Balance cannot be blank")
        .addRule(req -> BalanceUtils.isNegative(req.balance), "Balance cannot be less than 0 ")
        .addRule(req -> Objects.isNull(req.name), "Name cannot be blank")
        .getRules();
}
