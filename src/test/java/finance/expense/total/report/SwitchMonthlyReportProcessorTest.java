package finance.expense.total.report;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import finance.bot.Bot;
import finance.bot.chat.BotChat;
import finance.bot.chat.BotChatService;
import finance.update.UpdateService;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SwitchMonthlyReportProcessorTest {

    private UpdateService updateService = mock(UpdateService.class);
    private BotChatService botChatService = mock(BotChatService.class);
    private Bot bot = mock(Bot.class);

    private SwitchMonthlyReportProcessor switchMonthlyReportProcessor =
            new SwitchMonthlyReportProcessor(updateService, botChatService, bot);

    private Update update = mock(Update.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);

    @Test
    public void switchingMonthlyReportAndEditingMessage() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        switchMonthlyReportProcessor.process(update);
        verify(botChatService).getBotChatReportType(anyLong());
        verify(botChatService).updateReportType(anyLong(), isA(BotChat.ReportType.class));
        verify(bot).execute(isA(EditMessageReplyMarkup.class));
    }

}