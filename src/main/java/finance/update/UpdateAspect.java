package finance.update;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public final class UpdateAspect {

    @Before("execution(public int finance.update.SpringUpdatesListener.process(java.util.List))")
    void saveChatsAndUsers(JoinPoint joinPoint) {

    }
}
