package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;
import static finance.update.InlineKeyboardUtils.TOTAL_MONTH;

@Component
public class TotalMonthProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public TotalMonthProcessor(UpdateService updateService, ExpenseService expenseService, Bot bot) {
        this.updateService = updateService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.callbackQueryDataEquals(TOTAL_MONTH, update);
    }

    @Override
    public void process(Update update) {
        long chatId = update.callbackQuery().message().chat().id();
        bot.execute(new EditMessageText(
                chatId,
                update.callbackQuery().message().messageId(),
                "#total month\n\n" + expenseService.getTotalMonthText(chatId))
                .parseMode(Markdown));
    }
}
