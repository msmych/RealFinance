package finance.update;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import finance.bot.user.BotUser;

public class InlineKeyboardUtils {

    public final static String TOTAL_ALL = "total_all";
    public final static String TOTAL_MONTH = "total_month";
    public final static String CLEAR_ALL_DATA = "clear_all";

    private final static InlineKeyboardButton TOTAL_MONTH_BUTTON =
            new InlineKeyboardButton("This month").callbackData(TOTAL_MONTH);
    private final static InlineKeyboardButton TOTAL_ALL_BUTTON =
            new InlineKeyboardButton("All chat").callbackData(TOTAL_ALL);

    public final static InlineKeyboardButton CLEAR_ALL_BUTTON =
            new InlineKeyboardButton("All chat").callbackData(CLEAR_ALL_DATA);


    public static InlineKeyboardMarkup getTotalMarkup(BotUser botUser) {
        return new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {TOTAL_MONTH_BUTTON},
                {new InlineKeyboardButton(botUser.getShortName()).callbackData("total_" + botUser.id)},
                {TOTAL_ALL_BUTTON}});
    }
}
