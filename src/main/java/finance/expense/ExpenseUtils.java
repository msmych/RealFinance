package finance.expense;

public final class ExpenseUtils {

    public static int parseAmount(String text) {
        String[] amountParts = text
                .substring(1)
                .split("\\.");
        String amountText = amountParts.length == 1
                ? amountParts[0] + "00"
                : amountParts[0] + amountParts[1];
        return Integer.valueOf(amountText);
    }

    public static String formatAmount(long amount) {
        String amountString = Long.toString(amount);
        if (amountString.length() < 3) amountString = "0" + amountString;
        if (amountString.length() < 3) amountString = "0" + amountString;
        return new StringBuilder(amountString)
                    .insert(amountString.length() - 2, ".")
                    .toString();
    }
}
