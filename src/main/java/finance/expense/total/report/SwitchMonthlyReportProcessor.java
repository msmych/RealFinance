package finance.expense.total.report;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import finance.bot.Bot;
import finance.bot.chat.BotChat.ReportType;
import finance.bot.chat.BotChatService;
import finance.bot.update.UpdateProcessor;
import finance.bot.update.UpdateService;
import org.springframework.stereotype.Component;

import static finance.bot.chat.BotChat.ReportType.MONTHLY;
import static finance.bot.chat.BotChat.ReportType.NONE;
import static finance.expense.total.report.ReportUtils.getReportsSettingsMarkup;
import static finance.expense.total.report.ReportsProcessor.SWITCH_MONTHLY_REPORT_DATA;

@Component
public class SwitchMonthlyReportProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final BotChatService botChatService;
    private final Bot bot;

    public SwitchMonthlyReportProcessor(UpdateService updateService, BotChatService botChatService, Bot bot) {
        this.updateService = updateService;
        this.botChatService = botChatService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.isCallbackQueryData(update, SWITCH_MONTHLY_REPORT_DATA);
    }

    @Override
    public void process(Update update) {
        long botChatId = update.callbackQuery().message().chat().id();
        ReportType reportType = botChatService.getBotChatReportType(botChatId) == MONTHLY ? NONE : MONTHLY;
        botChatService.updateReportType(botChatId, reportType);
        bot.execute(new EditMessageReplyMarkup(botChatId, update.callbackQuery().message().messageId())
                .replyMarkup(getReportsSettingsMarkup(reportType)));
    }
}
