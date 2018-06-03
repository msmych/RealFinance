package finance.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Configuration
public class MessageConfig {

    private final String DOUBLE_SPACE = "  ";
    private final String NEW_ROW = "\n";
    private final Logger logger = LogManager.getLogger(MessageConfig.class);

    private final Resource helpResource;

    public MessageConfig(@Value("message/help.md") Resource helpResource) {
        this.helpResource = helpResource;
    }

    @Bean("message-help")
    String helpMessage() {
        return loadFrom(helpResource);
    }

    private String loadFrom(Resource resource) {
        StringBuilder message = new StringBuilder();
        try (InputStream inputStream = resource.getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace(DOUBLE_SPACE, NEW_ROW);
                message.append(line);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return message.toString();
    }
}
