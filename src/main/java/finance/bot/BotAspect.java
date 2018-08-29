package finance.bot;

import com.pengrad.telegrambot.response.BaseResponse;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static org.apache.logging.log4j.LogManager.getLogger;

@Aspect
@Component
public class BotAspect {

    private final Logger logger = getLogger(BotAspect.class);

    @AfterReturning(pointcut = "execution(public * com.pengrad.telegrambot.TelegramBot.execute(..))",
            returning = "response")
    void logExecutionResponse(BaseResponse response) {
        if (response.isOk()) {
            logger.info(response);
        } else {
            logger.error(response.description());
        }
    }
}
