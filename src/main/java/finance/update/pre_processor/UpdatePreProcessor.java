package finance.update.pre_processor;

import com.pengrad.telegrambot.model.Update;

public interface UpdatePreProcessor {
    void preProcess(Update update);
}
