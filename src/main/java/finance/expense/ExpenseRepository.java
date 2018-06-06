package finance.expense;

import finance.expense.total.ExpenseTotal;
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
    List<ExpenseTotal> totalByBotChatId(@Param("botChatId") long botChatId);
}
