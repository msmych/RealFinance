package finance.expense.total;

import java.util.Currency;

import static finance.expense.ExpenseUtils.formatAmount;

public final class TotalUtils {

    public static String formatTotalCurrency(ExpenseTotalCurrency expenseTotalCurrency) {
        return "`"
                + formatAmount(expenseTotalCurrency.getAmount())
                + " " + Currency.getInstance(expenseTotalCurrency.getCurrency()).getSymbol()
                + "`";
    }

    public static String formatTotalCategory(ExpenseTotalCategory expenseTotalCategory) {
        return expenseTotalCategory.getCategory().getName() + ": "
                + "`" + formatAmount(expenseTotalCategory.getAmount()) + "`";
    }
}
