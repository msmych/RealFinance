package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertEquals;
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
        int[] acts = {0};
        boolean applies = checkIfAppliesAndProcess(acts, "15");
        assertIfAppliesAndProcess(applies, 1500, acts);
    }

    @Test
    public void testMessageTextDecimal() {
        int[] acts = {0};
        boolean applies = checkIfAppliesAndProcess(acts, "3.50");
        assertIfAppliesAndProcess(applies, 350, acts);
    }

    @Test
    public void testMessageTextLessThatOne() {
        int[] acts = {0};
        boolean applies = checkIfAppliesAndProcess(acts, "0.12");
        assertIfAppliesAndProcess(applies, 12, acts);
    }

    private void assertIfAppliesAndProcess(boolean applies, int expectedAmount, int[] acts) {
        assertTrue(applies);
        assertEquals(expectedAmount, acts[0]);
    }

    private boolean checkIfAppliesAndProcess(int[] acts, String amount) {
        setMessageTextAmount(amount);
        setExpenseServiceAnswer(acts);
        boolean applies = expenseProcessor.appliesTo(update);
        expenseProcessor.process(update);
        return applies;
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
        when(message.text()).thenReturn(amount);
    }
}