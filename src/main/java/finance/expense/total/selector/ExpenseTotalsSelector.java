package finance.expense.total.selector;

import finance.expense.total.CurrencyCategoryExpenseTotal;
import finance.expense.total.CurrencyExpenseTotal;

import java.util.List;

public interface ExpenseTotalsSelector {
    List<CurrencyExpenseTotal> getCurrencyExpenseTotals();
    List<CurrencyCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency);
}
