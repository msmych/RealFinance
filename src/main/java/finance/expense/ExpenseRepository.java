package finance.expense;

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
    List<AmountCurrencyExpenseTotal> totalCurrencyByBotChatId(@Param("botChatId") long botChatId);

    interface AmountCurrencyExpenseTotal {
        long getAmount();
        String getCurrency();
    }

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, category " +
                    "from expense " +
                    "where bot_chat_id = :botChatId and currency = :currency " +
                    "group by category")
    List<AmountCategoryExpenseTotal> totalCategoryByBotChatIdAndCurrency(@Param("botChatId") long botChatId,
                                                                         @Param("currency") String currency);

    interface AmountCategoryExpenseTotal {
        long getAmount();
        ExpenseCategory getCategory();
    }

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, currency " +
                    "from expense " +
                    "where bot_chat_id = :botChatId " +
                    "and date > :startDate and date < :endDate " +
                    "group by currency")
    List<AmountCurrencyExpenseTotal> totalCurrencyByBotChatIdPeriod(@Param("botChatId") long botChatId,
                                                                    @Param("startDate") Date startDate,
                                                                    @Param("endDate") Date endDate);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, category " +
                    "from expense " +
                    "where bot_chat_id = :botChatId and currency = :currency " +
                    "and date > :startDate and date < :endDate " +
                    "group by category")
    List<AmountCategoryExpenseTotal> totalCategoryByBotChatIdAndCurrencyPeriod(@Param("botChatId") long botChatId,
                                                                               @Param("currency") String currency,
                                                                               @Param("startDate") Date startDate,
                                                                               @Param("endDate") Date endDate);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, currency " +
                    "from expense " +
                    "where bot_chat_id = :botChatId " +
                    "and bot_user_id = :botUserId " +
                    "group by currency")
    List<AmountCurrencyExpenseTotal> totalCurrencyByBotChatIdAndBotUserId(@Param("botChatId") long botChatId,
                                                                          @Param("botUserId") int botUserId);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, category " +
                    "from expense " +
                    "where bot_chat_id = :botChatId and bot_user_id = :botUserId and currency = :currency " +
                    "group by category")
    List<AmountCategoryExpenseTotal> totalCategoryByBotChatIdAndBotUserId(@Param("botChatId") long botChatId,
                                                                          @Param("botUserId") int botUserId,
                                                                          @Param("currency") String currency);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, currency " +
                    "from expense " +
                    "where bot_chat_id = :botChatId and bot_user_id = :botUserId " +
                    "and date > :startDate and date < :endDate " +
                    "group by currency")
    List<AmountCurrencyExpenseTotal> totalCurrencyByBotChatIdAndBotUserIdPeriod(@Param("botChatId") long botChatId,
                                                                                @Param("botUserId") int botUserId,
                                                                                @Param("startDate") Date startDate,
                                                                                @Param("endDate") Date endDate);

    @Query(nativeQuery = true,
            value = "select sum(amount) as amount, category " +
                    "from expense " +
                    "where bot_chat_id = :botChatId and bot_user_id = :botUserId and currency = :currency " +
                    "and date > :startDate and date < :endDate " +
                    "group by category")
    List<AmountCategoryExpenseTotal>
            totalCategoryByBotChatIdAndBotUserIdAndCurrencyPeriod(@Param("botChatId") long botChatId,
                                                                  @Param("botUserId") int botUserId,
                                                                  @Param("currency") String currency,
                                                                  @Param("startDate") Date startDate,
                                                                  @Param("endDate") Date endDate);

    void deleteByBotChatId(long botChatId);

    void deleteByBotChatIdAndDateBefore(long botChatId, Date date);

    Optional<Expense> findOneByBotChatIdAndMessageId(long botChatId, int messageId);

    Optional<Expense> findTopByBotChatIdAndBotUserIdOrderByDateDesc(long botChatId, int botUserId);
}
