package finance.expense.total.selector;

import finance.expense.ExpenseRepository;
import finance.expense.total.AmountCategoryExpenseTotal;
import finance.expense.total.AmountCurrencyExpenseTotal;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class LastMonthExpenseTotalsSelector implements ExpenseTotalsSelector {

    private final ExpenseRepository expenseRepository;
    private final long botChatId;
    private final Date lastMonthFirstDay;
    private final Date thisMonthFirstDay;

    public LastMonthExpenseTotalsSelector(ExpenseRepository expenseRepository, long botChatId) {
        this.expenseRepository = expenseRepository;
        this.botChatId = botChatId;
        this.lastMonthFirstDay = new DateTime().minusMonths(1).withDayOfMonth(1).withMillisOfDay(0).toDate();
        this.thisMonthFirstDay = new DateTime().withDayOfMonth(1).withMillisOfDay(0).toDate();
    }

    @Override
    public List<AmountCurrencyExpenseTotal> getCurrencyExpenseTotals() {
        return expenseRepository.totalCurrencyByBotChatIdPeriod(botChatId, lastMonthFirstDay, thisMonthFirstDay);
    }

    @Override
    public List<AmountCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency) {
        return expenseRepository
                .totalCategoryByBotChatIdAndCurrencyPeriod(botChatId, currency, lastMonthFirstDay, thisMonthFirstDay);
    }
}
