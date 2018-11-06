package finance.update;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import finance.bot.Bot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateServiceTest {

    private Bot bot = mock(Bot.class);

    private UpdateService updateService = new UpdateService(bot);

    private Update update = mock(Update.class);
    private Message message = mock(Message.class);
    private CallbackQuery callbackQuery = mock(CallbackQuery.class);

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(update.callbackQuery()).thenReturn(callbackQuery);
    }

    @Test
    public void messageTextSlashCommand() {
        when(message.text()).thenReturn("/command");
        assertTrue(updateService.isCommand(update, "command"));
    }

    @Test
    public void messageTextSlashCommandAtBotUsername() {
        when(message.text()).thenReturn("/command@BotUsername");
        User botUser = mock(User.class);
        when(bot.getUser()).thenReturn(botUser);
        when(botUser.username()).thenReturn("BotUsername");
        assertTrue(updateService.isCommand(update, "command"));
    }

    @Test
    public void messageTextSlashWrong() {
        when(message.text()).thenReturn("/WRONG");
        User botUser = mock(User.class);
        when(bot.getUser()).thenReturn(botUser);
        when(botUser.username()).thenReturn("BotUsername");
        assertFalse(updateService.isCommand(update, "command"));
    }

    @Test
    public void messageNoText() {
        when(message.text()).thenReturn(null);
        assertFalse(updateService.isCommand(update, "command"));
    }

    @Test
    public void noMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(updateService.isCommand(update, "command"));
    }

    @Test
    public void callbackQueryDataData() {
        when(callbackQuery.data()).thenReturn("data");
        assertTrue(updateService.equalsCallbackQueryData("data", update));
        assertTrue(updateService.getCallbackQueryData(update).isPresent());
    }

    @Test
    public void callbackQueryDataWrong() {
        when(callbackQuery.data()).thenReturn("WRONG");
        assertFalse(updateService.equalsCallbackQueryData("data", update));
    }

    @Test
    public void noCallbackQuery() {
        when(update.callbackQuery()).thenReturn(null);
        assertFalse(updateService.equalsCallbackQueryData("data", update));
        assertFalse(updateService.getCallbackQueryData(update).isPresent());
    }

    @Test
    public void callbackQueryNoData() {
        when(callbackQuery.data()).thenReturn(null);
        assertFalse(updateService.equalsCallbackQueryData("data", update));
        assertFalse(updateService.getCallbackQueryData(update).isPresent());
    }
}