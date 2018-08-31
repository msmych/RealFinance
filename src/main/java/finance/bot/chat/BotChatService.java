package finance.bot.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import finance.bot.chat.BotChat.ReportType;
import finance.bot.user.BotUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static finance.bot.chat.BotChat.ReportType.NONE;
import static finance.bot.update.UpdateUtils.getChat;

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
        botChat.title = chat.title();
        botChat.users.add(botUserService.saveBotUser(update));
        return botChatRepository.save(botChat);
    }

    private BotChat getBotChatToSave(long botChatId) {
        Optional<BotChat> optionalBotChat = botChatRepository.findById(botChatId);
        BotChat botChat;
        if (optionalBotChat.isPresent()) {
            botChat = optionalBotChat.get();
        } else {
            botChat = new BotChat();
            botChat.id = botChatId;
        }
        return botChat;
    }

    public Optional<BotChat> findById(long id) {
        return botChatRepository.findById(id);
    }

    public List<Long> getBotChatIdsForMonthlyReport() {
        return botChatRepository.findIdsByMonthlyReport();
    }

    public ReportType getBotChatReportType(long botChatId) {
        return botChatRepository.findReportTypeByBotChatId(botChatId)
                .orElse(NONE);
    }

    public void updateReportType(long botChatId, ReportType reportType) {
        botChatRepository.findById(botChatId)
                .ifPresent(botChat -> {
                    botChat.reportType = reportType;
                    botChatRepository.save(botChat);
                });
    }
}
