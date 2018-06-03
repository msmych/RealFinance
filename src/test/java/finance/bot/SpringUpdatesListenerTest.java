package finance.bot;

import com.pengrad.telegrambot.model.Update;
import finance.update.SpringUpdatesListener;
import finance.update.UpdateProcessor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SpringUpdatesListenerTest {

    Update update = mock(Update.class);

    @Test
    public void testCheckedAllUpdatesOnAllProcessors() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor());
        SpringUpdatesListener springUpdatesListener = new SpringUpdatesListener(updateProcessors);
        List<Update> updates = Arrays.asList(update, update, update, update);
        springUpdatesListener.process(updates);
        updateProcessors.forEach(updateProcessor ->
                assertEquals(updates.size(), ((NotAppliedUpdateProcessor) updateProcessor).checkedCount));
    }

    @Test
    public void testAllAppliedProcessed() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new AppliedUpdateProcessor(), new AppliedUpdateProcessor());
        SpringUpdatesListener springUpdatesListener = new SpringUpdatesListener(updateProcessors);
        List<Update> updates = Arrays.asList(update, update, update);
        springUpdatesListener.process(updates);
        updateProcessors.forEach(updateProcessor ->
                assertEquals(updates.size(), ((AppliedUpdateProcessor) updateProcessor).processedCount));
    }

    @Test
    public void testNoneNotAppliedProcessed() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor());
        SpringUpdatesListener springUpdatesListener = new SpringUpdatesListener(updateProcessors);
        List<Update> updates = Arrays.asList(update, update, update);
        springUpdatesListener.process(updates);
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