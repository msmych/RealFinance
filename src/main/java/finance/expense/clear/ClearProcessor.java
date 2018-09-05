package finance.expense.clear;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.update.UpdateProcessor;
import finance.bot.update.UpdateService;
import org.springframework.stereotype.Component;

import static finance.bot.Bot.CLEAR_MARKUP;

@Component
public class ClearProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final Bot bot;

    public ClearProcessor(UpdateService updateService, Bot bot) {
        this.updateService = updateService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand(update, "clear");
    }

    @Override
    public void process(Update update) {
        bot.execute(new SendMessage(update.message().chat().id(), "Choose clearing option:")
                .replyMarkup(CLEAR_MARKUP));
    }
}
