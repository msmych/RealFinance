package finance.expense.total.report;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.chat.BotChatService;
import finance.update.UpdateService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportsProcessorTest {

    private UpdateService us = mock(UpdateService.class);
    BotChatService bcs = mock(BotChatService.class);
    private Bot bot = mock(Bot.class);
    ReportsProcessor rp = new ReportsProcessor(us, bcs, bot);
    private Update update = mock(Update.class);
    Message message = mock(Message.class);
    Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
    }

    @Test
    public void sendingMessage() {
        rp.process(update);
        verify(bcs).getBotChatReportType(anyLong());
        verify(bot).execute(isA(SendMessage.class));
    }

}