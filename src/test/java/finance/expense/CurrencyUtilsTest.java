package finance.expense;

import org.junit.Test;

import static finance.expense.CurrencyUtils.isCurrency;
import static org.junit.Assert.*;

public class CurrencyUtilsTest {

    @Test
    public void testUsdIsCurrency() {
        assertTrue(isCurrency("USD"));
    }

    @Test
    public void testLowerCaseUsdIsCurrency() {
        assertTrue(isCurrency("usd"));
    }

    @Test
    public void testWrongIsNotCurrency() {
        assertFalse(isCurrency("WRONG"));
    }

}