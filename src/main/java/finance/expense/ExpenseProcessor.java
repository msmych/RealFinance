package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.update.UpdateProcessor;
import org.springframework.stereotype.Component;

@Component
public final class ExpenseProcessor implements UpdateProcessor {

    private final String AMOUNT_REGEX = "[0-9]+([/.][0-9]{2})?";
    private final ExpenseService expenseService;

    private int amount;

    public ExpenseProcessor(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        Message message = update.message();
        if (message == null) return false;
        String text = message.text();
        if (text == null) return false;
        if (!text.matches(AMOUNT_REGEX)) return false;
        amount = parseAmount(text);
        return true;
    }

    private int parseAmount(String text) {
        String[] amountParts = text.split("\\.");
        String amountString = amountParts.length == 1
                ? amountParts[0] + "00"
                : amountParts[0] + amountParts[1];
        return Integer.valueOf(amountString);
    }

    @Override
    public void process(Update update) {
        expenseService.save(update, amount);
    }
}
