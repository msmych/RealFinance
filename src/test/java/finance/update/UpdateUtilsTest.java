package finance.update;

import com.pengrad.telegrambot.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateUtilsTest {

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
    public void testHasMessage() {
        when(update.message()).thenReturn(message);
        when(update.callbackQuery()).thenReturn(null);
        assertUpdate();
    }

    @Test
    public void testHasCallbackQuery() {
        when(update.message()).thenReturn(null);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        assertUpdate();
    }

    private void assertUpdate() {
        assertNotNull(UpdateUtils.getChat(update));
        assertNotNull(UpdateUtils.getFrom(update));
    }
}