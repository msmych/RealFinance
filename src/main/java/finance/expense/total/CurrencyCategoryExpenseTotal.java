package finance.expense.total;

import finance.expense.ExpenseCategory;

public interface CurrencyCategoryExpenseTotal {
    long getAmount();
    ExpenseCategory getCategory();
}
