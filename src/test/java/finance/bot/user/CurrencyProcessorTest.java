package finance.bot.user;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CurrencyProcessorTest {

    private final BotUserService botUserService = mock(BotUserService.class);

    private final CurrencyProcessor currencyProcessor = new CurrencyProcessor(botUserService);

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final User user = mock(User.class);

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
        currencyProcessor.process(update);
        verify(botUserService).updateDefaultCurrency(anyInt(), anyString());
    }
}