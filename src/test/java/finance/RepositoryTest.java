package finance;

import finance.bot.chat.BotChat;
import finance.bot.chat.BotChatRepository;
import finance.expense.Expense;
import finance.expense.ExpenseRepository;
import finance.expense.total.AmountCurrencyExpenseTotal;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.junit.Assert.assertEquals;

@Transactional
@DataJpaTest
@RunWith(SpringRunner.class)
public class RepositoryTest {

    long botChatId = 777L;
    Expense lastMonthExpense = new Expense();

    @Autowired BotChatRepository bcr;
    @Autowired ExpenseRepository er;

    @Before
    public void setUp() {
        BotChat botChat = new BotChat();
        botChat.id = botChatId;
        botChat = bcr.save(botChat);

        BotChat anotherBotChat = new BotChat();
        anotherBotChat.id = 0L;
        anotherBotChat = bcr.save(anotherBotChat);

        Expense anotherChatExpense = new Expense();
        anotherChatExpense.botChat = anotherBotChat;
        er.save(anotherChatExpense);

        Expense lastYearExpense = new Expense();
        lastYearExpense.botChat = botChat;
        lastYearExpense.date = getLastYearDate();
        er.save(lastYearExpense);

        lastMonthExpense.botChat = botChat;
        lastMonthExpense.date = getLastMonthDate();
        lastMonthExpense.amount = 100;
        er.save(lastMonthExpense);

        Expense todaysExpense = new Expense();
        todaysExpense.botChat = botChat;
        todaysExpense.date = new Date();
        er.save(todaysExpense);
    }

    @Test
    public void selectingLastMonthsExpenses() {
        List<AmountCurrencyExpenseTotal> amountCurrencyExpenseTotals =
                er.totalCurrencyByBotChatIdPeriod(
                        botChatId,
                        new DateTime().minusMonths(1).withDayOfMonth(1).withMillisOfDay(0).toDate(),
                        new DateTime().withDayOfMonth(1).withMillisOfDay(0).toDate());
        assertEquals(1, amountCurrencyExpenseTotals.size());
        assertEquals(lastMonthExpense.amount, amountCurrencyExpenseTotals.get(0).getAmount());
    }

    private Date getLastYearDate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(YEAR, -1);
        return calendar.getTime();
    }

    private Date getLastMonthDate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(MONTH, -1);
        return calendar.getTime();
    }
}
