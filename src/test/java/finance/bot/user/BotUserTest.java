package finance.bot.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BotUserTest {

    BotUser botUser = new BotUser();

    @Test public void onlyUsername() {
        botUser.username = "Username";
        assertEquals("Username", botUser.getFullName());
    }

    @Test
    public void onlyFirstName() {
        botUser.firstName = "First";
        assertEquals("First", botUser.getFullName());
    }

    @Test
    public void onlyLastName() {
        botUser.lastName = "Last";
        assertEquals("Last", botUser.getFullName());
    }

    @Test
    public void firstNameAndUsername() {
        botUser.firstName = "First";
        botUser.username = "Username";
        assertEquals("First Username", botUser.getFullName());
    }

    @Test
    public void firstNameAndLastName() {
        botUser.firstName = "First";
        botUser.lastName = "Last";
        assertEquals("First Last", botUser.getFullName());
    }

    @Test
    public void usernameAndLastName() {
        botUser.username = "Username";
        botUser.lastName = "Last";
        assertEquals("Username Last", botUser.getFullName());
    }

    @Test
    public void firstNameUsernameLastName() {
        botUser.firstName = "First";
        botUser.username = "Username";
        botUser.lastName = "Last";
        assertEquals("First Username Last", botUser.getFullName());
    }
}