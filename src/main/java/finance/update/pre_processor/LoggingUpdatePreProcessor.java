package finance.update.pre_processor;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingUpdatePreProcessor implements UpdatePreProcessor {

    private final Logger logger = LoggerFactory.getLogger(LoggingUpdatePreProcessor.class);

    @Override
    public void preProcess(Update update) {
        logger.info(update.toString());
    }
}
