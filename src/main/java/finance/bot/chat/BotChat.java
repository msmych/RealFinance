package finance.bot.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public final class BotChat {

    @Id
    long id;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String username;

    @Column
    String title;


}
