package finance.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;

@Component
public class StartProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final Bot bot;
    private final String startMessage;

    public StartProcessor(UpdateService updateService, Bot bot,
                          @Qualifier("message-start") String startMessage) {
        this.updateService = updateService;
        this.bot = bot;
        this.startMessage = startMessage;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand("start", update);
    }

    @Override
    public void process(Update update) {
        bot.execute(new SendMessage(update.message().chat().id(), startMessage)
                .parseMode(Markdown)
                .disableWebPagePreview(true));
    }
}
