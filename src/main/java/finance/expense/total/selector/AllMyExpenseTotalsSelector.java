package finance.expense.total.selector;

import finance.expense.ExpenseRepository;
import finance.expense.ExpenseRepository.AmountCurrencyExpenseTotal;

import java.util.List;

public class AllMyExpenseTotalsSelector implements ExpenseTotalsSelector {

    private final ExpenseRepository expenseRepository;
    private final long botChatId;
    private final int botUserId;
    private final List<AmountCurrencyExpenseTotal> expenseTotalsCurrency;

    public AllMyExpenseTotalsSelector(ExpenseRepository expenseRepository, long botChatId, int botUserId) {
        this.expenseRepository = expenseRepository;
        this.botChatId = botChatId;
        this.botUserId = botUserId;
        expenseTotalsCurrency = expenseRepository.totalCurrencyByBotChatIdAndBotUserId(botChatId, botUserId);
    }

    @Override
    public List<AmountCurrencyExpenseTotal> getCurrencyExpenseTotals() {
        return expenseTotalsCurrency;
    }

    @Override
    public List<ExpenseRepository.AmountCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency) {
        return expenseRepository.totalCategoryByBotChatIdAndBotUserId(botChatId, botUserId, currency);
    }
}
