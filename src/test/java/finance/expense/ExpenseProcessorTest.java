package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.Before;
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

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
    }

    @Test
    public void testAppliesToMessageTextInt() {
        when(message.text()).thenReturn("/15");
        assertTrue(expenseProcessor.appliesTo(update));
    }

    @Test
    public void testMessageTextDecimal() {
        when(message.text()).thenReturn("/3.50");
        assertTrue(expenseProcessor.appliesTo(update));
    }

    @Test
    public void testMessageTextLessThatOne() {
        when(message.text()).thenReturn("/0.12");
        assertTrue(expenseProcessor.appliesTo(update));
    }

    @Test
    public void testNoMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(expenseProcessor.appliesTo(update));
    }

    @Test
    public void testMessageNoText() {
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
        when(message.text()).thenReturn("/7.77");
        when(expenseService.save(ArgumentMatchers.isA(Update.class), ArgumentMatchers.anyInt()))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return new Expense();
                });
        expenseProcessor.process(update);
        assertTrue(acts[0]);
    }

}