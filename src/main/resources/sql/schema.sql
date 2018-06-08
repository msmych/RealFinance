create sequence expense_id_seq
	increment by 50
;

create table if not exists bot_chat
(
	id bigint not null
		constraint bot_chat_pkey
			primary key,
	first_name varchar(255),
	last_name varchar(255),
	title varchar(255),
	username varchar(255)
)
;

create table if not exists bot_user
(
	id integer not null
		constraint bot_user_pkey
			primary key,
	first_name varchar(255),
	last_name varchar(255),
	user_action varchar(255),
	username varchar(255)
)
;

create table if not exists bot_chat_bot_user
(
	bot_chat_id bigint not null
		constraint fk2608ujktmx97j0plnexy9eopb
			references bot_chat,
	bot_user_id integer not null
		constraint fk5pxd3ae32enrmygerclkmf7to
			references bot_user,
	constraint bot_chat_bot_user_pkey
		primary key (bot_chat_id, bot_user_id)
)
;

create table if not exists expense
(
	id bigint not null
		constraint expense_pkey
			primary key,
	amount integer,
	currency varchar(255),
	type varchar(255),
	bot_chat_id bigint
		constraint fko7iakdufufk59ro2pgp4k8qrx
			references bot_chat,
	bot_user_id integer
		constraint fkbkff7f7a2togeb9dshvoub9b6
			references bot_user
)
;