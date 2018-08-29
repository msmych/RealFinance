package finance.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetMeResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.LogManager.getLogger;

@Service
public class Bot {

    public final static String CLEAR_ALL_DATA = "clear_all";
    public final static InlineKeyboardButton CLEAR_ALL_BUTTON =
            new InlineKeyboardButton("All chat").callbackData(CLEAR_ALL_DATA);
    public final static InlineKeyboardMarkup CLEAR_MARKUP = new InlineKeyboardMarkup(
            new InlineKeyboardButton[] { CLEAR_ALL_BUTTON });

    private final Logger logger = getLogger();

    private final TelegramBot telegramBot;
    private final User user;

    Bot(@Value("${token}") String token,
        UpdatesListener updatesListener) {
        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(updatesListener);
        GetMeResponse getMeResponse = telegramBot.execute(new GetMe());
        logger.info(getMeResponse);
        user = getMeResponse.user();
    }

    public <T extends BaseRequest, R extends BaseResponse> R execute(BaseRequest<T, R> request) {
        return telegramBot.execute(request);
    }

    public User getUser() {
        return user;
    }
}
