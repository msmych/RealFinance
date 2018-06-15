package finance.expense;

import java.util.Arrays;
import java.util.Optional;

public enum ExpenseCategory {

    ANY("Any"),
    HOUSE("House", "\uD83C\uDFE0"),
    FOOD("Food", "\uD83C\uDF5E"),
    HEALTH("Health", "\uD83D\uDC8A"),
    SPORT("Sport", "\uD83C\uDFCA"),
    FUN("Fun", "\uD83C\uDF89"),
    TRAVEL("Travel", "✈️");

    private final String name;
    private final String[] emojis;

    ExpenseCategory(String name, String... emojis) {
        this.name = name;
        this.emojis = emojis;
    }

    static Optional<ExpenseCategory> getByEmoji(String emoji) {
        return Arrays.stream(ExpenseCategory.values())
                .filter(expenseCategory -> Arrays.stream(expenseCategory.emojis)
                        .anyMatch(expenseCategoryEmoji -> expenseCategoryEmoji.equals(emoji)))
                .findFirst();
    }

    public String getName() {
        return name;
    }
}
