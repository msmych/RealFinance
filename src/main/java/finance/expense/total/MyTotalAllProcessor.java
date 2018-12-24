package finance.expense.total;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.bot.user.BotUser;
import finance.expense.ExpenseService;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;

@Component
public class MyTotalAllProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final ExpenseService expenseService;
    private final Bot bot;

    public MyTotalAllProcessor(UpdateService updateService, ExpenseService expenseService, Bot bot) {
        this.updateService = updateService;
        this.expenseService = expenseService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.getCallbackQueryData(update)
                .map(data -> data.equals("total_all_" + update.callbackQuery().from().id()))
                .orElse(false);
    }

    @Override
    public void process(Update update) {
        long chatId = update.callbackQuery().message().chat().id();
        BotUser botUser = BotUser.fromUser(update.callbackQuery().from());
        bot.execute(new EditMessageText(chatId, update.callbackQuery().message().messageId(),
                "#total " + botUser.getShortName() + "\n\n" +
                expenseService.getAllMyTotalText(chatId, botUser.id))
                .parseMode(Markdown));
    }
}
