package finance.expense;

import com.pengrad.telegrambot.model.Update;
import finance.update.UpdateProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static finance.expense.ExpenseUtils.isExpense;
import static finance.update.UpdateUtils.getChat;

@Component
public final class ExpenseModifyProcessor implements UpdateProcessor {

    private final ExpenseService expenseService;

    public ExpenseModifyProcessor(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        if (update.editedMessage() == null) return false;
        Optional<Expense> optionalExpense = expenseService
                .getExpenseByBotChatIdAndMessageId(getChat(update).id(), update.editedMessage().messageId());
        if (!optionalExpense.isPresent()) return false;
        return isExpense(update);
    }

    @Override
    public void process(Update update) {
        expenseService.save(update);
    }
}
