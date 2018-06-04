package finance.bot.user;

import org.junit.Test;

import java.util.Arrays;
import java.util.Currency;

public class CurrencyLearningTest {

    @Test
    public void testCurrencies() {
        Currency eur = Currency.getInstance("EUR");
        Currency rub = Currency.getInstance("RUB");
        Currency usd = Currency.getInstance("USD");
        showCurrencies(eur, rub, usd);
    }

    private void showCurrencies(Currency... currencies) {
        Arrays.asList(currencies).forEach(currency ->
                System.out.println(
                        currency.getCurrencyCode()
                        + " " + currency.getSymbol()
                        + " " + currency.getDisplayName()));
    }
}
