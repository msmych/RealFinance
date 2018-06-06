package finance.expense.total;

import java.util.Currency;

import static finance.expense.ExpenseUtils.formatAmount;

public final class TotalUtils {

    public static String formatTotal(ExpenseTotal expenseTotal) {
        return "`"
                + formatAmount(expenseTotal.getAmount())
                + " " + Currency.getInstance(expenseTotal.getCurrency()).getSymbol()
                + "`";
    }
}
