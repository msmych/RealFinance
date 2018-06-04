package finance.bot.chat;

import finance.bot.user.BotUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "bot_chat_bot_user",
            joinColumns = { @JoinColumn(name = "bot_chat_id") },
            inverseJoinColumns = { @JoinColumn(name = "bot_user_id") })
    Set<BotUser> users = new HashSet<>();
}
