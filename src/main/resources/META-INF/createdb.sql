-- first install and start a mysql server on your machine
-- then:
-- $ mysql -h localhost -uroot -p < src/main/resources/META-INF/createdb.sql

DROP USER IF EXISTS 'apiedi-web'@'localhost';
DROP DATABASE IF EXISTS apiedi;

CREATE USER 'apiedi-web'@'localhost' IDENTIFIED BY '1234';
CREATE DATABASE apiedi DEFAULT CHARACTER SET utf8;
GRANT INSERT,SELECT,UPDATE,DELETE,DROP,CREATE ON apiedi.* TO 'apiedi-web'@'localhost';
FLUSH PRIVILEGES;
