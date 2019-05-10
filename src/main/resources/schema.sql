CREATE TABLE shop (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255)  DEFAULT NULL,
  url varchar(255)  DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE theme (
  id int(11) NOT NULL AUTO_INCREMENT,
  theme varchar(255)  DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE reader (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255)  DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE book (
  id int(11) NOT NULL AUTO_INCREMENT,
  author varchar(255) DEFAULT NULL,
  locations int(11) NOT NULL,
  title varchar(255) DEFAULT NULL,
  theme_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE reading_progress (
  reading_end date DEFAULT NULL,
  reading_start date NOT NULL,
  key_book int(11) NOT NULL,
  key_reader int(11) NOT NULL,
  PRIMARY KEY (key_book,key_reader)
);

CREATE TABLE shopping (
  buy_date date DEFAULT NULL,
  price decimal(19,2) DEFAULT NULL,
  key_book int(11) NOT NULL,
  key_reader int(11) NOT NULL,
  key_shop int(11) NOT NULL,
  PRIMARY KEY (key_book,key_reader,key_shop)
);
