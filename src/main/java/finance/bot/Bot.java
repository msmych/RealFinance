package finance.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.response.GetMeResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class Bot extends TelegramBot {

    private final Logger logger = LogManager.getLogger(Bot.class);

    private final User user;

    @Autowired private UpdatesListener updatesListener;

    Bot(@Value("${token}") String token) {
        super(token);
        GetMeResponse getMeResponse = execute(new GetMe());
        logger.info(getMeResponse);
        user = getMeResponse.user();
    }

    @PostConstruct public void init() {
        setUpdatesListener(updatesListener);
    }

    public User getUser() { return user; }
}
