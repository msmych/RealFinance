package finance.expense.total;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class TotalMonthProcessorTest {

    private UpdateService updateService = mock(UpdateService.class);
    private ExpenseService expenseService = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);

    private TotalMonthProcessor totalMonthProcessor = new TotalMonthProcessor(updateService, expenseService, bot);

    private Update update = mock(Update.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
    }

    @Test
    public void processing() {
        totalMonthProcessor.process(update);
        verify(expenseService).getTotalMonthText(anyLong());
        verify(bot).execute(isA(EditMessageText.class));
    }
}