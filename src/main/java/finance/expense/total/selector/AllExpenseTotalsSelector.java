package finance.expense.total.selector;

import finance.expense.ExpenseRepository;
import finance.expense.total.CurrencyCategoryExpenseTotal;
import finance.expense.total.CurrencyExpenseTotal;

import java.util.List;

public class AllExpenseTotalsSelector implements ExpenseTotalsSelector {

    private final ExpenseRepository expenseRepository;
    private final long botChatId;
    private final List<CurrencyExpenseTotal> expenseTotalsCurrency;

    public AllExpenseTotalsSelector(ExpenseRepository expenseRepository, long botChatId) {
        this.expenseRepository = expenseRepository;
        this.botChatId = botChatId;
        expenseTotalsCurrency = expenseRepository.totalCurrencyByBotChatId(botChatId);
    }

    @Override
    public List<CurrencyExpenseTotal> getCurrencyExpenseTotals() {
        return expenseTotalsCurrency;
    }

    @Override
    public List<CurrencyCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency) {
        return expenseRepository.totalCategoryByBotChatIdAndCurrency(botChatId, currency);
    }
}
