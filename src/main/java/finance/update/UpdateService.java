package finance.update;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.bot.Bot;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateService {

    private final Bot bot;

    public UpdateService(Bot bot) {
        this.bot = bot;
    }

    public boolean isCommand(String command, Update update) {
        Message message = update.message();
        if (message == null) return false;
        String text = message.text();
        return text != null
                && (text.equals("/" + command) || text.equals("/" + command + "@" + bot.getUser().username()));
    }

    public Optional<String> getCallbackQueryData(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) return Optional.empty();
        String data = callbackQuery.data();
        return Optional.ofNullable(data);
    }

    public boolean callbackQueryDataEquals(String data, Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) return false;
        String text = callbackQuery.data();
        return text != null && text.equals(data);
    }
}
