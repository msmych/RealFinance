package finance.expense.total;

import finance.expense.ExpenseCategory;
import finance.expense.ExpenseRepository.AmountCategoryExpenseTotal;
import finance.expense.ExpenseRepository.AmountCurrencyExpenseTotal;
import org.junit.Test;

import static finance.expense.total.TotalUtils.formatTotalCategory;
import static finance.expense.total.TotalUtils.formatTotalCurrency;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TotalUtilsTest {

    private final AmountCurrencyExpenseTotal totalCurrency = mock(AmountCurrencyExpenseTotal.class);
    private final AmountCategoryExpenseTotal totalCategory = mock(AmountCategoryExpenseTotal.class);

    @Test
    public void testFormatTotalEur() {
        when(totalCurrency.getAmount()).thenReturn(1500L);
        when(totalCurrency.getCurrency()).thenReturn("EUR");
        assertEquals("`15.00 €`", formatTotalCurrency(totalCurrency));
    }

    @Test
    public void testFormatTotalHouse() {
        when(totalCategory.getAmount()).thenReturn(10000L);
        when(totalCategory.getCategory()).thenReturn(ExpenseCategory.HOUSE);
        assertEquals("House: `100.00`", formatTotalCategory(totalCategory));
    }
}