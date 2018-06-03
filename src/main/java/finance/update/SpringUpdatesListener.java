package finance.update;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringUpdatesListener implements UpdatesListener {

    private final Logger logger = LogManager.getLogger(SpringUpdatesListener.class);

    private final List<UpdateProcessor> updateProcessors;

    public SpringUpdatesListener(List<UpdateProcessor> updateProcessors) {
        this.updateProcessors = updateProcessors;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info(update);
            updateProcessors.stream()
                    .filter(updateProcessor -> updateProcessor.appliesTo(update))
                    .forEach(updateProcessor -> updateProcessor.process(update));
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
