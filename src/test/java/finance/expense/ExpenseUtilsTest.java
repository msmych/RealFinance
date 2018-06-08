package finance.expense;

import org.junit.Test;

import static finance.expense.ExpenseUtils.formatAmount;
import static finance.expense.ExpenseUtils.parseAmount;
import static org.junit.Assert.assertEquals;

public class ExpenseUtilsTest {

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