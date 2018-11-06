package finance.bot.user;

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
