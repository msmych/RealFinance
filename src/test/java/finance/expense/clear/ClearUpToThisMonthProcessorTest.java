package finance.expense.clear;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ClearUpToThisMonthProcessorTest {

    private UpdateService updateService = mock(UpdateService.class);
    private ExpenseService expenseService = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);
    private ClearUpToThisMonthProcessor clearUpToThisMonthProcessor = new ClearUpToThisMonthProcessor(updateService, expenseService, bot);
    private Update update = mock(Update.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(updateService.callbackQueryDataEquals(anyString(), isA(Update.class))).thenReturn(true);
    }

    @Test
    public void processing() {
        clearUpToThisMonthProcessor.process(update);
        verify(expenseService).removeUpToThisMonth(anyLong());
        verify(bot).execute(isA(DeleteMessage.class));
    }

}