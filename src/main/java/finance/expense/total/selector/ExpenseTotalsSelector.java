package finance.expense.total.selector;

import finance.expense.total.AmountCategoryExpenseTotal;
import finance.expense.total.AmountCurrencyExpenseTotal;

import java.util.List;

public interface ExpenseTotalsSelector {
    List<AmountCurrencyExpenseTotal> getCurrencyExpenseTotals();
    List<AmountCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency);
}
