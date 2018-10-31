package finance.update;

import com.pengrad.telegrambot.model.*;

import java.util.Optional;

public class UpdateUtils {

    public static Chat getChat(Update update) {
        Message message = update.message();
        if (message != null) return message.chat();
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) return callbackQuery.message().chat();
        return update.editedMessage().chat();
    }

    public static User getFrom(Update update) {
        Message message = update.message();
        if (message != null) return message.from();
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) return callbackQuery.from();
        return update.editedMessage().from();
    }

    public static Optional<Message> getMessage(Update update) {
        Message message = update.message();
        if (message != null) return Optional.of(message);
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) return Optional.of(callbackQuery.message());
        message = update.editedMessage();
        if (message != null) return Optional.of(message);
        return Optional.empty();
    }

    public static Optional<String> getText(Update update) {
        Optional<Message> optionalMessage = getMessage(update);
        if (!optionalMessage.isPresent()) return Optional.empty();
        String text = optionalMessage.get().text();
        if (text == null) return Optional.empty();
        return Optional.of(text);
    }
}
