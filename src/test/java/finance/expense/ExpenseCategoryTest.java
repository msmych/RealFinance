package finance.expense;

import org.junit.Test;

import static finance.expense.ExpenseCategory.*;
import static org.junit.Assert.*;

public class ExpenseCategoryTest {

    @Test
    public void testGetByEmojis() {
        assertEquals(HOUSE, getByEmoji("\uD83C\uDFE0").get());
        assertEquals(FOOD, getByEmoji("\uD83C\uDF5E").get());
        assertEquals(HEALTH, getByEmoji("\uD83D\uDC8A").get());
        assertEquals(SPORT, getByEmoji("\uD83C\uDFF8").get());
        assertEquals(FUN, getByEmoji("\uD83C\uDF89").get());
        assertEquals(TRAVEL, getByEmoji("✈️").get());
    }

    @Test
    public void testGetByWrongEmoji() {
        assertFalse(getByEmoji("WRONG").isPresent());
    }

}