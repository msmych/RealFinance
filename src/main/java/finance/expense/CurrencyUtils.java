package finance.expense;

import java.util.Currency;

public class CurrencyUtils {

    public static boolean isCurrency(String code) {
        try {
            Currency.getInstance(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
