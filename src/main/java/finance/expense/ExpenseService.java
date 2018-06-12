package finance.expense;

import com.pengrad.telegrambot.model.Update;
import finance.bot.chat.BotChatService;
import finance.bot.user.BotUser;
import finance.bot.user.BotUserService;
import finance.expense.total.ExpenseTotal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static finance.expense.CurrencyUtils.isCurrency;
import static finance.expense.ExpenseUtils.parseAmount;
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
        Expense expense = new Expense();
        expense.botChat = botChatService.findById(getChat(update).id()).get();
        BotUser botUser = botUserService.findById(getFrom(update).id()).get();
        expense.botUser = botUser;
        String text = getText(update).get();
        expense.amount = parseAmount(text.split(" ")[0]);
        expense.currency = getCurrency(text)
                .orElse(botUser.defaultCurrency);
        return expenseRepository.save(expense);
    }

    private Optional<String> getCurrency(String text) {
        String[] textParts = text.split(" ");
        if (textParts.length > 1 && isCurrency(textParts[1].toUpperCase()))
            return Optional.of(textParts[1].toUpperCase());
        return Optional.empty();
    }

    public List<ExpenseTotal> getTotal(long botChatId) {
        return expenseRepository.totalByBotChatId(botChatId);
    }

    @Transactional
    public void deleteByBotChatId(long botChatId) {
        expenseRepository.deleteByBotChatId(botChatId);
    }
}
