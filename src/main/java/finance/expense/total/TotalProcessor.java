package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static finance.bot.Bot.TOTAL_MARKUP;

@Component
public class TotalProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final Bot bot;

    public TotalProcessor(UpdateService updateService, Bot bot) {
        this.updateService = updateService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand("total", update);
    }

    @Override
    public void process(Update update) {
        bot.execute(new SendMessage(update.message().chat().id(), "Select total period")
                .replyMarkup(TOTAL_MARKUP));
    }
}
