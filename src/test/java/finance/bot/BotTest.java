package finance.bot;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BotTest {

    @Test
    public void testTelegramBotCreated() {
        Bot bot = new Bot("TOKEN", null);
        assertNotNull(bot.telegramBot());
    }
}