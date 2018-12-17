package finance.expense.total;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import finance.bot.Bot;
import finance.bot.user.BotUserService;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static com.pengrad.telegrambot.model.request.ParseMode.Markdown;
import static finance.update.InlineKeyboardUtils.getMyTotalMarkup;

@Component
public class MyTotalProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final BotUserService botUserService;
    private final Bot bot;

    public MyTotalProcessor(UpdateService updateService, BotUserService botUserService, Bot bot) {
        this.updateService = updateService;
        this.botUserService = botUserService;
        this.bot = bot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return updateService.getCallbackQueryData(update)
                .map(data -> data.equals("total_" + update.callbackQuery().from().id()))
                .orElse(false);
    }

    @Override
    public void process(Update update) {
        Message message = update.callbackQuery().message();
        int userId = update.callbackQuery().from().id();
        bot.execute(new EditMessageText(message.chat().id(), message.messageId(),
                "*" + botUserService.findById(userId).get().getShortName() + "*, select total option")
                .replyMarkup(getMyTotalMarkup(userId))
                .parseMode(Markdown));
    }
}
