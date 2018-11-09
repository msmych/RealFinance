package finance.expense.clear;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static finance.update.InlineKeyboardUtils.CLEAR_ALL_DATA;

@Component
public class ClearAllProcessor implements UpdateProcessor {

    private final ExpenseService expenseService;
    private final Bot bot;

    public ClearAllProcessor(ExpenseService expenseService, Bot bot) {
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery == null) return false;
        String data = callbackQuery.data();
        return data != null && data.equals(CLEAR_ALL_DATA);
    }

    @Override
    public void process(Update update) {
        long chatId = update.callbackQuery().message().chat().id();
        expenseService.deleteByBotChatId(chatId);
        bot.execute(new DeleteMessage(chatId, update.callbackQuery().message().messageId()));
    }
}
