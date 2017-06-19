# --- !Ups

create table users (
  id bigserial,
  firstName VARCHAR,
  lastName VARCHAR,
  fullName VARCHAR,
  email VARCHAR,
  avatarURL VARCHAR,
  gitbucket_token text,
  created_at bigint not null,
  updated_at bigint not null,
  primary key (id)
);

create table login_infos (
  id bigserial,
  provider_id VARCHAR NOT NULL,
  provider_key VARCHAR NOT NULL,
  primary key (id)
);

create table user_login_infos (
  user_id VARCHAR NOT NULL,
  login_info_id BIGINT NOT NULL
);

create table password_infos (
  hasher VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  salt VARCHAR,
  login_info_id BIGINT NOT NULL
);

# --- !Downs

drop table password_infos;
drop table user_login_infos;
drop table login_infos;
drop table users;