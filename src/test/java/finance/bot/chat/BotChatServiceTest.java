package finance.bot.chat;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import finance.bot.user.BotUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotChatServiceTest {

    private final String CHAT_FIRST_NAME = "chatFirstName";
    private final String CHAT_LAST_NAME = "chatLastName";
    private final String CHAT_USERNAME = "chatUsername";
    private final String CHAT_TITLE = "chatTitle";

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final BotChatRepository botChatRepository = mock(BotChatRepository.class);
    private final BotUserService botUserService = mock(BotUserService.class);
    private final BotChatService botChatService = new BotChatService(botChatRepository, botUserService);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(chat.firstName()).thenReturn(CHAT_FIRST_NAME);
        when(chat.lastName()).thenReturn(CHAT_LAST_NAME);
        when(chat.username()).thenReturn(CHAT_USERNAME);
        when(chat.title()).thenReturn(CHAT_TITLE);
    }

    @Test
    public void testSaveNewBotChat() {
        boolean[] acts = {false, false};
        setBotChatRepositoryAnswers(acts, Optional.empty());
        BotChat savedBotChat = botChatService.saveBotChat(update);
        assertSavedBotChat(acts, savedBotChat);
    }

    @Test
    public void testSaveExistingBotChat() {
        boolean[] acts = {false, false};
        BotChat existingBotChat = new BotChat();
        existingBotChat.id = 1L;
        setBotChatRepositoryAnswers(acts, Optional.of(existingBotChat));
        BotChat savedBotChat = botChatService.saveBotChat(update);
        assertSavedBotChat(acts, savedBotChat);
    }

    @Test
    public void testFindById() {
        BotChat botChat = new BotChat();
        botChat.id = 1L;
        when(botChatRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(botChat));
        assertEquals(1L, botChatService.findById(1L).get().id);
    }

    private void setBotChatRepositoryAnswers(boolean[] acts, Optional<BotChat> optionalBotChat) {
        when(botChatRepository.findById(ArgumentMatchers.anyLong()))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return optionalBotChat;
                });
        when(botChatRepository.save(ArgumentMatchers.isA(BotChat.class)))
                .then(invocationOnMock -> {
                    acts[1] = true;
                    return invocationOnMock.getArgument(0);
                });
    }

    private void assertSavedBotChat(boolean[] acts, BotChat savedBotChat) {
        assertArrayEquals(new boolean[]{true, true}, acts);
        assertEquals(1, savedBotChat.id);
        assertEquals(CHAT_FIRST_NAME, savedBotChat.firstName);
        assertEquals(CHAT_LAST_NAME, savedBotChat.lastName);
        assertEquals(CHAT_USERNAME, savedBotChat.username);
        assertEquals(CHAT_TITLE, savedBotChat.title);
        assertEquals(1, savedBotChat.users.size());
    }
}