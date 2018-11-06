package finance.bot.user;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BotUserServiceTest {

    private final BotUserRepository botUserRepository = mock(BotUserRepository.class);

    private final BotUserService botUserService = new BotUserService(botUserRepository);

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final User user = mock(User.class);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1);
        when(user.firstName()).thenReturn("userFirstName");
        when(user.lastName()).thenReturn("userLastName");
        when(user.username()).thenReturn("userUsername");
        when(botUserRepository.save(isA(BotUser.class)))
                .then(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void testSaveNewBotUser() {
        BotUser savedBotUser = botUserService.saveBotUser(update);
        assertSavedBotUser(savedBotUser);
        assertEquals("EUR", savedBotUser.defaultCurrency);
    }

    @Test
    public void testSaveExistingBotUser() {
        BotUser existingBotUser = new BotUser();
        existingBotUser.id = 1;
        existingBotUser.defaultCurrency = "USD";
        when(botUserRepository.findById(anyInt())).thenReturn(Optional.of(existingBotUser));
        BotUser savedBotUser = botUserService.saveBotUser(update);
        assertSavedBotUser(savedBotUser);
        assertEquals("USD", savedBotUser.defaultCurrency);
    }

    @Test
    public void testFindById() {
        BotUser botUser = new BotUser();
        botUser.id = 1;
        when(botUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(botUser));
        assertEquals(1, botUserService.findById(1).get().id);
    }

    @Test
    public void testUpdateDefaultCurrency() {
        when(botUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(new BotUser()));
        when(botUserRepository.save(isA(BotUser.class)))
                .then(invocation -> {
                    BotUser savedBotUser = new BotUser();
                    savedBotUser.defaultCurrency =
                            ((BotUser) invocation.getArgument(0)).defaultCurrency;
                    return savedBotUser;
                });
        assertEquals("USD", botUserService.updateDefaultCurrency(1, "usd").defaultCurrency);
    }

    private void assertSavedBotUser(BotUser botUser) {
        assertEquals(1, botUser.id);
        assertEquals("userFirstName", botUser.firstName);
        assertEquals("userLastName", botUser.lastName);
        assertEquals("userUsername", botUser.username);
    }
}