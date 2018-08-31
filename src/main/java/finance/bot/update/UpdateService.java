package finance.bot.update;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.bot.Bot;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    private final Bot bot;

    public UpdateService(@Lazy Bot bot) {
        this.bot = bot;
    }

    public boolean isCommand(Update update, String command) {
        Message message = update.message();
        if (message == null) return false;
        String text = message.text();
        return text != null
                && (text.equals("/" + command) || text.equals("/" + command + "@" + bot.getUser().username()));
    }

    public boolean isCallbackQueryData(Update update, String data) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) return false;
        String text = callbackQuery.data();
        return text != null && text.equals(data);
    }
}
