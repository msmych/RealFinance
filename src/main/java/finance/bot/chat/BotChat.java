package finance.bot.chat;

import finance.bot.user.BotUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
public class BotChat {

    @Id public long id;
    @Column public String firstName;
    @Column public String lastName;
    @Column public String username;
    @Column public String title;
    @Column public boolean monthlyReport = false;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "bot_chat_bot_user",
            joinColumns = { @JoinColumn(name = "bot_chat_id") },
            inverseJoinColumns = { @JoinColumn(name = "bot_user_id")})
    public Set<BotUser> users = new HashSet<>();
}
