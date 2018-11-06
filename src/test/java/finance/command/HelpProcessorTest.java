package finance.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.update.UpdateService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class HelpProcessorTest {

    private final UpdateService updateService = mock(UpdateService.class);
    private final Bot bot = mock(Bot.class);
    private final HelpProcessor helpProcessor = new HelpProcessor(updateService, bot, "help");

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final User user = mock(User.class);

    @Before
    public void setUp() {
        when(bot.getUser()).thenReturn(user);
        when(user.username()).thenReturn("RealFinanceBot");
    }

    @Test
    public void testNoMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(helpProcessor.appliesTo(update));
    }

    @Test
    public void testNoText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(null);
        assertFalse(helpProcessor.appliesTo(update));
    }

    @Test
    public void testWrongText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("WRONG");
        assertFalse(helpProcessor.appliesTo(update));
    }

    @Test
    public void testProcess() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(0L);
        helpProcessor.process(update);
        verify(bot).execute(isA(SendMessage.class));
    }
}