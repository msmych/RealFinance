package finance.expense.total;

import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.chat.BotChatService;
import finance.expense.ExpenseService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TotalTask {

    private final BotChatService botChatService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public TotalTask(BotChatService botChatService, ExpenseService expenseService, Bot bot) {
        this.botChatService = botChatService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Scheduled(cron = "0 0 9 1 * ?")
    public void reportMonth() {
        botChatService.getBotChatIdsForMonthlyReport().forEach(chatId ->
                bot.execute(new SendMessage(chatId, "#total\n\n" + expenseService.getMonthlyReportText(chatId))));
    }
}