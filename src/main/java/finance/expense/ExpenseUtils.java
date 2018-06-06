package finance.expense;

public final class ExpenseUtils {

    private static final String CODE = "`";

    public static String formatAmount(long amount) {
        String amountString = Long.toString(amount);
        if (amountString.length() < 3) amountString = "0" + amountString;
        if (amountString.length() < 3) amountString = "0" + amountString;
        return CODE
                + new StringBuilder(amountString)
                    .insert(amountString.length() - 2, ".")
                    .toString()
                + CODE;
    }
}
