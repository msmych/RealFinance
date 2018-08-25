package finance.expense;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Arrays.asList;

public enum ExpenseCategory {

    ANY("Any", "☕️"),
    HOUSE("House", "\uD83C\uDFE0"),
    FOOD("Food", "\uD83C\uDF5E", "\uD83C\uDF55", "\uD83C\uDF4C"),
    DRESS("Dress", "\uD83D\uDC54", "\uD83D\uDC60", "\uD83D\uDC56"),
    HEALTH("Health", "\uD83D\uDC8A", "\uD83D\uDC89", "\uD83C\uDF21"),
    EDUCATION("Education", "\uD83C\uDF93", "\uD83D\uDCDA"),
    SPORT("Sport", "⚽️", "\uD83C\uDFF8"),
    FUN("Fun", "\uD83C\uDF89", "\uD83C\uDF7A", "\uD83C\uDF81"),
    TRAVEL("Travel", "\uD83D\uDE95", "\uD83D\uDE82", "✈️");

    private final String name;
    private final String[] emojis;

    ExpenseCategory(String name, String... emojis) {
        this.name = name;
        this.emojis = emojis;
    }

    static Optional<ExpenseCategory> getByEmoji(String emoji) {
        return Arrays.stream(ExpenseCategory.values())
                .filter(expenseCategory -> asList(expenseCategory.emojis).contains(emoji))
                .findFirst();
    }

    public String getName() {
        return name;
    }
}
