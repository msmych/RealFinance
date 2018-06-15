package finance.expense.total;

import finance.expense.ExpenseCategory;

public interface ExpenseTotalCategory {
    long getAmount();
    ExpenseCategory getCategory();
}
