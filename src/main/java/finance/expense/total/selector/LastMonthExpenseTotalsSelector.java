package finance.expense.total.selector;

import finance.expense.ExpenseRepository;
import finance.expense.total.CurrencyCategoryExpenseTotal;
import finance.expense.total.CurrencyExpenseTotal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Calendar.*;

public class LastMonthExpenseTotalsSelector implements ExpenseTotalsSelector {

    private final ExpenseRepository expenseRepository;
    private final long botChatId;
    private final Date lastMonthFirstDay;
    private final Date thisMonthFirstDay;
    private final List<CurrencyExpenseTotal> expenseTotalsCurrency;

    public LastMonthExpenseTotalsSelector(ExpenseRepository expenseRepository, long botChatId) {
        this.expenseRepository = expenseRepository;
        this.botChatId = botChatId;
        this.lastMonthFirstDay = getLastMonthFirstDay();
        this.thisMonthFirstDay = getThisMonthFirstDay();
        expenseTotalsCurrency =
                expenseRepository.totalCurrencyByBotChatIdPeriod(botChatId, lastMonthFirstDay, thisMonthFirstDay);
    }

    private Date getLastMonthFirstDay() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(getThisMonthFirstDay());
        calendar.add(MONTH, -1);
        return calendar.getTime();
    }

    private Date getThisMonthFirstDay() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(DAY_OF_MONTH, 1);
        calendar.set(HOUR, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }

    @Override
    public List<CurrencyExpenseTotal> getCurrencyExpenseTotals() {
        return expenseTotalsCurrency;
    }

    @Override
    public List<CurrencyCategoryExpenseTotal> getCurrencyCategoryExpenseTotals(String currency) {
        return expenseRepository
                .totalCategoryByBotChatIdAndCurrencyPeriod(botChatId, currency, lastMonthFirstDay, thisMonthFirstDay);
    }
}
