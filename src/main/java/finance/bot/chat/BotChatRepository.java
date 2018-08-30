package finance.bot.chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BotChatRepository extends CrudRepository<BotChat, Long> {

    @Query("select id from BotChat where monthlyReport = true")
    List<Long> findIdByMonthlyReportTrue();
}
