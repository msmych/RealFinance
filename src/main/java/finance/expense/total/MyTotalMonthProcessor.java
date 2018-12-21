package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.bot.user.BotUserService;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;

@Component
public class MyTotalMonthProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final BotUserService botUserService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public MyTotalMonthProcessor(UpdateService updateService,
                                 BotUserService botUserService,
                                 ExpenseService expenseService,
                                 Bot bot) {
        this.updateService = updateService;
        this.botUserService = botUserService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.getCallbackQueryData(update)
                .map(data -> data.equals("total_month_" + update.callbackQuery().from().id()))
                .orElse(false);
    }

    @Override
    public void process(Update update) {
        long chatId = update.callbackQuery().message().chat().id();
        int userId = update.callbackQuery().from().id();
        bot.execute(new EditMessageText(
                chatId,
                update.callbackQuery().message().messageId(),
                "#total *" + botUserService.findById(userId).get().getShortName() + "*'s month\n\n"
                        + expenseService.getMyTotalMonthText(chatId, userId))
                .parseMode(Markdown));
    }
}
