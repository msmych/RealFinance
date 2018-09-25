# Real finance
*Personal finance Telegram bot*

### Commands

`/amount [currency] [category]` - Save new expense

Examples:
```
/100
/12.50 RUB
/0.45 usd
/36.60 💊
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

`/total` -- Total chat expenses

`/my_total` -- User chat expenses

`/clear` -- Clear chat expenses

/reports -- Chat reports settings
  
If you want to receive monthly reports, check *Monthly* option

`/currency` -- Change user default currency

Examples:
```
/USD
/rub
```

### Links
[Java API for Telegram Bots and Gaming Platform](https://github.com/pengrad/java-telegram-bot-api)