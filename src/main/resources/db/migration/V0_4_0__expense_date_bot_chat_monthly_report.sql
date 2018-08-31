alter table expense add column date timestamp;
update expense set date = current_date;
alter table bot_chat add column report_type varchar(255);
update bot_chat set report_type = 'NONE';
