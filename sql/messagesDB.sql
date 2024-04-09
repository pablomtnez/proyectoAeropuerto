/* DELETE 'messagesDB' database*/
DROP SCHEMA IF EXISTS messagesDB;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'root'@'localhost';

/* CREATE 'messagesDB' DATABASE */
CREATE SCHEMA messagesDB;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'root';

GRANT ALL ON messagesDB.* TO 'root'@'localhost';