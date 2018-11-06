package finance.bot.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.bot.user.BotUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotChatServiceTest {

    private final BotChatRepository botChatRepository = mock(BotChatRepository.class);
    private final BotUserService botUserService = mock(BotUserService.class);

    private final BotChatService botChatService = new BotChatService(botChatRepository, botUserService);

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(chat.firstName()).thenReturn("chatFirstName");
        when(chat.lastName()).thenReturn("chatLastName");
        when(chat.username()).thenReturn("chatUsername");
        when(chat.title()).thenReturn("chatTitle");
        when(botChatRepository.save(isA(BotChat.class)))
                .then(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void testSaveNewBotChat() {
        assertSavedBotChat(botChatService.saveBotChat(update));
    }

    @Test
    public void testSaveExistingBotChat() {
        BotChat existingBotChat = new BotChat();
        existingBotChat.id = 1L;
        assertSavedBotChat(botChatService.saveBotChat(update));
    }

    @Test
    public void testFindById() {
        BotChat botChat = new BotChat();
        botChat.id = 1L;
        when(botChatRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(botChat));
        assertEquals(1L, botChatService.findById(1L).get().id);
    }

    private void assertSavedBotChat(BotChat savedBotChat) {
        assertEquals(1, savedBotChat.id);
        assertEquals("chatFirstName", savedBotChat.firstName);
        assertEquals("chatLastName", savedBotChat.lastName);
        assertEquals("chatUsername", savedBotChat.username);
        assertEquals("chatTitle", savedBotChat.title);
        assertEquals(1, savedBotChat.users.size());
    }
}