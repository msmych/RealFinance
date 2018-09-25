package finance.expense.total;

import java.util.Currency;

import static finance.expense.ExpenseUtils.formatAmount;

public class TotalUtils {

    public static String formatTotalCurrency(AmountCurrencyExpenseTotal amountCurrencyExpenseTotal) {
        return "`"
                + formatAmount(amountCurrencyExpenseTotal.getAmount())
                + " " + Currency.getInstance(amountCurrencyExpenseTotal.getCurrency()).getSymbol()
                + "`";
    }

    public static String formatTotalCategory(AmountCategoryExpenseTotal amountCategoryExpenseTotal) {
        return amountCategoryExpenseTotal.getCategory().getName() + ": "
                + "`" + formatAmount(amountCategoryExpenseTotal.getAmount()) + "`";
    }
}
