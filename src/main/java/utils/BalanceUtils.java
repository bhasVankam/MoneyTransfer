package utils;

import java.math.BigDecimal;

public class BalanceUtils
{

    public static boolean isNegative(BigDecimal value)
    {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }


    public static boolean isZero(BigDecimal value)
    {
        return BigDecimal.ZERO.equals(value);
    }
}
