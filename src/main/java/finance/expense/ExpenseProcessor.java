package finance.expense;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.bot.Bot;
import finance.update.UpdateProcessor;
import org.springframework.stereotype.Component;

import static finance.expense.ExpenseUtils.parseAmount;
import static finance.update.UpdateUtils.getText;

@Component
public final class ExpenseProcessor implements UpdateProcessor {

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
        String amountRegex = "[/][0-9]+([/.][0-9]{2})?";
        return (text.matches(amountRegex));
    }

    @Override
    public void process(Update update) {
        expenseService.save(update, parseAmount(getText(update).get()));
    }
}
