package finance.expense.clear;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.bot.update.UpdateService;
import finance.expense.ExpenseService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class CancelLastExpenseProcessorTest {

    private UpdateService udpateService = mock(UpdateService.class);
    private ExpenseService expenseService = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);

    private CancelLastExpenseProcessor cancelLastExpenseProcessor =
            new CancelLastExpenseProcessor(udpateService, expenseService, bot);

    private Update update = mock(Update.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("clear_1");
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
    }

    @Test
    public void processing() {
        cancelLastExpenseProcessor.process(update);
        verify(expenseService).deleteById(anyLong());
        verify(bot).execute(isA(EditMessageText.class));
    }
}