package finance.bot.user;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotUserServiceTest {

    private final String USER_FIRST_NAME = "userFirstName";
    private final String USER_LAST_NAME = "userLastName";
    private final String USER_USERNAME = "userUsername";

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final User user = mock(User.class);
    private final BotUserRepository botUserRepository = mock(BotUserRepository.class);
    private final BotUserService botUserService = new BotUserService(botUserRepository);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1);
        when(user.firstName()).thenReturn(USER_FIRST_NAME);
        when(user.lastName()).thenReturn(USER_LAST_NAME);
        when(user.username()).thenReturn(USER_USERNAME);
    }

    @Test
    public void testSaveNewBotUser() {
        boolean[] acts = {false, false};
        setBotUserRepositoryAnswers(acts, Optional.empty());
        BotUser savedBotUser = botUserService.saveBotUser(update);
        assertSavedBotUser(acts, savedBotUser, UserAction.NONE);
    }

    @Test
    public void testSaveExistingBotUser() {
        boolean[] acts = {false, false};
        BotUser existingBotUser = new BotUser();
        existingBotUser.id = 1;
        setBotUserRepositoryAnswers(acts, Optional.of(existingBotUser));
        BotUser savedBotUser = botUserService.saveBotUser(update);
        assertSavedBotUser(acts, savedBotUser, null);
    }

    private void assertSavedBotUser(boolean[] acts, BotUser botUser, UserAction expectedUserAction) {
        assertArrayEquals(new boolean[]{true, true}, acts);
        assertEquals(1, botUser.id);
        assertEquals(expectedUserAction, botUser.userAction);
        assertEquals(USER_FIRST_NAME, botUser.firstName);
        assertEquals(USER_LAST_NAME, botUser.lastName);
        assertEquals(USER_USERNAME, botUser.username);
    }

    private void setBotUserRepositoryAnswers(boolean[] acts, Optional<BotUser> optionalBotUser) {
        when(botUserRepository.findById(ArgumentMatchers.anyInt()))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return optionalBotUser;
                });
        when(botUserRepository.save(ArgumentMatchers.isA(BotUser.class)))
                .then(invocationOnMock -> {
                    acts[1] = true;
                    return invocationOnMock.getArgument(0);
                });
    }
}