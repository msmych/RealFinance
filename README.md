# Real finance
Personal finance Telegram bot

### Commands

`/amount [currency] [category]` - Save new expense.

Examples: `/100`, `/12.50 RUB`, `/0.45 usd`, `/777 🏊`, `/0.99 usd 🎉`

The default currency is Euro.
The default category is "Any".
Possible categories:

| Category | Emoji |
|----------|-------|
| House    | 🏠    |
| Food     | 🍞    |
| Health   | 💊    |
| Sport    | 🏊    |
| Fun      | 🎉    |
| Travel   | ✈️   |

/total - Total chat expenses

/clear - Clear chat expenses

`/currency` - Change user default currency.

Examples: `/USD`, `/rub`

###### How to run

Create schema:
```
sudo -i -u postgres
psql schema
```

Run application: `mvn spring-boot:run &`

View process: `top | grep java`

Kill process: `kill PID`

###### app.properties
```
token

#Datasource
spring.datasource.url
spring.datasource.username
spring.datasource.password
spring.datasource.driver-class-name=org.postgresql.Driver
#JPA
spring.jpa.hibernate.ddl-auto
#Flyway
spring.flyway.baseline-on-migrate
spring.flyway.baseline-version
spring.flyway.locations
```

### Links
[Java API for Telegram Bots and Gaming Platform](https://github.com/pengrad/java-telegram-bot-api)