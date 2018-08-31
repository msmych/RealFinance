package finance.expense;

import finance.bot.chat.BotChat;
import finance.bot.user.BotUser;

import javax.persistence.*;
import java.util.Date;

import static finance.expense.ExpenseCategory.ANY;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Expense {

    @Id
    @GeneratedValue(generator = "expense_id_seq", strategy = SEQUENCE)
    @SequenceGenerator(name = "expense_id_seq", sequenceName = "expense_id_seq")
    public long id;

    @ManyToOne
    @JoinColumn(name = "bot_chat_id", referencedColumnName = "id")
    public BotChat botChat;

    @ManyToOne
    @JoinColumn(name = "bot_user_id", referencedColumnName = "id")
    public BotUser botUser;

    @Column public Integer messageId;
    @Column public int amount;
    @Column public String currency = "EUR";
    @Enumerated(STRING) public ExpenseCategory category = ANY;
    @Column public Date date = new Date();
}
