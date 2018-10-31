package finance.update.processor;

import com.pengrad.telegrambot.model.Update;

public interface UpdateProcessor {

    boolean appliesTo(Update update);

    void process(Update update);
}
