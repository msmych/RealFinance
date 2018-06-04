package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.update.UpdateProcessor;
import org.springframework.stereotype.Component;

@Component
public final class ExpenseProcessor implements UpdateProcessor {

    private final ExpenseService expenseService;

    private String amount;

    public ExpenseProcessor(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        Message message = update.message();
        if (message == null) return false;
        String text = message.text();
        if (text == null) return false;
        if (!text.matches("[0-9]+")) return false;
        amount = text + "00";
        return true;
    }

    @Override
    public void process(Update update) {
        expenseService.save(update, amount);
    }
}
