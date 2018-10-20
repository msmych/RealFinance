package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.update.UpdateProcessor;
import finance.bot.update.UpdateService;
import finance.expense.ExpenseService;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;

@Component
public class MonthProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public MonthProcessor(UpdateService updateService, ExpenseService expenseService, Bot bot) {
        this.updateService = updateService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand(update, "month");
    }

    @Override
    public void process(Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, "#total month\n\n" + expenseService.getTotalMonthText(chatId))
                .parseMode(Markdown));
    }
}
