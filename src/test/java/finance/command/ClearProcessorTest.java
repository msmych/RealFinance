package finance.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.expense.clear.ClearProcessor;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClearProcessorTest {

    private Bot bot = mock(Bot.class);
    private ExpenseService es = mock(ExpenseService.class);
    ClearProcessor cp = new ClearProcessor(bot, es);

    @Test
    public void sendingMessage() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        cp.process(update);
        verify(bot).execute(isA(SendMessage.class));
    }
}