package finance.expense.total.selector;

import finance.expense.ExpenseRepository;
import finance.expense.total.AmountCategoryExpenseTotal;
import finance.expense.total.AmountCurrencyExpenseTotal;

import java.util.List;

public class AllExpenseTotalsSelector implements ExpenseTotalsSelector {

    private final ExpenseRepository expenseRepository;
    private final long botChatId;
    private final List<AmountCurrencyExpenseTotal> expenseTotalsCurrency;

    public AllExpenseTotalsSelector(ExpenseRepository expenseRepository, long botChatId) {
        this.expenseRepository = expenseRepository;
        this.botChatId = botChatId;
        expenseTotalsCurrency = expenseRepository.totalCurrencyByBotChatId(botChatId);
    }

    @Override
    public List<AmountCurrencyExpenseTotal> getCurrencyExpenseTotals() {
        return expenseTotalsCurrency;
    }

    @Override
    public List<AmountCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency) {
        return expenseRepository.totalCategoryByBotChatIdAndCurrency(botChatId, currency);
    }
}
