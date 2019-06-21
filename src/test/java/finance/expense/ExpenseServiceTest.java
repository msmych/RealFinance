package finance.expense;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import finance.bot.chat.BotChat;
import finance.bot.chat.BotChatService;
import finance.bot.user.BotUser;
import finance.bot.user.BotUserService;
import finance.expense.ExpenseService.WastedCashExpense;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.Date;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static finance.expense.ExpenseCategory.FUN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class ExpenseServiceTest {

    private final ExpenseRepository expenseRepository = mock(ExpenseRepository.class);
    private final BotChatService botChatService = mock(BotChatService.class);
    private final BotUserService botUserService = mock(BotUserService.class);

    private final ExpenseService expenseService = new ExpenseService(expenseRepository, botChatService, botUserService, new ObjectMapper());

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);
    private final User user = mock(User.class);

    @ClassRule
    public static WireMockClassRule wireMockClassRule = new WireMockClassRule();

    public static void setUpStubs() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        wireMockClassRule.stubFor(post(urlEqualTo("/expense"))
                .withRequestBody(matchingJsonPath("$.userId"))
                .withRequestBody(matchingJsonPath("$.groupId"))
                .withRequestBody(matchingJsonPath("$.telegramMessageId"))
                .withRequestBody(matchingJsonPath("$.amount"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsString(
                                new WastedCashExpense() {{
                                    id = 1;
                                    userId = 2;
                                    groupId = 3;
                                    telegramMessageId = 4;
                                    amount = 1000;
                                    currency = "USD";
                                    category = "SHOPPING";
                                    date = new Date();
                                }}))));
    }

    @Before
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.messageId()).thenReturn(1);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(1);
        when(botChatService.findById(anyLong())).thenReturn(Optional.of(new BotChat(){{ id = 1L; }}));
        when(botUserService.findById(anyInt())).thenReturn(Optional.of(new BotUser(){{ id = 1; }}));
        when(expenseRepository.save(isA(Expense.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    public void testSaveExpense() {
        when(message.text()).thenReturn("/15");
        assertExpense(expenseService.save(update), "EUR");
    }

    @Test
    public void testSaveExpenseWithCurrency() {
        when(message.text()).thenReturn("/15 RUB");
        assertExpense(expenseService.save(update), "RUB");
    }

    @Test
    public void testSaveExpenseWithLowerCaseCurrency() {
        when(message.text()).thenReturn("/15 rub");
        assertExpense(expenseService.save(update), "RUB");
    }

    @Test
    public void testSaveExpenseWithCategory() {
        when(message.text()).thenReturn("/15 \uD83C\uDF89");
        assertExpense(expenseService.save(update), FUN);
    }

    @Test
    public void testDeleteByBotChatId() {
        expenseService.deleteByBotChatId(1L);
        verify(expenseRepository).deleteByBotChatId(anyLong());
    }

    @Test
    public void getExpenseByBotChatIdAndMessageId() {
        when(expenseRepository.findOneByBotChatIdAndMessageId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(new Expense()));
        assertTrue(expenseService.getExpenseByBotChatIdAndMessageId(1L, 1).isPresent());
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
        assertEquals(chat.id().longValue(), expense.botChat.id);
        assertEquals(user.id().intValue(), expense.botUser.id);
        assertEquals(message.messageId(), expense.messageId);
        assertEquals(1500, expense.amount);
    }


}