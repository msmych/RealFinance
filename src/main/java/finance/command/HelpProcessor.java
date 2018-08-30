package finance.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.update.UpdateProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static finance.update.UpdateUtils.isCommand;

@Component
public class HelpProcessor implements UpdateProcessor {

    private final String HELP = "help";

    private final Bot bot;
    private final String helpMessage;

    public HelpProcessor(@Lazy Bot bot,
                         @Qualifier("message-help") String helpMessage) {
        this.bot = bot;
        this.helpMessage = helpMessage;
    }

    @Override
    public boolean appliesTo(Update update) {
        return isCommand(update, HELP, bot.getUser().username());
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
