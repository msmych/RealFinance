package finance.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ClearProcessorTest {

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final User user = mock(User.class);
    private final Chat chat = mock(Chat.class);
    private final ExpenseService expenseService = mock(ExpenseService.class);
    private final ClearProcessor clearProcessor = new ClearProcessor(expenseService);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
    }

    @Test
    public void testMessageTextCommandClear() {
        when(message.text()).thenReturn("/clear");
        Bot.user = user;
        when(user.username()).thenReturn("Bot");
        assertTrue(clearProcessor.appliesTo(update));
    }

    @Test
    public void testProcess() {
        boolean[] acts = {false};
        doAnswer(invocationOnMock -> acts[0] = true)
                .when(expenseService).deleteByBotChatId(ArgumentMatchers.anyLong());
        clearProcessor.process(update);
        assertTrue(acts[0]);
    }
}