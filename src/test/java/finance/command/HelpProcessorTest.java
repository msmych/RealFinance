package finance.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import finance.bot.Bot;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpProcessorTest {

    private final String HELP = "/help";
    private final String BOT_USERNAME = "RealFinanceBot";
    private final long CHAT_ID = 0L;

    private final TelegramBot telegramBot = mock(TelegramBot.class);
    private final HelpProcessor helpProcessor = new HelpProcessor(telegramBot, "help");

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final User user = mock(User.class);
    private final Chat chat = mock(Chat.class);
    private final SendResponse sendResponse = mock(SendResponse.class);

    @Before
    public void before() {
        Bot.user = user;
        when(user.username()).thenReturn(BOT_USERNAME);
    }

    @Test
    public void testCommandHelp() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(HELP);
        assertTrue(helpProcessor.appliesTo(update));
    }

    @Test
    public void testCommandHelpAtBotUsername() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(HELP + "@" + BOT_USERNAME);
        assertTrue(helpProcessor.appliesTo(update));
    }

    @Test
    public void testNoMessage() {
        when(update.message()).thenReturn(null);
        assertFalse(helpProcessor.appliesTo(update));
    }

    @Test
    public void testNoText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(null);
        assertFalse(helpProcessor.appliesTo(update));
    }

    @Test
    public void testWrongText() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("WRONG");
        assertFalse(helpProcessor.appliesTo(update));
    }

    @Test
    public void testProcess() {
        List<SendMessage> requests = new ArrayList<>();
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(CHAT_ID);
        when(telegramBot.execute(ArgumentMatchers.isA(SendMessage.class)))
                .then(invocationOnMock -> {
                    Arrays.stream(invocationOnMock.getArguments()).forEach(
                            argument -> requests.add((SendMessage) argument));
                    return sendResponse;
                });
        helpProcessor.process(update);
        assertEquals(1, requests.size());
    }
}