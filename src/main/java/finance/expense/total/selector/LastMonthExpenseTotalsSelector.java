package finance.expense.total.selector;

import finance.expense.ExpenseRepository;
import finance.expense.total.CurrencyCategoryExpenseTotal;
import finance.expense.total.CurrencyExpenseTotal;

import java.util.Date;
import java.util.List;

import static finance.DateUtils.getLastMonthFirstDay;
import static finance.DateUtils.getThisMonthFirstDay;

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
