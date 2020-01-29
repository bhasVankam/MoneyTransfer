package validations;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rules<T>
{

    List<Validator<T>> rules = new LinkedList<>();


    public Rules<T> addRule(Predicate<T> rule, String message)
    {
        Validator<T> validationRule = new Validator<>(rule, message);
        rules.add(validationRule);
        return this;
    }


    public static <T> Rules<T> newInstance()
    {
        return new Rules<>();
    }
}
