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
    public void slash4() {
        when(message.text()).thenReturn("/4");
        assertTrue(isExpense(update));
    }

    @Test
    public void slash999999999999999() {
        when(message.text()).thenReturn("/999999999999999");
        assertFalse(isExpense(update));
    }

    @Test
    public void slash7$77() {
        when(message.text()).thenReturn("/7.77");
        assertTrue(isExpense(update));
    }

    @Test
    public void slash81_USD() {
        when(message.text()).thenReturn("/81 USD");
        assertTrue(isExpense(update));
    }

    @Test
    public void slash57$00_usd() {
        when(message.text()).thenReturn("/57.00 usd");
        assertTrue(isExpense(update));
    }

    @Test
    public void slash445_WRONG() {
        when(message.text()).thenReturn("/445 WRONG");
        assertFalse(isExpense(update));
    }

    @Test
    public void slash31_USD_WRONG() {
        when(message.text()).thenReturn("/31 USD WRONG");
        assertFalse(isExpense(update));
    }

    @Test
    public void slash63$36_Emoji() {
        when(message.text()).thenReturn("/63.36 \uD83C\uDFE0");
        assertTrue(isExpense(update));
    }

    @Test
    public void slash29_rub_Emoji() {
        when(message.text()).thenReturn("/29 rub \uD83C\uDF5E");
        assertTrue(isExpense(update));
    }

    @Test
    public void slash777_Emoji_USD() {
        when(message.text()).thenReturn("/777 \uD83D\uDC8A USD");
        assertFalse(isExpense(update));
    }

    @Test
    public void slash43$90_usd_Emoji_WRONG() {
        when(message.text()).thenReturn("/43.90 usd ✈️ WRONG");
        assertFalse(isExpense(update));
    }

    @Test
    public void slash552_usd_rub() {
        when(message.text()).thenReturn("/552 usd rub");
        assertFalse(isExpense(update));
    }

    @Test
    public void slash0$99_Emoji_Emoji() {
        when(message.text()).thenReturn("/0.99 \uD83C\uDF5E \uD83D\uDC8A");
        assertFalse(isExpense(update));
    }

    @Test
    public void noMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(isExpense(update));
    }

    @Test
    public void noMessageText() {
        when(message.text()).thenReturn(null);
        assertFalse(isExpense(update));
    }

    @Test
    public void slash100() {
        assertEquals(10000, parseAmount("/100"));
    }

    @Test
    public void slash22$22() {
        assertEquals(2222, parseAmount("/22.22"));
    }

    @Test
    public void slash0$99() {
        assertEquals(99, parseAmount("/0.99"));
    }

    @Test
    public void format15$00() {
        assertEquals("15.00", formatAmount(1500L));
    }

    @Test
    public void format0$12() {
        assertEquals("0.12", formatAmount(12L));
    }

    @Test
    public void format0$00() {
        assertEquals("0.00", formatAmount(0L));
    }
}