package finance.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.response.SendResponse;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.expense.total.TotalProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TotalProcessorTest {

    private final String COMMAND_TOTAL = "/total";
    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final User user = mock(User.class);
    private final Bot bot = mock(Bot.class);
    private final SendResponse sendResponse = mock(SendResponse.class);
    private final ExpenseService expenseService = mock(ExpenseService.class);
    private final TotalProcessor totalProcessor = new TotalProcessor(bot, expenseService);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
    }

    @Test
    public void testMessageTextCommandTotal() {
        when(message.text()).thenReturn(COMMAND_TOTAL);
        when(bot.getUser()).thenReturn(user);
        when(user.username()).thenReturn("Bot");
        assertTrue(totalProcessor.appliesTo(update));
    }
}