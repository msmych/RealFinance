package finance.expense;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query("select sum(amount) from Expense where botChat.id = :chatId")
    Optional<Long> sumAmountByBotChat(@Param("chatId") long chatId);
}
