
CREATE SEQUENCE hibernate_sequence INCREMENT BY 1 START WITH 1;

create table el_persistent_token (
  series varchar(256),
  token_value VARCHAR(256),
  token_date DATE ,
  "user" varchar(50)
);

CREATE TABLE el_user (
  login VARCHAR(50) PRIMARY KEY ,
  password VARCHAR(256),
  name VARCHAR(256),
  email VARCHAR(256),
  activated boolean,
  lang_key VARCHAR(5),
  ACTIVATION_KEY VARCHAR(100),
  FACEBOOK_USER_ID VARCHAR(500),
  FACEBOOK_AUTH_TOKEN VARCHAR(500),
  status VARCHAR(25)
);

create table USER_AUTHORITIES (
  login VARCHAR(50) ,
  name VARCHAR(50)
);

create table TOURNAMENT (
  name VARCHAR(100),
  status VARCHAR(25),
  type VARCHAR(50),
  start_date TIMESTAMP,
  end_date TIMESTAMP
);

create table team (
  name VARCHAR(100),
  rank int,
  image VARCHAR(50)
);

create table LEAGUE (
  name VARCHAR(50),
  fee INT,
  tournament VARCHAR(100),
  owner VARCHAR(50),
  max_members INT,
  status VARCHAR(25),
  message VARCHAR(256)
);

create table USER_LEAGUE (
  login VARCHAR(50),
  name VARCHAR(50),
  status VARCHAR(25)
);