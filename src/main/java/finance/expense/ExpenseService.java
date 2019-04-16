package finance.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import finance.bot.chat.BotChatService;
import finance.bot.user.BotUser;
import finance.bot.user.BotUserService;
import finance.expense.total.TotalUtils;
import finance.expense.total.selector.*;
import org.joda.time.DateTime;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static finance.expense.CurrencyUtils.isCurrency;
import static finance.expense.ExpenseCategory.ANY;
import static finance.expense.ExpenseCategory.getByEmoji;
import static finance.expense.ExpenseUtils.parseAmount;
import static finance.expense.total.TotalUtils.formatTotalCurrency;
import static finance.update.UpdateUtils.*;

@Service
public class ExpenseService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ExpenseRepository expenseRepository;
    private final BotChatService botChatService;
    private final BotUserService botUserService;

    public ExpenseService(ExpenseRepository expenseRepository,
                          BotChatService botChatService,
                          BotUserService botUserService) {
        this.expenseRepository = expenseRepository;
        this.botChatService = botChatService;
        this.botUserService = botUserService;
    }

    @PostConstruct void moveExpensesToWastedCash() {
        expenseRepository.findAll().forEach(expense -> {
            WastedCashExpense wastedCashExpense = restTemplate.postForEntity(
                    "http://localhost:8080/expense",
                    new PostExpenseRequest() {{
                        userId = expense.botUser.id;
                        groupId = expense.botChat.id;
                        telegramMessageId = expense.messageId;
                        amount = expense.amount;
                    }},
                    WastedCashExpense.class)
                    .getBody();
            wastedCashExpense.currency = expense.currency;
            wastedCashExpense.category = expense.category.getName();
            wastedCashExpense.date = expense.date;
            restTemplate.put("http://localhost:8080/expense", wastedCashExpense);
        });
    }

    static class PostExpenseRequest {
        int userId;
        long groupId;
        int telegramMessageId;
        long amount;
    }

    static class WastedCashExpense {
        long id;
        int userId;
        long groupId;
        int telegramMessageId;
        long amount;
        String currency;
        String category;
        Date date;
    }

    public Expense save(Update update) {
        long chatId = getChat(update).id();
        int messageId = getMessage(update).get().messageId();
        Expense expense = getExpenseByBotChatIdAndMessageId(chatId, messageId)
                .orElse(new Expense());
        expense.botChat = botChatService.findById(chatId).get();
        BotUser botUser = botUserService.findById(getFrom(update).id()).get();
        expense.botUser = botUser;
        expense.messageId = messageId;
        String text = getText(update).get();
        expense.amount = parseAmount(text.split(" ")[0]);
        expense.currency = getCurrency(text)
                .orElse(botUser.defaultCurrency);
        expense.category = getCategory(text)
                .orElse(ANY);
        return expenseRepository.save(expense);
    }

    private Optional<ExpenseCategory> getCategory(String text) {
        String[] textParts = text.split(" ");
        if (textParts.length > 1) {
            Optional<ExpenseCategory> optionalExpenseCategory = getByEmoji(textParts[1]);
            if (optionalExpenseCategory.isPresent()) {
                return optionalExpenseCategory;
            }
            if (textParts.length > 2) {
                return getByEmoji(textParts[2]);
            }
        }
        return Optional.empty();
    }

    private Optional<String> getCurrency(String text) {
        String[] textParts = text.split(" ");
        if (textParts.length > 1) {
            String currencyUpperCase = textParts[1].toUpperCase();
            if (isCurrency(currencyUpperCase)) {
                return Optional.of(currencyUpperCase);
            }
        }
        return Optional.empty();
    }

    public String getAllTotalText(long botChatId) {
        return getTotalText(new AllExpenseTotalsSelector(expenseRepository, botChatId));
    }

    public String getAllMyTotalText(long botChatId, int botUserId) {
        return getTotalText(new AllMyExpenseTotalsSelector(expenseRepository, botChatId, botUserId));
    }

    public String getMonthlyReportText(long botChatId) {
        return getTotalText(new LastMonthExpenseTotalsSelector(expenseRepository, botChatId));
    }

    private String getTotalText(ExpenseTotalsSelector expenseTotalsSelector) {
        return expenseTotalsSelector.getCurrencyExpenseTotals().stream()
                .map(etc -> formatTotalCurrency(etc) +
                        "\n" + expenseTotalsSelector.getCurrencyCategoryExpenseTotals(etc.getCurrency()).stream()
                                .map(TotalUtils::formatTotalCategory)
                                .collect(Collectors.joining("\n")))
                .collect(Collectors.joining("\n\n"));
    }

    public String getTotalMonthText(long botChatId) {
        return getTotalText(new FromDateExpenseTotalsSelector(
                expenseRepository,
                botChatId,
                getThisMonthBeginning()));
    }

    public String getMyTotalMonthText(long botChatId, int botUserId) {
        return getTotalText(new FromDateMyExpenseTotalsSelector(
                expenseRepository,
                botChatId,
                botUserId,
                getThisMonthBeginning()));
    }

    private Date getThisMonthBeginning() {
        return new DateTime().withDayOfMonth(1).withMillisOfDay(0).toDate();
    }

    @Transactional
    public void deleteByBotChatId(long botChatId) {
        expenseRepository.deleteByBotChatId(botChatId);
    }

    public Optional<Expense> getExpenseByBotChatIdAndMessageId(long botChatId, int messageId) {
        return expenseRepository.findOneByBotChatIdAndMessageId(botChatId, messageId);
    }

    public Optional<Expense> getLastBotUserIdExpense(long botChatId, int botUserId) {
        return expenseRepository.findTopByBotChatIdAndBotUserIdOrderByDateDesc(botChatId, botUserId);
    }

    @Transactional
    public void deleteById(long expenseId) {
        if (expenseRepository.existsById(expenseId))
            expenseRepository.deleteById(expenseId);
    }

    @Transactional
    public void removeUpToThisMonth(long botChatId) {
        expenseRepository.deleteByBotChatIdAndDateBefore(botChatId, getThisMonthBeginning());
    }
}
