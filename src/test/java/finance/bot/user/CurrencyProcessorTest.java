package finance.bot.user;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyProcessorTest {

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final User user = mock(User.class);
    private final BotUserService botUserService = mock(BotUserService.class);
    private final CurrencyProcessor currencyProcessor = new CurrencyProcessor(botUserService);

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/usd");
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1);
    }

    @Test
    public void testMessageTextCommandCurrency() {
        assertTrue(currencyProcessor.appliesTo(update));
    }

    @Test
    public void testProcess() {
        boolean[] acts = {false};
        when(botUserService.updateDefaultCurrency(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
                .then(invocation -> {
                    acts[0] = true;
                    return new BotUser();
                });
        currencyProcessor.process(update);
        assertTrue(acts[0]);
    }
}