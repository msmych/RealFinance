package finance.bot.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import finance.bot.user.BotUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static finance.update.UpdateUtils.getChat;

@Service
public class BotChatService {

    private final BotChatRepository botChatRepository;
    private final BotUserService botUserService;

    public BotChatService(BotChatRepository botChatRepository, BotUserService botUserService) {
        this.botChatRepository = botChatRepository;
        this.botUserService = botUserService;
    }

    public BotChat saveBotChat(Update update) {
        Chat chat = getChat(update);
        BotChat botChat = getBotChatToSave(chat.id());
        botChat.firstName = chat.firstName();
        botChat.lastName = chat.lastName();
        botChat.username = chat.username();
        botChat.users.add(botUserService.saveBotUser(update));
        return botChatRepository.save(botChat);
    }

    private BotChat getBotChatToSave(long chatId) {
        Optional<BotChat> optionalBotChat = botChatRepository.findById(chatId);
        BotChat botChat;
        if (optionalBotChat.isPresent()) {
            botChat = optionalBotChat.get();
        } else {
            botChat = new BotChat();
            botChat.id = chatId;
        }
        return botChat;
    }

    public Optional<BotChat> findById(long id) {
        return botChatRepository.findById(id);
    }
}
