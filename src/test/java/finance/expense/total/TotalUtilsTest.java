package finance.expense.total;

import org.junit.Test;

import static finance.expense.total.TotalUtils.formatTotal;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TotalUtilsTest {

    private final ExpenseTotal total = mock(ExpenseTotal.class);

    @Test
    public void testFormatTotalEur() {
        when(total.getAmount()).thenReturn(1500L);
        when(total.getCurrency()).thenReturn("EUR");
        assertEquals("`15.00 EUR`", formatTotal(total));
    }
}