package finance.expense.total;

import com.pengrad.telegrambot.request.SendMessage;
import finance.bot.Bot;
import finance.bot.chat.BotChatService;
import finance.expense.ExpenseService;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class TotalTaskTest {

    private BotChatService bcs = mock(BotChatService.class);
    ExpenseService es = mock(ExpenseService.class);
    private Bot bot = mock(Bot.class);
    TotalTask tt = new TotalTask(bcs, es, bot);

    @Test
    public void reportingMonth() {
        List<Long> chatIds = asList(1L, 2L, 3L, 4L);
        when(bcs.getBotChatIdsForMonthlyReport()).thenReturn(chatIds);
        tt.reportMonth();
        verify(bcs).getBotChatIdsForMonthlyReport();
        verify(es, times(chatIds.size())).getAllTotalText(anyLong());
        verify(bot, times(chatIds.size())).execute(isA(SendMessage.class));
    }

}