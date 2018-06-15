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

import java.util.Arrays;
import java.util.Optional;

import static finance.expense.ExpenseCategory.FUN;
import static finance.expense.ExpenseCategory.SPORT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ExpenseServiceTest {

    private final int AMOUNT = 1500;
    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final User user = mock(User.class);
    private final ExpenseRepository expenseRepository = mock(ExpenseRepository.class);
    private final BotChatService botChatService = mock(BotChatService.class);
    private final BotUserService botUserService = mock(BotUserService.class);
    private final ExpenseService expenseService = new ExpenseService(expenseRepository, botChatService, botUserService);

    private boolean[] acts;

    @Before
    public void before() {
        acts = new boolean[]{false};
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1);
    }

    @Test
    public void testSaveExpense() {
        when(message.text()).thenReturn("/15");
        setReturnsAndAnswers();
        assertExpense(expenseService.save(update), "EUR");
    }

    @Test
    public void testSaveExpenseWithCurrency() {
        when(message.text()).thenReturn("/15 RUB");
        setReturnsAndAnswers();
        assertExpense(expenseService.save(update), "RUB");
    }

    @Test
    public void testSaveExpenseWithLowerCaseCurrency() {
        when(message.text()).thenReturn("/15 rub");
        setReturnsAndAnswers();
        assertExpense(expenseService.save(update), "RUB");
    }

    @Test
    public void testSaveExpenseWithCategory() {
        when(message.text()).thenReturn("/15 \uD83C\uDF89");
        setReturnsAndAnswers();
        assertExpense(expenseService.save(update), FUN);
    }

    @Test
    public void testSaveExpenseWithCurrencyAndCategory() {
        when(message.text()).thenReturn("/15 usd \uD83C\uDFCA");
        setReturnsAndAnswers();
        assertExpense(expenseService.save(update), "USD", SPORT);
    }

    @Test
    public void testGetTotalCurrencyByChatId() {
        when(expenseRepository.totalCurrencyByBotChatId(ArgumentMatchers.anyLong()))
                .thenReturn(Arrays.asList(null, null, null));
        assertEquals(3, expenseService.getTotalCurrency(1L).size());
    }

    @Test
    public void testGetTotalCategoryByChatIdAndCurrency() {
        when(expenseRepository.totalCategoryByBotChatIdAndCurrency(
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()))
                .thenReturn(Arrays.asList(null, null, null));
        assertEquals(3, expenseService.getTotalCategory(1L, "USD").size());
    }

    @Test
    public void testDeleteByBotChatId() {
        doAnswer(invocationOnMock -> acts[0] = true)
                .when(expenseRepository).deleteByBotChatId(ArgumentMatchers.anyLong());
        expenseService.deleteByBotChatId(1L);
        assertTrue(acts[0]);
    }

    private void setReturnsAndAnswers() {
        setBotChatServiceFindByIdReturn();
        setBotUserServiceFindByIdReturn();
        setExpenseRepositorySaveAnswer();
    }

    private void setExpenseRepositorySaveAnswer() {
        when(expenseRepository.save(ArgumentMatchers.isA(Expense.class)))
                .then(invocationOnMock -> {
                    acts[0] = true;
                    return invocationOnMock.getArgument(0);
                });
    }

    private void setBotUserServiceFindByIdReturn() {
        BotUser botUser = new BotUser();
        botUser.id = 1;
        botUser.defaultCurrency = "EUR";
        when(botUserService.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(botUser));
    }

    private void setBotChatServiceFindByIdReturn() {
        BotChat botChat = new BotChat();
        botChat.id = 1L;
        when(botChatService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(botChat));
    }

    private void assertExpense(Expense expense, String expectedCurrency, ExpenseCategory expectedExpenseCategory) {
        assertExpense(expense, expectedCurrency);
        assertEquals(expectedExpenseCategory, expense.category);
    }

    private void assertExpense(Expense expense, String expectedCurrency) {
        assertExpenseBase(expense);
        assertEquals(expectedCurrency, expense.currency);
    }

    private void assertExpense(Expense expense, ExpenseCategory expectedExpenseCategory) {
        assertExpenseBase(expense);
        assertEquals(expectedExpenseCategory, expense.category);
    }

    private void assertExpenseBase(Expense expense) {
        assertTrue(acts[0]);
        assertEquals(chat.id().longValue(), expense.botChat.id);
        assertEquals(user.id().intValue(), expense.botUser.id);
        assertEquals(AMOUNT, expense.amount);
    }
}