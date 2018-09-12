package finance.bot.update;

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
            if (!tryToSaveBotChat(update)) return;
            updateProcessors.stream()
                    .filter(updateProcessor -> tryToCheckIfApplies(updateProcessor, update))
                    .forEach(updateProcessor -> tryToProcessUpdate(updateProcessor, update));
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private boolean tryToSaveBotChat(Update update) {
        try {
            botChatService.saveBotChat(update);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private boolean tryToCheckIfApplies(UpdateProcessor updateProcessor, Update update) {
        try {
            return updateProcessor.appliesTo(update);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private void tryToProcessUpdate(UpdateProcessor updateProcessor, Update update) {
        try {
            updateProcessor.process(update);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
