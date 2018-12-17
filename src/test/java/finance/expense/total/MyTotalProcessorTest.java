package finance.expense.total;

import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.bot.user.BotUser;
import finance.bot.user.BotUserService;
import finance.update.UpdateService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MyTotalProcessorTest {

    private UpdateService updateService = mock(UpdateService.class);
    private BotUserService botUserService = mock(BotUserService.class);
    private Bot bot = mock(Bot.class);

    private MyTotalProcessor myTotalProcessor = new MyTotalProcessor(updateService, botUserService, bot);

    private Update update = mock(Update.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);
    private Message message = mock(Message.class);
    private User user = mock(User.class);
    private Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.message()).thenReturn(message);
        when(callbackQuery.from()).thenReturn(user);
        when(user.id()).thenReturn(1234567);
        when(message.chat()).thenReturn(chat);

        when(updateService.getCallbackQueryData(isA(Update.class))).thenReturn(Optional.of("total_" + 1234567));
        when(botUserService.findById(anyInt())).thenReturn(Optional.of(new BotUser()));
    }

    @Test
    public void total_1234567() {
        assertTrue(myTotalProcessor.appliesTo(update));
    }

    @Test
    public void processing() {
        myTotalProcessor.process(update);
        verify(bot).execute(isA(EditMessageText.class));
    }
}