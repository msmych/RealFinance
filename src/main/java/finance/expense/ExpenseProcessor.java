package finance.expense;

import com.pengrad.telegrambot.model.Update;
import finance.update.UpdateProcessor;
import org.springframework.stereotype.Component;

import static finance.expense.ExpenseUtils.isExpense;

@Component
public class ExpenseProcessor implements UpdateProcessor {

    private final ExpenseService expenseService;

    public ExpenseProcessor(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return isExpense(update);
    }

    @Override
    public void process(Update update) {
        expenseService.save(update);
    }
}
