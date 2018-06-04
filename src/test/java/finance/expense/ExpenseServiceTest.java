package finance.expense;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import finance.bot.chat.BotChat;
import finance.bot.chat.BotChatService;
import finance.bot.user.BotUser;
import finance.bot.user.BotUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseServiceTest {

    private final int AMOUNT = 1500;
    private final String EXPENSE_CURRENCY = "EUR";
    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final User user = mock(User.class);
    private final ExpenseRepository expenseRepository = mock(ExpenseRepository.class);
    private final BotChatService botChatService = mock(BotChatService.class);
    private final BotUserService botUserService = mock(BotUserService.class);
    private final ExpenseService expenseService = new ExpenseService(expenseRepository, botChatService, botUserService);

    @Before
    public void before() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1);
    }

    @Test
    public void testSaveExpense() {
        boolean[] acts = {false};
        setReturnsAndAnswers(acts);
        assertExpense(acts, expenseService.save(update, "1500"));
    }

    private void setReturnsAndAnswers(boolean[] acts) {
        setBotChatServiceFindByIdReturn();
        setBotUserServiceFindByIdReturn();
        setExpenseRepositorySaveAnswer(acts);
    }

    private void setExpenseRepositorySaveAnswer(boolean[] acts) {
        when(expenseRepository.save(ArgumentMatchers.isA(Expense.class)))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return invocationOnMock.getArgument(0);
                });
    }

    private void setBotUserServiceFindByIdReturn() {
        BotUser botUser = new BotUser();
        botUser.id = 1;
        when(botUserService.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(botUser));
    }

    private void setBotChatServiceFindByIdReturn() {
        BotChat botChat = new BotChat();
        botChat.id = 1L;
        when(botChatService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(botChat));
    }

    private void assertExpense(boolean[] acts, Expense expense) {
        assertTrue(acts[0]);
        assertEquals(chat.id().longValue(), expense.botChat.id);
        assertEquals(user.id().intValue(), expense.botUser.id);
        assertEquals(AMOUNT, expense.amount);
        assertEquals(EXPENSE_CURRENCY, expense.currency);
        assertEquals(ExpenseType.ANY, expense.type);
    }
}