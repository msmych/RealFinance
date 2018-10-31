package finance.expense.total;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.update.UpdateService;
import finance.expense.ExpenseService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MyTotalProcessorTest {

    private UpdateService us = mock(UpdateService.class);
    private ExpenseService es = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);
    MyTotalProcessor myTotalProcessor = new MyTotalProcessor(us, es, bot);

    Update update = mock(Update.class);
    Message message = mock(Message.class);
    private Chat chat = mock(Chat.class);
    User user = mock(User.class);

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.from()).thenReturn(user);
    }

    @Test
    public void processing() {
        myTotalProcessor.process(update);
        verify(es).getTotalByBotChatIdAndBotUserId(anyLong(), anyInt());
        verify(bot).execute(isA(SendMessage.class));
    }

}