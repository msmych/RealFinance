package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseProcessorTest {

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final ExpenseService expenseService = mock(ExpenseService.class);
    private final ExpenseProcessor expenseProcessor = new ExpenseProcessor(expenseService);

    @Test
    public void testMessageTextInt() {
        setMessageTextAmount("15");
        assertTrue(expenseProcessor.appliesTo(update));
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

    @Test
    public void testProcess() {
        boolean[] acts = {false};
        setMessageTextAmount("15");
        expenseProcessor.appliesTo(update);
        setExpenseServiceAnswer(acts);
        expenseProcessor.process(update);
        assertTrue(acts[0]);
    }

    private void setExpenseServiceAnswer(boolean[] acts) {
        when(expenseService.save(ArgumentMatchers.isA(Update.class), ArgumentMatchers.anyString()))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return new Expense();
                });
    }

    private void setMessageTextAmount(String amount) {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(amount);
    }
}