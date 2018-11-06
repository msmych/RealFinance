package finance.bot.chat;

import com.pengrad.telegrambot.model.Update;
import finance.update.pre_processor.UpdatePreProcessor;
import org.springframework.stereotype.Component;

@Component
public class BotChatSavingUpdatePreProcessor implements UpdatePreProcessor {

    private final BotChatService botChatService;

    public BotChatSavingUpdatePreProcessor(BotChatService botChatService) {
        this.botChatService = botChatService;
    }

    @Override
    public void preProcess(Update update) {
        botChatService.saveBotChat(update);
    }
}
