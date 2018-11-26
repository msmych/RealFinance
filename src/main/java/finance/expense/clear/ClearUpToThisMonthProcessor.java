package finance.expense.clear;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static finance.update.InlineKeyboardUtils.CLEAR_UP_TO_THIS_MONTH_DATA;

@Component
public class ClearUpToThisMonthProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public ClearUpToThisMonthProcessor(UpdateService updateService, ExpenseService expenseService, Bot bot) {
        this.updateService = updateService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.callbackQueryDataEquals(CLEAR_UP_TO_THIS_MONTH_DATA, update);
    }

    @Override
    public void process(Update update) {
        long chatId = update.callbackQuery().message().chat().id();
        expenseService.removeUpToThisMonth(chatId);
        bot.execute(new DeleteMessage(chatId, update.callbackQuery().message().messageId()));
    }
}
