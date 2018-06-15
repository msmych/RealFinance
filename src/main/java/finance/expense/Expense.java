package finance.expense;

import finance.bot.chat.BotChat;
import finance.bot.user.BotUser;

import javax.persistence.*;

@Entity
public final class Expense {

    @Id
    @GeneratedValue(generator = "expense_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "expense_id_seq", sequenceName = "expense_id_seq")
    public long id;

    @ManyToOne
    @JoinColumn(name = "bot_chat_id", referencedColumnName = "id")
    public BotChat botChat;

    @ManyToOne
    @JoinColumn(name = "bot_user_id", referencedColumnName = "id")
    public BotUser botUser;

    @Column
    public int amount;

    @Column
    public String currency = "EUR";

    @Column
    @Enumerated(EnumType.STRING)
    public ExpenseCategory category = ExpenseCategory.ANY;
}
