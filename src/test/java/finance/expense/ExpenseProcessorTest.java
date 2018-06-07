package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import finance.bot.Bot;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseProcessorTest {

    private final String COMMAND_EXPENSE = "/expense ";
    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final User user = mock(User.class);
    private final ExpenseService expenseService = mock(ExpenseService.class);
    private final ExpenseProcessor expenseProcessor = new ExpenseProcessor(expenseService);

    @Test
    public void testMessageTextCommandExpenseInt() {
        int[] acts = {0};
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(COMMAND_EXPENSE + "15");
        boolean applies = expenseProcessor.appliesTo(update);
        checkProcessAnswer(acts, "15");
        assertTrue(applies);
        assertEquals(1500, acts[0]);
    }

    @Test
    public void testMessageTextCommandExpenseAtBotUsernameInt() {
        int[] acts = {0};
        when(update.message()).thenReturn(message);
        Bot.user = user;
        when(user.username()).thenReturn("Bot");
        when(message.text()).thenReturn("/expense@Bot 100");
        boolean applies = expenseProcessor.appliesTo(update);
        when(expenseService.save(ArgumentMatchers.isA(Update.class), ArgumentMatchers.anyInt()))
                .then(invocationOnMock -> {
                    acts[0] = invocationOnMock.getArgument(1);
                    return new Expense();
                });
        expenseProcessor.process(update);
        assertTrue(applies);
        assertEquals(10000, acts[0]);
    }

    @Test
    public void testMessageTextDecimal() {
        int[] acts = {0};
        checkProcessAnswer(acts, "3.50");
        assertEquals(350, acts[0]);
    }

    @Test
    public void testMessageTextLessThatOne() {
        int[] acts = {0};
        checkProcessAnswer(acts, "0.12");
        assertEquals(12, acts[0]);
    }

    private void checkProcessAnswer(int[] acts, String amount) {
        setMessageTextAmount(amount);
        setExpenseServiceAnswer(acts);
        expenseProcessor.process(update);
    }

    @Test
    public void testNoMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(expenseProcessor.appliesTo(update));
    }

    @Test
    public void testNoMessageText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(null);
        assertFalse(expenseProcessor.appliesTo(update));
    }

    @Test
    public void testWrongMessageText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("WRONG");
        assertFalse(expenseProcessor.appliesTo(update));
    }

    private void setExpenseServiceAnswer(int[] acts) {
        when(expenseService.save(ArgumentMatchers.isA(Update.class), ArgumentMatchers.anyInt()))
                .then(invocationOnMock -> {
                    acts[0] = invocationOnMock.getArgument(1);
                    return new Expense();
                });
    }

    private void setMessageTextAmount(String amount) {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(COMMAND_EXPENSE + amount);
    }
}