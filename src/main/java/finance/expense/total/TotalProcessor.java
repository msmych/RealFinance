package finance.expense.total;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static finance.update.UpdateUtils.getChat;
import static finance.update.UpdateUtils.isCommand;

@Component
public final class TotalProcessor implements UpdateProcessor {

    private final String TOTAL = "total";

    private final TelegramBot telegramBot;
    private final ExpenseService expenseService;

    public TotalProcessor(@Lazy TelegramBot telegramBot, ExpenseService expenseService) {
        this.telegramBot = telegramBot;
        this.expenseService = expenseService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return isCommand(update, TOTAL, Bot.user.username());
    }

    @Override
    public void process(Update update) {
        telegramBot.execute(getSendMessage(update));
    }

    private SendMessage getSendMessage(Update update) {
        long chatId = getChat(update).id();
        return new SendMessage(chatId, getTotalText(chatId))
                .parseMode(ParseMode.Markdown);
    }

    private String getTotalText(long chatId) {
        String totalText = "*Total*\n";
        totalText += expenseService.getTotal(chatId).stream()
                .map(TotalUtils::formatTotal)
                .collect(Collectors.joining("\n"));
        return totalText;
    }
}