package finance.expense;

import finance.expense.total.CurrencyCategoryExpenseTotal;
import finance.expense.total.CurrencyExpenseTotal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, currency " +
                    "from expense " +
                    "where bot_chat_id = :botChatId " +
                    "group by currency")
    List<CurrencyExpenseTotal> totalCurrencyByBotChatId(@Param("botChatId") long botChatId);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, category " +
                    "from expense " +
                    "where bot_chat_id = :botChatId and currency = :currency " +
                    "group by category")
    List<CurrencyCategoryExpenseTotal> totalCategoryByBotChatIdAndCurrency(@Param("botChatId") long botChatId,
                                                                           @Param("currency") String currency);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, currency " +
                    "from expense " +
                    "where bot_chat_id = :botChatId " +
                    "and date > :startDate " +
                    "and date < :endDate " +
                    "group by currency")
    List<CurrencyExpenseTotal> totalCurrencyByBotChatIdPeriod(@Param("botChatId") long botChatId,
                                                              @Param("startDate") Date startDate,
                                                              @Param("endDate") Date endDate);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, category " +
                    "from expense " +
                    "where bot_chat_id = :botChatId and currency = :currency " +
                    "and date > :startDate " +
                    "and date < :endDate " +
                    "group by category")
    List<CurrencyCategoryExpenseTotal> totalCategoryByBotChatIdAndCurrencyPeriod(@Param("botChatId") long botChatId,
                                                                                 @Param("currency") String currency,
                                                                                 @Param("startDate") Date startDate,
                                                                                 @Param("endDate") Date endDate);

    void deleteByBotChatId(long botChatId);

    Optional<Expense> findOneByBotChatIdAndMessageId(long botChatId, int messageId);
}
