package finance.expense;

public final class ExpenseUtils {

    public static String formatAmount(long amount) {
        String amountString = Long.toString(amount);
        if (amountString.length() < 3) amountString = "0" + amountString;
        if (amountString.length() < 3) amountString = "0" + amountString;
        return new StringBuilder(amountString)
                    .insert(amountString.length() - 2, ".")
                    .toString();
    }
}
