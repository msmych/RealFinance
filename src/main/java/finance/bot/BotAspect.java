package finance.bot;

import com.pengrad.telegrambot.response.BaseResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public final class BotAspect {

    private final Logger logger = LogManager.getLogger(BotAspect.class);

    @AfterReturning(pointcut = "execution(public * com.pengrad.telegrambot.TelegramBot.execute(..))",
            returning = "response")
    void logExecutionResponse(Object response) {
        BaseResponse baseResponse = (BaseResponse) response;
        if (baseResponse.isOk()) {
            logger.info(baseResponse);
        } else {
            logger.error(baseResponse.description());
        }
    }
}
