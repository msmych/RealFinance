package finance.expense;

import finance.expense.total.ExpenseTotalCategory;
import finance.expense.total.ExpenseTotalCurrency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, currency as currency " +
                    "from Expense " +
                    "where bot_chat_id = :botChatId " +
                    "group by currency")
    List<ExpenseTotalCurrency> totalCurrencyByBotChatId(@Param("botChatId") long botChatId);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, category " +
                    "from Expense " +
                    "where bot_chat_id = :botChatId and currency = :currency " +
                    "group by category")
    List<ExpenseTotalCategory> totalCategoryByBotChatIdAndCurrency(@Param("botChatId") long botChatId,
                                                                   @Param("currency") String currency);

    void deleteByBotChatId(long botChatId);
}
