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

    private UpdateService updateService = mock(UpdateService.class);
    private BotChatService botChatService = mock(BotChatService.class);
    private Bot bot = mock(Bot.class);

    private ReportsProcessor reportsProcessor = new ReportsProcessor(updateService, botChatService, bot);

    private Update update = mock(Update.class);
    private Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
    }

    @Test
    public void sendingMessage() {
        reportsProcessor.process(update);
        verify(botChatService).getBotChatReportType(anyLong());
        verify(bot).execute(isA(SendMessage.class));
    }

}