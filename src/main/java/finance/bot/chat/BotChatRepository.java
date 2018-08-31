package finance.bot.chat;

import finance.bot.chat.BotChat.ReportType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BotChatRepository extends CrudRepository<BotChat, Long> {

    @Query("select id from BotChat where reportType = 'MONTHLY'")
    List<Long> findIdsByMonthlyReport();

    @Query("select reportType from BotChat where id = :botChatId")
    Optional<ReportType> findReportTypeByBotChatId(@Param("botChatId") long botChatId);
}
