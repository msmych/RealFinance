package finance.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MessageConfig {

    private final String helpMessage;
    private final String startMessage;

    public MessageConfig(IOService ioService,
                         @Value("message/help.md") Resource helpResource,
                         @Value("message/start.md") Resource startResource) {
        helpMessage = ioService.loadFrom(helpResource);
        startMessage = ioService.loadFrom(startResource);
    }

    @Bean("message-help")
    String getHelpMessage() {
        return helpMessage;
    }

    @Bean("message-start")
    String getStartMessage() { return startMessage; }
}
