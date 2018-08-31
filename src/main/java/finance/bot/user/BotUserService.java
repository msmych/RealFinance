package finance.bot.user;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static finance.bot.update.UpdateUtils.getFrom;

@Service
public class BotUserService {

    private final BotUserRepository botUserRepository;

    public BotUserService(BotUserRepository botUserRepository) {
        this.botUserRepository = botUserRepository;
    }

    public BotUser saveBotUser(Update update) {
        User user = getFrom(update);
        BotUser botUser = getBotUserToSave(user.id());
        botUser.firstName = user.firstName();
        botUser.lastName = user.lastName();
        botUser.username = user.username();
        return botUserRepository.save(botUser);
    }

    private BotUser getBotUserToSave(int userId) {
        Optional<BotUser> optionalBotUser = botUserRepository.findById(userId);
        BotUser botUser;
        if (optionalBotUser.isPresent()) {
            botUser = optionalBotUser.get();
        } else {
            botUser = new BotUser();
            botUser.id = userId;
            botUser.userAction = UserAction.NONE;
        }
        return botUser;
    }

    public Optional<BotUser> findById(int id) {
        return botUserRepository.findById(id);
    }

    public BotUser updateDefaultCurrency(int id, String currency) {
        BotUser botUser = botUserRepository.findById(id).get();
        botUser.defaultCurrency = currency.toUpperCase();
        return botUserRepository.save(botUser);
    }
}
