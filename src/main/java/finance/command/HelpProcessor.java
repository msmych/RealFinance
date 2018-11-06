package finance.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.update.processor.UpdateProcessor;
import finance.update.UpdateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HelpProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final Bot bot;
    private final String helpMessage;

    public HelpProcessor(UpdateService updateService, Bot bot,
                         @Qualifier("message-help") String helpMessage) {
        this.updateService = updateService;
        this.bot = bot;
        this.helpMessage = helpMessage;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand(update, "help");
    }

    @Override
    public void process(Update update) {
        bot.execute(getSendMessage(update));
    }

    private SendMessage getSendMessage(Update update) {
        return new SendMessage(update.message().chat().id(), helpMessage)
                .parseMode(ParseMode.Markdown);
    }
}
