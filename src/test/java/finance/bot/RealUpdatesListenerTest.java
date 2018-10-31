package finance.bot;

import com.pengrad.telegrambot.model.Update;
import finance.update.RealUpdatesListener;
import finance.update.pre_processor.UpdatePreProcessor;
import finance.update.processor.UpdateProcessor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RealUpdatesListenerTest {

    private final UpdatePreProcessor updatePreProcessor = mock(UpdatePreProcessor.class);
    private final List<UpdatePreProcessor> updatePreProcessors = Arrays.asList(updatePreProcessor, updatePreProcessor);
    private final Update update = mock(Update.class);

    @Test
    public void testCheckedAllUpdatesOnAllProcessors() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor());
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updatePreProcessors, updateProcessors);
        List<Update> updates = Arrays.asList(update, update, update, update);
        realUpdatesListener.process(updates);
        updateProcessors.forEach(updateProcessor ->
                assertEquals(updates.size(), ((NotAppliedUpdateProcessor) updateProcessor).checkedCount));
    }

    @Test
    public void testAllAppliedProcessed() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new AppliedUpdateProcessor(), new AppliedUpdateProcessor());
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updatePreProcessors, updateProcessors);
        List<Update> updates = Arrays.asList(update, update, update);
        realUpdatesListener.process(updates);
        updateProcessors.forEach(updateProcessor ->
                assertEquals(updates.size(), ((AppliedUpdateProcessor) updateProcessor).processedCount));
    }

    @Test
    public void testNoneNotAppliedProcessed() {
        List<UpdateProcessor> updateProcessors = Arrays.asList(
                new NotAppliedUpdateProcessor(), new NotAppliedUpdateProcessor());
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updatePreProcessors, updateProcessors);
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