package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.Before;
import org.junit.Test;

import static finance.expense.ExpenseUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseUtilsTest {

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
    }

    @Test
    public void testMessageTextCommandExpense() {
        when(message.text()).thenReturn("/4");
        assertTrue(isExpense(update));
    }

    @Test
    public void testMessageTextCommandDecimalExpense() {
        when(message.text()).thenReturn("/7.77");
        assertTrue(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCurrency() {
        when(message.text()).thenReturn("/81 USD");
        assertTrue(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseLowerCaseCurrency() {
        when(message.text()).thenReturn("/57.00 usd");
        assertTrue(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseWrongArg1() {
        when(message.text()).thenReturn("/445 WRONG");
        assertFalse(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCurrencyWrongArg2() {
        when(message.text()).thenReturn("/31 USD WRONG");
        assertFalse(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCategory() {
        when(message.text()).thenReturn("/63.36 \uD83C\uDFE0");
        assertTrue(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCurrencyCategory() {
        when(message.text()).thenReturn("/29 rub \uD83C\uDF5E");
        assertTrue(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCategoryCurrency() {
        when(message.text()).thenReturn("/777 \uD83D\uDC8A USD");
        assertFalse(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCurrencyWrongArg3() {
        when(message.text()).thenReturn("/43.90 usd ✈️ WRONG");
        assertFalse(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCurrencyCurrency() {
        when(message.text()).thenReturn("/552 usd rub");
        assertFalse(isExpense(update));
    }

    @Test
    public void testMessageTextCommandExpenseCategoryCategory() {
        when(message.text()).thenReturn("/0.99 \uD83C\uDF5E \uD83D\uDC8A");
        assertFalse(isExpense(update));
    }

    @Test
    public void testNoMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(isExpense(update));
    }

    @Test
    public void testMessageNoText() {
        when(message.text()).thenReturn(null);
        assertFalse(isExpense(update));
    }

    @Test
    public void testParseIntAmount() {
        assertEquals(10000, parseAmount("/100"));
    }

    @Test
    public void testParseDecimalAmount() {
        assertEquals(2222, parseAmount("/22.22"));
    }

    @Test
    public void testParseAmountLessThanOne() {
        assertEquals(99, parseAmount("/0.99"));
    }

    @Test
    public void testAmountLong() {
        assertEquals("15.00", formatAmount(1500L));
    }

    @Test
    public void testAmountLessThanOne() {
        assertEquals("0.12", formatAmount(12L));
    }

    @Test
    public void testAmountZero() {
        assertEquals("0.00", formatAmount(0L));
    }
}