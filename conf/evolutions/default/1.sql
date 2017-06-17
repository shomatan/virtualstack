# --- !Ups

create table users (
  id SERIAL,
  username varchar(255) not null,
  password varchar(255) not null,
  gitbucket_token text,
  created_at bigint not null,
  updated_at bigint not null,
  primary key (id)
);

# --- !Downs

drop table users;