package finance.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.GetMeResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("app.properties")
public class Bot {

    private final Logger logger = LogManager.getLogger(Bot.class);

    private final String token;
    private final UpdatesListener updatesListener;

    public static User user;

    public Bot(@Value("${token}") String token,
               UpdatesListener updatesListener) {
        this.token = token;
        this.updatesListener = updatesListener;
    }

    @Bean
    TelegramBot telegramBot() {
        TelegramBot telegramBot = new TelegramBot(token);
        setBotUser(telegramBot);
        telegramBot.setUpdatesListener(updatesListener);
        return telegramBot;
    }

    private void setBotUser(TelegramBot telegramBot) {
        GetMeResponse getMeResponse = telegramBot.execute(new GetMe());
        logger.info(getMeResponse);
        user = getMeResponse.user();
    }
}
