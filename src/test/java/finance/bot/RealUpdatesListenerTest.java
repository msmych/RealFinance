package finance.bot;

import com.pengrad.telegrambot.model.Update;
import finance.update.RealUpdatesListener;
import finance.update.pre_processor.UpdatePreProcessor;
import finance.update.processor.UpdateProcessor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class RealUpdatesListenerTest {

    private final UpdatePreProcessor updatePreProcessor = mock(UpdatePreProcessor.class);
    private final List<UpdatePreProcessor> updatePreProcessors = Arrays.asList(updatePreProcessor, updatePreProcessor);
    private final UpdateProcessor updateProcessor = mock(UpdateProcessor.class);
    private final Update update = mock(Update.class);

    @Test
    public void testCheckedAllUpdatesOnAllProcessors() {
        List<UpdateProcessor> updateProcessors =
                Arrays.asList(updateProcessor, updateProcessor, updateProcessor);
        when(updateProcessor.appliesTo(isA(Update.class))).thenReturn(false);
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updatePreProcessors, updateProcessors);
        List<Update> updates = Arrays.asList(update, update, update, update);
        realUpdatesListener.process(updates);
        verify(updatePreProcessor, times(updatePreProcessors.size() * updates.size()))
                .preProcess(isA(Update.class));
        verify(updateProcessor, times(updates.size() * updateProcessors.size()))
                .appliesTo(isA(Update.class));
        verify(updateProcessor, never()).process(isA(Update.class));
    }

    @Test
    public void testAllAppliedProcessed() {
        when(updateProcessor.appliesTo(isA(Update.class))).thenReturn(true);
        List<UpdateProcessor> updateProcessors = Arrays.asList(updateProcessor, updateProcessor);
        RealUpdatesListener realUpdatesListener = new RealUpdatesListener(updatePreProcessors, updateProcessors);
        List<Update> updates = Arrays.asList(update, update, update);
        realUpdatesListener.process(updates);
        verify(updateProcessor, times(updateProcessors.size() * updates.size()))
                .process(isA(Update.class));
    }
}