create table bot_chat
(
  id         bigint not null
    constraint bot_chat_pkey
    primary key,
  first_name varchar(255),
  last_name  varchar(255),
  title      varchar(255),
  username   varchar(255)
);

create table bot_user
(
  id          integer not null
    constraint bot_user_pkey
    primary key,
  first_name  varchar(255),
  last_name   varchar(255),
  user_action varchar(255),
  username    varchar(255)
);

create table bot_chat_bot_user
(
  bot_chat_id bigint  not null
    constraint fk2608ujktmx97j0plnexy9eopb
    references bot_chat,
  bot_user_id integer not null
    constraint fk5pxd3ae32enrmygerclkmf7to
    references bot_user,
  constraint bot_chat_bot_user_pkey
  primary key (bot_chat_id, bot_user_id)
);

