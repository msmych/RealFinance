package finance.expense.total.report;

import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.chat.BotChatService;
import finance.expense.ExpenseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Locale;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;
import static java.util.Calendar.*;

@Component
public class ReportTask {

    private final Logger logger = LogManager.getLogger(ReportTask.class);

    private final BotChatService botChatService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public ReportTask(BotChatService botChatService, ExpenseService expenseService, Bot bot) {
        this.botChatService = botChatService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Scheduled(cron = "0 0 9 1 * ?")
    public void reportMonthly() {
        logger.info("Monthly report");
        botChatService.getBotChatIdsForMonthlyReport().forEach(chatId ->
                bot.execute(new SendMessage(chatId,
                        "#total " + getLastMonthNameAndYear() + "\n\n" + expenseService.getMonthlyReportText(chatId))
                        .parseMode(Markdown)));
    }

    private String getLastMonthNameAndYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(MONTH, -1);
        return calendar.getDisplayName(MONTH, LONG, Locale.getDefault()) + " " + calendar.get(YEAR);
    }
}