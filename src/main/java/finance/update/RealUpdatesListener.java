package finance.update;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import finance.bot.chat.BotChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealUpdatesListener implements UpdatesListener {

    private final Logger logger = LogManager.getLogger(RealUpdatesListener.class);

    private final List<UpdateProcessor> updateProcessors;
    private final BotChatService botChatService;

    public RealUpdatesListener(List<UpdateProcessor> updateProcessors, BotChatService botChatService) {
        this.updateProcessors = updateProcessors;
        this.botChatService = botChatService;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info(update);
            botChatService.saveBotChat(update);
            updateProcessors.stream()
                    .filter(updateProcessor -> updateProcessor.appliesTo(update))
                    .forEach(updateProcessor -> processUpdate(updateProcessor, update));
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processUpdate(UpdateProcessor updateProcessor, Update update) {
        try {
            updateProcessor.process(update);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
