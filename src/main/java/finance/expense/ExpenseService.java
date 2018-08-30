package finance.expense;

import com.pengrad.telegrambot.model.Update;
import finance.bot.chat.BotChatService;
import finance.bot.user.BotUser;
import finance.bot.user.BotUserService;
import finance.expense.total.TotalUtils;
import finance.expense.total.selector.AllExpenseTotalsSelector;
import finance.expense.total.selector.ExpenseTotalsSelector;
import finance.expense.total.selector.LastMonthExpenseTotalsSelector;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void deleteByBotChatId(long botChatId) {
        expenseRepository.deleteByBotChatId(botChatId);
    }

    public Optional<Expense> getExpenseByBotChatIdAndMessageId(long botChatId, int messageId) {
        return expenseRepository.findOneByBotChatIdAndMessageId(botChatId, messageId);
    }
}
