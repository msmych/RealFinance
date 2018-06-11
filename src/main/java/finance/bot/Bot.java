package finance.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetMeResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("app.properties")
public class Bot {

    private final Logger logger = LogManager.getLogger();

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
