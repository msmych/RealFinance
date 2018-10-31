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

    private UpdateService us = mock(UpdateService.class);
    private BotChatService bcs = mock(BotChatService.class);
    private Bot bot = mock(Bot.class);
    SwitchMonthlyReportProcessor smrp = new SwitchMonthlyReportProcessor(us, bcs, bot);
    private Update update = mock(Update.class);
    CallbackQuery callbackQuery = mock(CallbackQuery.class);
    Message message = mock(Message.class);
    Chat chat = mock(Chat.class);

    @Test
    public void switchingMonthlyReportAndEditingMessage() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        smrp.process(update);
        verify(bcs).getBotChatReportType(anyLong());
        verify(bcs).updateReportType(anyLong(), isA(BotChat.ReportType.class));
        verify(bot).execute(isA(EditMessageReplyMarkup.class));
    }

}