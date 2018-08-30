package finance.expense.total;

import java.util.Currency;

import static finance.expense.ExpenseUtils.formatAmount;

public class TotalUtils {

    public static String formatTotalCurrency(CurrencyExpenseTotal currencyExpenseTotal) {
        return "`"
                + formatAmount(currencyExpenseTotal.getAmount())
                + " " + Currency.getInstance(currencyExpenseTotal.getCurrency()).getSymbol()
                + "`";
    }

    public static String formatTotalCategory(CurrencyCategoryExpenseTotal currencyCategoryExpenseTotal) {
        return currencyCategoryExpenseTotal.getCategory().getName() + ": "
                + "`" + formatAmount(currencyCategoryExpenseTotal.getAmount()) + "`";
    }
}
