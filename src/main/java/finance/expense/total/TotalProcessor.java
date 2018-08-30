package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;
import static finance.update.UpdateUtils.isCommand;

@Component
public class TotalProcessor implements UpdateProcessor {

    private final Bot bot;
    private final ExpenseService expenseService;

    public TotalProcessor(@Lazy Bot bot, ExpenseService expenseService) {
        this.bot = bot;
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return isCommand(update, "total", bot.getUser().username());
    }

    @Override
    public void process(Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, "#total\n\n" + expenseService.getAllTotalText(chatId))
                .parseMode(Markdown));
    }
}
