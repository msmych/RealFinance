package finance.bot.user;

import javax.persistence.*;

@Entity
public final class BotUser {

    @Id
    public int id;

    @Column
    @Enumerated(EnumType.STRING)
    public UserAction userAction;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column
    public String username;

    @Column
    public String defaultCurrency = "EUR";
}
