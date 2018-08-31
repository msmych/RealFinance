package finance.expense.total.report;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import finance.bot.chat.BotChat.ReportType;

import static finance.bot.chat.BotChat.ReportType.MONTHLY;
import static finance.expense.total.report.ReportsProcessor.SWITCH_MONTHLY_REPORT_DATA;

public class ReportUtils {

    public static InlineKeyboardMarkup getReportsSettingsMarkup(ReportType reportType) {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton[] {
                        new InlineKeyboardButton((reportType == MONTHLY ? "✅ " : "") +"Monthly")
                                .callbackData(SWITCH_MONTHLY_REPORT_DATA)});
    }
}
