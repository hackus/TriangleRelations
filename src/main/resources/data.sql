create table "chat" (
    "id" bigint not null,
    primary key ("id")
);

create table "message" (
    "id" bigint not null,
    "value" varchar(255),
    "chat" bigint,
    "sender_id" bigint not null,
    primary key ("id")
);

create table "user" (
    "id" bigint not null,
    primary key ("id")
);

create table "user_chat" (
    "chat" bigint not null,
    "user" bigint not null,
    primary key ("chat", "user")
);

create sequence "chat_seq" start with 1 increment by 50;

create sequence "message_seq" start with 1 increment by 50;

create sequence "user_seq" start with 1 increment by 50;

alter table if exists "message"
   add constraint "FK6uevjmnm755ivvw2klevege38"
   foreign key ("chat")
   references "chat";

alter table if exists "message"
   add constraint "FK7m83l67ilb72af0lce8mkjhms"
   foreign key ("sender_id")
   references "user";

alter table if exists "user_chat"
   add constraint "FKt3kbnj7vj3sw0gt2ishjq04dx"
   foreign key ("user")
   references "user";

alter table if exists "user_chat"
   add constraint "FKqej3nwq4ras2c0ud0d13w7l40"
   foreign key ("chat")
   references "chat";
