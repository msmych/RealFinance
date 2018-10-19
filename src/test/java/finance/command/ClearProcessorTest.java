package finance.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.update.UpdateService;
import finance.expense.ExpenseService;
import finance.expense.clear.ClearProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class ClearProcessorTest {

    private UpdateService updateService = mock(UpdateService.class);
    private ExpenseService expenseService = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);

    private ClearProcessor clearProcessor = new ClearProcessor(updateService, expenseService, bot);

    private Update update = mock(Update.class);
    private Message message = mock(Message.class);
    private User user = mock(User.class);
    private Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.from()).thenReturn(user);
    }

    @Test
    public void sendingMessage() {
        clearProcessor.process(update);
        verify(bot).execute(isA(SendMessage.class));
        verify(expenseService).getLastBotUserIdExpense(anyLong(), anyInt());
    }
}