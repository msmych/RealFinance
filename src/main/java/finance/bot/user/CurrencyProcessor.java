package finance.bot.user;

import com.pengrad.telegrambot.model.Update;
import finance.update.UpdateProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static finance.expense.CurrencyUtils.isCurrency;
import static finance.update.UpdateUtils.getFrom;
import static finance.update.UpdateUtils.getText;

@Component
public class CurrencyProcessor implements UpdateProcessor {

    private final BotUserService botUserService;

    public CurrencyProcessor(BotUserService botUserService) {
        this.botUserService = botUserService;
    }

    @Override
    public boolean appliesTo(Update update) {
        Optional<String> optionalText = getText(update);
        if (!optionalText.isPresent()) return false;
        String text = optionalText.get();
        return text.startsWith("/")
                && isCurrency(text.substring(1));
    }

    @Override
    public void process(Update update) {
        botUserService.updateDefaultCurrency(
                getFrom(update).id(),
                getText(update).get().substring(1));
    }
}
