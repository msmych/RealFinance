package finance.update;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import java.util.Optional;

public final class UpdateUtils {

    public static boolean isCommand(Update update, String command, String botUsername) {
        Message message = update.message();
        if (message == null) return false;
        String text = message.text();
        if (text == null) return false;
        String slash = "/";
        return text.equals(slash + command)
                || text.equals(slash + command + "@" + botUsername);
    }

    public static Chat getChat(Update update) {
        Message message = update.message();
        if (message != null) return message.chat();
        return update.callbackQuery().message().chat();
    }

    public static User getFrom(Update update) {
        Message message = update.message();
        if (message != null) return message.from();
        return update.callbackQuery().from();
    }

    public static Optional<String> getText(Update update) {
        Message message = update.message();
        if (message == null) return Optional.empty();
        String text = message.text();
        if (text == null) return Optional.empty();
        return Optional.of(text);
    }
}
