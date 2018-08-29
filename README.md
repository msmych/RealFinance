# Real finance
*Personal finance Telegram bot*

### Commands

`/amount [currency] [category]` - Save new expense

Examples:
```
/100
/12.50 RUB
/0.45 usd
/36.6 💊
/10 usd 🍕
```
The default currency is Euro. The default category is *Any*.

Possible categories:

| Category | Emoji |
|----------|-------|
| Any:     | ☕️   |
| House    | 🏠    |
| Food     | 🍞🍕🍌 |
| Clothes  | 👔👠👖 |
| Health   | 💊💉🌡 |
| Education| 🎓📚 |
| Sport    | ⚽️🏸 |
| Fun      | 🎉🍺🎁 |
| Travel   | 🚕🚂✈️ |

To modify an expense, just modify its message

/total - Total chat expenses

/clear - Clear chat expenses

`/currency` - Change user default currency

Examples:
```
/USD
/rub
```

###### How to run

Access PostgreSQL:
```
sudo -i -u postgres
psql schema
```

Run application: `mvn spring-boot:run &`

View process: `top | grep java`

Kill process: `kill PID`

### Links
[Java API for Telegram Bots and Gaming Platform](https://github.com/pengrad/java-telegram-bot-api)