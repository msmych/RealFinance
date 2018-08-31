package finance.bot.update;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import finance.bot.Bot;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateServiceTest {

    private Bot bot = mock(Bot.class);
    UpdateService us = new UpdateService(bot);
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    CallbackQuery callbackQuery = mock(CallbackQuery.class);

    @Test
    public void messageTextSlashCommand() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/command");
        assertTrue(us.isCommand(update, "command"));
    }

    @Test
    public void messageTextSlashCommandAtBotUsername() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/command@BotUsername");
        User botUser = mock(User.class);
        when(bot.getUser()).thenReturn(botUser);
        when(botUser.username()).thenReturn("BotUsername");
        assertTrue(us.isCommand(update, "command"));
    }

    @Test
    public void messageTextSlashWrong() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/WRONG");
        User botUser = mock(User.class);
        when(bot.getUser()).thenReturn(botUser);
        when(botUser.username()).thenReturn("BotUsername");
        assertFalse(us.isCommand(update, "command"));
    }

    @Test
    public void messageNoText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(null);
        assertFalse(us.isCommand(update, "command"));
    }

    @Test
    public void noMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(us.isCommand(update, "command"));
    }

    @Test
    public void callbackQueryDataData() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("data");
        assertTrue(us.isCallbackQueryData(update, "data"));
    }

    @Test
    public void callbackQueryDataWrong() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("WRONG");
        assertFalse(us.isCallbackQueryData(update, "data"));
    }

    @Test
    public void callbackQueryNoData() {
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn(null);
        assertFalse(us.isCallbackQueryData(update, "data"));
    }

    @Test
    public void noCallbackQuery() {
        when(update.callbackQuery()).thenReturn(null);
        assertFalse(us.isCallbackQueryData(update, "data"));
    }

}