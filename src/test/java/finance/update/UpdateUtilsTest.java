package finance.update;

import com.pengrad.telegrambot.model.*;
import org.junit.Before;
import org.junit.Test;

import static finance.update.UpdateUtils.getText;
import static finance.update.UpdateUtils.isCommand;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateUtilsTest {

    private final String COMMAND = "command";
    private final String SLASH_COMMAND = "/" + COMMAND;
    private final String BOT_USERNAME = "Bot";
    private final String AT_BOT_USERNAME = "@" + BOT_USERNAME;

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final User user = mock(User.class);
    private final CallbackQuery callbackQuery = mock(CallbackQuery.class);

    @Before
    public void before() {
        when(callbackQuery.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.from()).thenReturn(user);
        when(callbackQuery.from()).thenReturn(user);
    }

    @Test
    public void testMessageTextCommand() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(SLASH_COMMAND);
        assertTrue(isCommand(update, COMMAND, BOT_USERNAME));
    }

    @Test
    public void testMessageTextCommandAtBotUsername() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(SLASH_COMMAND + AT_BOT_USERNAME);
        assertTrue(isCommand(update, COMMAND, BOT_USERNAME));
    }

    @Test
    public void testNoMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(isCommand(update, COMMAND, BOT_USERNAME));
        assertFalse(getText(update).isPresent());
    }

    @Test
    public void testMessageNoText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(null);
        assertFalse(isCommand(update, COMMAND, BOT_USERNAME));
        assertFalse(getText(update).isPresent());
    }

    @Test
    public void testMessageText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("TEXT");
        assertFalse(isCommand(update, COMMAND, BOT_USERNAME));
        assertTrue(getText(update).isPresent());
    }

    @Test
    public void testMessage() {
        when(update.message()).thenReturn(message);
        when(update.callbackQuery()).thenReturn(null);
        assertUpdate();
    }

    @Test
    public void testCallbackQuery() {
        when(update.message()).thenReturn(null);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        assertUpdate();
    }

    @Test
    public void testEditedMessage() {
        when(update.message()).thenReturn(null);
        when(update.editedMessage()).thenReturn(message);
        assertUpdate();
    }

    private void assertUpdate() {
        assertNotNull(UpdateUtils.getChat(update));
        assertNotNull(UpdateUtils.getFrom(update));
    }
}