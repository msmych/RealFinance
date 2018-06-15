package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static finance.expense.total.TotalUtils.formatTotalCurrency;
import static finance.update.UpdateUtils.getChat;
import static finance.update.UpdateUtils.isCommand;

@Component
public final class TotalProcessor implements UpdateProcessor {

    private final String TOTAL = "total";

    private final Bot bot;
    private final ExpenseService expenseService;

    public TotalProcessor(@Lazy Bot bot, ExpenseService expenseService) {
        this.bot = bot;
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return isCommand(update, TOTAL, bot.getUser().username());
    }

    @Override
    public void process(Update update) {
        bot.execute(getSendTotalMessage(update));
    }

    private SendMessage getSendTotalMessage(Update update) {
        long chatId = getChat(update).id();
        return new SendMessage(chatId, getTotalText(chatId))
                .parseMode(ParseMode.Markdown);
    }

    private String getTotalText(long chatId) {
        String totalText = "*Total*\n\n";
        totalText += expenseService.getTotalCurrency(chatId).stream()
                .map(expenseTotalCurrency -> {
                    String totalExpense = formatTotalCurrency(expenseTotalCurrency) + "\n";
                    totalExpense += expenseService.getTotalCategory(chatId, expenseTotalCurrency.getCurrency()).stream()
                            .map(TotalUtils::formatTotalCategory)
                            .collect(Collectors.joining("\n"));
                    return totalExpense;
                })
                .collect(Collectors.joining("\n\n"));
        return totalText;
    }
}
