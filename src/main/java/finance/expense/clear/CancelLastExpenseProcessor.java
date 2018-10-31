package finance.expense.clear;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.update.processor.UpdateProcessor;
import finance.update.UpdateService;
import finance.expense.ExpenseService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class CancelLastExpenseProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public CancelLastExpenseProcessor(UpdateService updateService, ExpenseService expenseService, Bot bot) {
        this.updateService = updateService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        Optional<String> dataOptional = updateService.getCallbackQueryData(update);
        if (!dataOptional.isPresent()) return false;
        String data = dataOptional.get();
        return Pattern.matches("clear_[0-9]+", data);
    }

    @Override
    public void process(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        expenseService.deleteById(Long.valueOf(callbackQuery.data().replace("clear_", "")));
        Message message = callbackQuery.message();
        bot.execute(new EditMessageText(message.chat().id(), message.messageId(), "Canceled last expense"));
    }
}
