package finance.update;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import finance.update.pre_processor.UpdatePreProcessor;
import finance.update.processor.UpdateProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealUpdatesListener implements UpdatesListener {

    private final Logger logger = LogManager.getLogger(RealUpdatesListener.class);

    private final List<UpdatePreProcessor> updatePreProcessors;
    private final List<UpdateProcessor> updateProcessors;

    public RealUpdatesListener(List<UpdatePreProcessor> updatePreProcessors,
                               List<UpdateProcessor> updateProcessors) {
        this.updatePreProcessors = updatePreProcessors;
        this.updateProcessors = updateProcessors;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            updatePreProcessors.forEach(updatePreProcessor -> tryPreProcess(updatePreProcessor, update));
            updateProcessors.stream()
                    .filter(updateProcessor -> tryCheckIfProcessorApplies(updateProcessor, update))
                    .forEach(updateProcessor -> tryProcess(updateProcessor, update)); });
        return CONFIRMED_UPDATES_ALL;
    }

    private void tryPreProcess(UpdatePreProcessor updatePreProcessor, Update update) {
        try {
            updatePreProcessor.preProcess(update);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private boolean tryCheckIfProcessorApplies(UpdateProcessor updateProcessor, Update update) {
        try {
            return updateProcessor.appliesTo(update);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private void tryProcess(UpdateProcessor updateProcessor, Update update) {
        try {
            updateProcessor.process(update);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
