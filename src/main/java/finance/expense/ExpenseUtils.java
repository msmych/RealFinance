package finance.expense;

import com.pengrad.telegrambot.model.Update;

import java.util.Optional;

import static finance.expense.CurrencyUtils.isCurrency;
import static finance.expense.ExpenseCategory.getByEmoji;
import static finance.update.UpdateUtils.getText;

public class ExpenseUtils {

    public static boolean isExpense(Update update) {
        Optional<String> optionalText = getText(update);
        if (!optionalText.isPresent()) return false;
        String[] commandWithArguments = optionalText.get().split(" ");
        String command = commandWithArguments[0];
        String amountRegex = "[/][0-9]+([/.][0-9]{2})?";
        if (!command.matches(amountRegex)) return false;
        if (commandWithArguments.length == 1) return true;
        String arg1 = commandWithArguments[1];
        if (commandWithArguments.length == 2) {
            return isCurrency(arg1) || getByEmoji(arg1).isPresent();
        }
        String arg2 = commandWithArguments[2];
        return getByEmoji(arg2).isPresent() && !getByEmoji(arg1).isPresent()
                && commandWithArguments.length == 3;
    }

    public static int parseAmount(String text) {
        String[] amountParts = text
                .split(" ")[0]
                .substring(1)
                .split("[/.]");
        String amountText = amountParts.length == 1
                ? amountParts[0] + "00"
                : amountParts[0] + amountParts[1];
        return Integer.valueOf(amountText);
    }

    public static String formatAmount(long amount) {
        String amountString = Long.toString(amount);
        if (amountString.length() < 3) amountString = "0" + amountString;
        if (amountString.length() < 3) amountString = "0" + amountString;
        return new StringBuilder(amountString)
                    .insert(amountString.length() - 2, ".")
                    .toString();
    }
}
