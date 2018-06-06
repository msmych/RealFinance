package finance.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TotalProcessorTest {

    private final String COMMAND_TOTAL = "/total";
    private final long TOTAL = 1000L;
    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final User user = mock(User.class);
    private final TelegramBot telegramBot = mock(TelegramBot.class);
    private final SendResponse sendResponse = mock(SendResponse.class);
    private final ExpenseService expenseService = mock(ExpenseService.class);
    private final TotalProcessor totalProcessor = new TotalProcessor(telegramBot, expenseService);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
    }

    @Test
    public void testMessageTextCommandTotal() {
        when(message.text()).thenReturn(COMMAND_TOTAL);
        Bot.user = user;
        when(user.username()).thenReturn("RealFinanceBot");
        assertTrue(totalProcessor.appliesTo(update));
    }

    @Test
    public void testProcess() {
        boolean[] acts = {false, false};
        when(expenseService.getTotal(ArgumentMatchers.anyLong()))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return TOTAL;
                });
        when(telegramBot.execute(ArgumentMatchers.isA(SendMessage.class)))
                .then(invocationOnMock -> {
                    acts[1] = true;
                    return sendResponse;
                });
        totalProcessor.process(update);
        assertArrayEquals(new boolean[]{true, true}, acts);
    }
}