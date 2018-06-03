package finance.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.update.UpdateProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public final class HelpProcessor implements UpdateProcessor {

    private final String HELP = "/help";

    private final TelegramBot telegramBot;
    private final String helpMessage;

    public HelpProcessor(@Lazy TelegramBot telegramBot,
                         @Qualifier("message-help") String helpMessage) {
        this.telegramBot = telegramBot;
        this.helpMessage = helpMessage;
    }

    @Override
    public boolean appliesTo(Update update) {
        Message message = update.message();
        if (message == null) return false;
        String text = message.text();
        if (text == null) return false;
        return text.equals(HELP)
                || text.equals(HELP + "@" + Bot.user.username());
    }

    @Override
    public void process(Update update) {
        telegramBot.execute(getSendMessage(update));
    }

    private SendMessage getSendMessage(Update update) {
        return new SendMessage(update.message().chat().id(), helpMessage)
                .parseMode(ParseMode.Markdown);
    }
}
