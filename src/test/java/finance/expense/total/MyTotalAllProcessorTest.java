package finance.expense.total;

import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MyTotalAllProcessorTest {

    private UpdateService updateService = mock(UpdateService.class);
    private ExpenseService expenseService = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);

    private MyTotalAllProcessor myTotalAllProcessor = new MyTotalAllProcessor(updateService, expenseService, bot);

    private Update update = mock(Update.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);
    private User user = mock(User.class);

    @Before
    public void setUp() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(callbackQuery.from()).thenReturn(user);
        when(user.id()).thenReturn(1234567);

        when(updateService.getCallbackQueryData(isA(Update.class)))
                .thenReturn(Optional.of("total_all_" + 1234567));
    }

    @Test
    public void total_all_1234567() {
        assertTrue(myTotalAllProcessor.appliesTo(update));
    }

    @Test
    public void processing() {
        myTotalAllProcessor.process(update);
        verify(expenseService).getTotalByBotChatIdAndBotUserId(anyLong(), anyInt());
        verify(bot).execute(isA(EditMessageText.class));
    }

}