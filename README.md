# Real finance
Personal finance Telegram bot

### Commands

`/amount` - Save new expense. Examples: `/100`, `/12.50`, `/0.45`

/total - Total chat expenses

/clear - Clear chat expenses

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
```

### Links
[Java API for Telegram Bots and Gaming Platform](https://github.com/pengrad/java-telegram-bot-api)