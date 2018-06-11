package finance.command;

import com.pengrad.telegrambot.model.Update;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static finance.update.UpdateUtils.getChat;
import static finance.update.UpdateUtils.isCommand;

@Component
public final class ClearProcessor implements UpdateProcessor {

    private final String CLEAR = "clear";

    private final Bot bot;
    private final ExpenseService expenseService;

    public ClearProcessor(@Lazy Bot bot,
                          ExpenseService expenseService) {
        this.bot = bot;
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return isCommand(update, CLEAR, bot.getUser().username());
    }

    @Override
    public void process(Update update) {
        expenseService.deleteByBotChatId(getChat(update).id());
    }
}
