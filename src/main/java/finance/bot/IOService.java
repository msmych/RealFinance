package finance.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class IOService {

    private final Logger logger = LoggerFactory.getLogger(IOService.class);

    public String loadFrom(Resource resource) {
        StringBuilder message = new StringBuilder();
        try (InputStream inputStream = resource.getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("  ", "\n");
                message.append(line);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return message.toString();
    }
}
