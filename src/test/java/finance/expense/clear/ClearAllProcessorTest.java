package finance.expense.clear;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClearAllProcessorTest {

    private ExpenseService expenseService = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);

    private ClearAllProcessor cap = new ClearAllProcessor(expenseService, bot);

    private Update update = mock(Update.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(callbackQuery.data()).thenReturn("clear_all");
    }

    @Test
    public void callbackQueryDataClearAllApplies() {
        assertTrue(cap.appliesTo(update));
    }

    @Test
    public void noCallbackQueryNotApplies() {
        when(update.callbackQuery()).thenReturn(null);
        assertFalse(cap.appliesTo(update));
    }

    @Test
    public void noCallbackQueryDataNotApplies() {
        when(callbackQuery.data()).thenReturn(null);
        assertFalse(cap.appliesTo(update));
    }

    @Test
    public void wrongCallbackQueryDataNotApplies() {
        when(callbackQuery.data()).thenReturn("WRONG");
        assertFalse(cap.appliesTo(update));
    }

    @Test
    public void clearingAll() {
        cap.process(update);
        verify(expenseService).deleteByBotChatId(anyLong());
        verify(bot).execute(isA(DeleteMessage.class));
    }

}