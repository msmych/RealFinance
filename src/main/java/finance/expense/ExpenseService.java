package finance.expense;

import com.pengrad.telegrambot.model.Update;
import finance.bot.chat.BotChatService;
import finance.bot.user.BotUserService;
import finance.expense.total.ExpenseTotal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static finance.update.UpdateUtils.getChat;
import static finance.update.UpdateUtils.getFrom;

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

    public Expense save(Update update, int amount) {
        Expense expense = new Expense();
        expense.botChat = botChatService.findById(getChat(update).id()).get();
        expense.botUser = botUserService.findById(getFrom(update).id()).get();
        expense.amount = amount;
        return expenseRepository.save(expense);
    }

    public List<ExpenseTotal> getTotal(long botChatId) {
        return expenseRepository.totalByBotChatId(botChatId);
    }

    @Transactional
    public void deleteByBotChatId(long botChatId) {
        expenseRepository.deleteByBotChatId(botChatId);
    }
}
