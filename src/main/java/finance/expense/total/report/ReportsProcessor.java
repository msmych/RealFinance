package finance.expense.total.report;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.chat.BotChatService;
import finance.update.processor.UpdateProcessor;
import finance.update.UpdateService;
import org.springframework.stereotype.Component;

import static finance.expense.total.report.ReportUtils.getReportsSettingsMarkup;

@Component
public class ReportsProcessor implements UpdateProcessor {

    public final static String SWITCH_MONTHLY_REPORT_DATA = "switch_monthly_report";

    private final UpdateService updateService;
    private final BotChatService botChatService;
    private final Bot bot;

    public ReportsProcessor(UpdateService updateService, BotChatService botChatService, Bot bot) {
        this.updateService = updateService;
        this.botChatService = botChatService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCommand(update, "reports");
    }

    @Override
    public void process(Update update) {
        long botChatId = update.message().chat().id();
        bot.execute(new SendMessage(botChatId, "Chat reports")
                .replyMarkup(getReportsSettingsMarkup(botChatService.getBotChatReportType(botChatId))));
    }
}
