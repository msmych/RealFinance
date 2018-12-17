package finance.expense.total.selector;

import finance.expense.ExpenseRepository.AmountCategoryExpenseTotal;
import finance.expense.ExpenseRepository.AmountCurrencyExpenseTotal;

import java.util.List;

public interface ExpenseTotalsSelector {
    List<AmountCurrencyExpenseTotal> getCurrencyExpenseTotals();
    List<AmountCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency);
}
