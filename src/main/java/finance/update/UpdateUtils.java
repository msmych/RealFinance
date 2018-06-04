package finance.update;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

public final class UpdateUtils {

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
}
