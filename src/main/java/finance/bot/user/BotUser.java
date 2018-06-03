package finance.bot.user;

import javax.persistence.*;

@Entity
public final class BotUser {

    @Id
    int id;

    @Column
    @Enumerated(EnumType.STRING)
    UserAction userAction;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String username;

}
