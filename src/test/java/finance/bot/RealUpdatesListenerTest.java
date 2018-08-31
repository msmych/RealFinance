package finance.bot;

import com.pengrad.telegrambot.model.Update;
import finance.bot.chat.BotChat;
import finance.bot.chat.BotChatService;
import finance.bot.update.RealUpdatesListener;
import finance.bot.update.UpdateProcessor;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RealUpdatesListenerTest {

    private final Update update = mock(Update.class);
    private final BotChatService botChatService = mock(BotChatService.class);

    @Test
    public void testCheckedAllUpdatesOnAllProcessors() {
        boolean[] acts = {false};
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor());
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updateProcessors, botChatService);
        List<Update> updates = Arrays.asList(update, update, update, update);
        when(botChatService.saveBotChat(ArgumentMatchers.isA(Update.class)))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return new BotChat();
                });
        realUpdatesListener.process(updates);
        updateProcessors.forEach(updateProcessor ->
                assertEquals(updates.size(), ((NotAppliedUpdateProcessor) updateProcessor).checkedCount));
        assertTrue(acts[0]);
    }

    @Test
    public void testAllAppliedProcessed() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new AppliedUpdateProcessor(), new AppliedUpdateProcessor());
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updateProcessors, botChatService);
        List<Update> updates = Arrays.asList(update, update, update);
        realUpdatesListener.process(updates);
        updateProcessors.forEach(updateProcessor ->
                assertEquals(updates.size(), ((AppliedUpdateProcessor) updateProcessor).processedCount));
    }

    @Test
    public void testNoneNotAppliedProcessed() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor());
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updateProcessors, botChatService);
        List<Update> updates = Arrays.asList(update, update, update);
        realUpdatesListener.process(updates);
        updateProcessors.forEach(updateProcessor ->
                assertEquals(0, ((NotAppliedUpdateProcessor) updateProcessor).processedCount));
    }
}

class AppliedUpdateProcessor implements UpdateProcessor {

    int checkedCount = 0;
    int processedCount = 0;

    @Override
    public boolean appliesTo(Update update) {
        checkedCount++;
        return true;
    }

    @Override
    public void process(Update update) {
        processedCount++;
    }
}

class NotAppliedUpdateProcessor implements UpdateProcessor {

    int checkedCount = 0;
    int processedCount = 0;

    @Override
    public boolean appliesTo(Update update) {
        checkedCount++;
        return false;
    }

    @Override
    public void process(Update update) {
        processedCount++;
    }
}