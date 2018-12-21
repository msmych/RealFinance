package finance.expense.total.selector;

import finance.expense.ExpenseRepository;

import java.util.Date;
import java.util.List;

public class FromDateMyExpenseTotalSelector implements ExpenseTotalsSelector {

    private final ExpenseRepository expenseRepository;
    private final long botChatId;
    private final int botUserId;
    private final Date from;
    private final Date to = new Date();

    public FromDateMyExpenseTotalSelector(ExpenseRepository expenseRepository,
                                          long botChatId,
                                          int botUserId,
                                          Date from) {
        this.expenseRepository = expenseRepository;
        this.botChatId = botChatId;
        this.botUserId = botUserId;
        this.from = from;
    }

    @Override
    public List<ExpenseRepository.AmountCurrencyExpenseTotal> getCurrencyExpenseTotals() {
        return expenseRepository.totalCurrencyByBotChatIdAndBotUserIdPeriod(botChatId, botUserId, from, to);
    }

    @Override
    public List<ExpenseRepository.AmountCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency) {
        return expenseRepository
                .totalCategoryByBotChatIdAndBotUserIdAndCurrencyPeriod(botChatId, botUserId, currency, from, to);
    }
}
