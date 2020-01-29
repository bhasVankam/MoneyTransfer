package validations;

import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Validator<T>
{
    private Predicate<T> predicate;
    private String message;

}
