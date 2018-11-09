package finance.expense.clear;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.expense.Expense;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static finance.update.InlineKeyboardUtils.CLEAR_ALL_BUTTON;

@Component
public class ClearProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public ClearProcessor(UpdateService updateService, ExpenseService expenseService, Bot bot) {
        this.updateService = updateService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand("clear", update);
    }

    @Override
    public void process(Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, "Choose clearing option:")
                .replyMarkup(getClearMarkup(chatId, update.message().from().id())));
    }

    private InlineKeyboardMarkup getClearMarkup(long chatId, int userId) {
        Optional<Expense> expenseOptional = expenseService.getLastBotUserIdExpense(chatId, userId);
        List<InlineKeyboardButton[]> buttons = new ArrayList<>();
        if (expenseOptional.isPresent()) {
            Expense expense = expenseOptional.get();
            buttons.add(new InlineKeyboardButton[]{new InlineKeyboardButton(expense.toString())
                    .callbackData("clear_" + expense.id)});
        }
        buttons.add(new InlineKeyboardButton[]{CLEAR_ALL_BUTTON});
        return new InlineKeyboardMarkup(buttons.toArray(new InlineKeyboardButton[][]{}));
    }
}
