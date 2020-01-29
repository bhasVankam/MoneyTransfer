package utils;

import exception.NotValidRequestException;
import java.util.List;
import java.util.Optional;
import validations.Validator;

public class RequestValidator
{

    public static <T> void validateRequest(T request, List<Validator<T>> rules)
    {
        Optional<Validator<T>> failedRule = rules.stream()
            .filter(rule -> rule.getPredicate().test(request))
            .findFirst();
        failedRule.ifPresent(rule -> {
            throw new NotValidRequestException(rule.getMessage());
        });
    }
}
