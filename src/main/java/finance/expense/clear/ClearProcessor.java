package finance.expense.clear;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static finance.bot.Bot.CLEAR_MARKUP;
import static finance.update.UpdateUtils.isCommand;

@Component
public class ClearProcessor implements UpdateProcessor {

    private final Bot bot;
    private final ExpenseService expenseService;

    public ClearProcessor(@Lazy Bot bot,
                          ExpenseService expenseService) {
        this.bot = bot;
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return isCommand(update, "clear", bot.getUser().username());
    }

    @Override
    public void process(Update update) {
        bot.execute(new SendMessage(update.message().chat().id(), "Choose clearing option:")
                .replyMarkup(CLEAR_MARKUP));
    }
}
