alter table expense add column date timestamp;
update expense set date = current_date;
alter table bot_chat add column monthly_report boolean;
update bot_chat set monthly_report = false;
