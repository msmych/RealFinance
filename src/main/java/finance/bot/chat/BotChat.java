package finance.bot.chat;

import finance.bot.user.BotUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public final class BotChat {

    @Id
    public long id;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column
    public String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bot_chat_bot_user",
            joinColumns = { @JoinColumn(name = "bot_chat_id") },
            inverseJoinColumns = { @JoinColumn(name = "bot_user_id")})
    public Set<BotUser> users = new HashSet<>();
}
