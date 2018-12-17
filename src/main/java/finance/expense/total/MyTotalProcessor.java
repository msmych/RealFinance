package finance.expense.total;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import finance.bot.Bot;
import finance.update.UpdateService;
import finance.update.processor.UpdateProcessor;
import org.springframework.stereotype.Component;

import static finance.update.InlineKeyboardUtils.getMyTotalMarkup;

@Component
public class MyTotalProcessor implements UpdateProcessor {

    private final UpdateService updateService;
    private final Bot bot;

    public MyTotalProcessor(UpdateService updateService, Bot bot) {
        this.updateService = updateService;
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
        bot.execute(new EditMessageReplyMarkup(message.chat().id(), message.messageId())
                .replyMarkup(getMyTotalMarkup(update.callbackQuery().from().id())));
    }
}
