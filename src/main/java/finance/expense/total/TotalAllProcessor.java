package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;
import static finance.update.InlineKeyboardUtils.TOTAL_ALL;

@Component
public class TotalAllProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final Bot bot;
    private final ExpenseService expenseService;

    public TotalAllProcessor(UpdateService updateService, Bot bot, ExpenseService expenseService) {
        this.updateService = updateService;
        this.bot = bot;
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.callbackQueryDataEquals(TOTAL_ALL, update);
    }

    @Override
    public void process(Update update) {
        long chatId = update.callbackQuery().message().chat().id();
        bot.execute(new EditMessageText(
                chatId,
                update.callbackQuery().message().messageId(),
                "#total\n\n" + expenseService.getAllTotalText(chatId))
                .parseMode(Markdown));
    }
}
