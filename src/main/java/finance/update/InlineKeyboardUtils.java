package finance.update;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import finance.bot.user.BotUser;

public class InlineKeyboardUtils {

    public final static String TOTAL_ALL = "total_all";
    public final static String TOTAL_MONTH = "total_month";


    private final static InlineKeyboardButton TOTAL_MONTH_BUTTON =
            new InlineKeyboardButton("This month").callbackData(TOTAL_MONTH);
    private final static InlineKeyboardButton TOTAL_ALL_BUTTON =
            new InlineKeyboardButton("All chat").callbackData(TOTAL_ALL);

    public final static String CLEAR_ALL_DATA = "clear_all";
    public final static String CLEAR_UP_TO_THIS_MONTH_DATA = "clear_up_to_this_month";
    public final static InlineKeyboardButton CLEAR_UP_TO_THIS_MONTH_BUTTON =
            new InlineKeyboardButton("Up to this month").callbackData(CLEAR_UP_TO_THIS_MONTH_DATA);
    public final static InlineKeyboardButton CLEAR_ALL_BUTTON =
            new InlineKeyboardButton("All chat").callbackData(CLEAR_ALL_DATA);

    public static InlineKeyboardMarkup getTotalMarkup(BotUser botUser) {
        return new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {TOTAL_MONTH_BUTTON},
                {new InlineKeyboardButton(botUser.getShortName()).callbackData("total_" + botUser.id)},
                {TOTAL_ALL_BUTTON}});
    }

    public static InlineKeyboardMarkup getMyTotalMarkup(int botUserId) {
        return new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {new InlineKeyboardButton("All chat").callbackData("total_all_" + botUserId)}});
    }
}
