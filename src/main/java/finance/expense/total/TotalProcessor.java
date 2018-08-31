package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.update.UpdateProcessor;
import finance.bot.update.UpdateService;
import finance.expense.ExpenseService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;

@Component
public class TotalProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final Bot bot;
    private final ExpenseService expenseService;

    public TotalProcessor(UpdateService updateService, @Lazy Bot bot, ExpenseService expenseService) {
        this.updateService = updateService;
        this.bot = bot;
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand(update, "total");
    }

    @Override
    public void process(Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, "#total\n\n" + expenseService.getAllTotalText(chatId))
                .parseMode(Markdown));
    }
}
