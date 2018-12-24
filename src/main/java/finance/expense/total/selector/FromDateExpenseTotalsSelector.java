package finance.expense.total.selector;

import finance.expense.ExpenseRepository;
import finance.expense.ExpenseRepository.AmountCategoryExpenseTotal;
import finance.expense.ExpenseRepository.AmountCurrencyExpenseTotal;

import java.util.Date;
import java.util.List;

public class FromDateExpenseTotalsSelector implements ExpenseTotalsSelector {

    private final ExpenseRepository expenseRepository;
    private final long botChatId;
    private final Date from;
    private final Date to = new Date();

    public FromDateExpenseTotalsSelector(ExpenseRepository expenseRepository, long botChatId, Date from) {
        this.expenseRepository = expenseRepository;
        this.botChatId = botChatId;
        this.from = from;
    }

    @Override
    public List<AmountCurrencyExpenseTotal> getCurrencyExpenseTotals() {
        return expenseRepository.totalCurrencyByBotChatIdPeriod(botChatId, from, to);
    }

    @Override
    public List<AmountCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency) {
        return expenseRepository.totalCategoryByBotChatIdAndCurrencyPeriod(botChatId, currency, from, to);
    }
}
