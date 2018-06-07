package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.update.UpdateProcessor;
import org.springframework.stereotype.Component;

import static finance.update.UpdateUtils.getText;

@Component
public final class ExpenseProcessor implements UpdateProcessor {

    private final String COMMAND_EXPENSE = "/expense ";
    private final String AMOUNT_REGEX = "[/]expense [0-9]+([/.][0-9]{2})?";
    private final ExpenseService expenseService;

    public ExpenseProcessor(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        Message message = update.message();
        if (message == null) return false;
        String text = message.text();
        if (text == null) return false;
        return (text.matches(AMOUNT_REGEX));
    }

    @Override
    public void process(Update update) {
        expenseService.save(update, parseAmount(getText(update).get()));
    }

    private int parseAmount(String text) {
        String[] amountParts = text
                .substring(COMMAND_EXPENSE.length())
                .split("\\.");
        String amountString = amountParts.length == 1
                ? amountParts[0] + "00"
                : amountParts[0] + amountParts[1];
        return Integer.valueOf(amountString);
    }
}
