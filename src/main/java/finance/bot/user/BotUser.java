package finance.bot.user;

import com.pengrad.telegrambot.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BotUser {

    @Id
    public int id;
    @Column
    public String firstName;
    @Column
    public String lastName;
    @Column
    public String username;
    @Column
    public String defaultCurrency = "EUR";

    public BotUser() {}

    public static BotUser fromId(int id) {
        BotUser botUser = new BotUser();
        botUser.id = id;
        return botUser;
    }

    public static BotUser fromUser(User user) {
        BotUser botUser = new BotUser();
        botUser.id = user.id();
        botUser.firstName = user.firstName();
        botUser.lastName = user.lastName();
        botUser.username = user.username();
        return botUser;
    }

    public String getShortName() {
        if (username != null)
            return username;
        if (firstName != null)
            return firstName;
        return lastName;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (firstName != null) {
            sb.append(firstName);
        }
        if (username != null) {
            if (firstName != null) {
                sb.append(" ");
            }
            sb.append(username);
        }
        if (lastName != null) {
            if (firstName != null || username != null) {
                sb.append(" ");
            }
            sb.append(lastName);
        }
        return sb.toString();
    }
}
