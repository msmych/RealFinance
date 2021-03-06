package finance.expense;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ExpenseModifyProcessorTest {

    private final ExpenseService expenseService = mock(ExpenseService.class);

    private final ExpenseModifyProcessor expenseModifyProcessor = new ExpenseModifyProcessor(expenseService);

    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);
    private final Chat chat = mock(Chat.class);

    @Test
    public void editedMessageApplies() {
        when(update.editedMessage()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/60");
        when(chat.id()).thenReturn(1L);
        when(expenseService.getExpenseByBotChatIdAndMessageId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(new Expense()));
        assertTrue(expenseModifyProcessor.appliesTo(update));
    }

    @Test
    public void process() {
        Expense expense = new Expense();
        expense.amount = 100;
        expenseModifyProcessor.process(update);
        verify(expenseService).save(isA(Update.class));
    }
}