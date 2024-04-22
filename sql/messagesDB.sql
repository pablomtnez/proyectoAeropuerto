/* ELIMINAR la base de datos 'messagesDB' si existe */
DROP DATABASE IF EXISTS messagesDB;

/* ELIMINAR el usuario Usuario */
DROP USER IF EXISTS 'usuario'@'localhost';

/* CREAR la base de datos 'messagesDB' */
CREATE DATABASE messagesDB;

/* CREAR un NUEVO USUARIO (si pretendías crear 'spq' en lugar de usar 'root') */
CREATE USER IF NOT EXISTS 'usuario'@'localhost' IDENTIFIED BY 'usuario';

/*  TODOS LOS PRIVILEGIOS sobre 'messagesDB' AL NUEVO USUARIO */
GRANT ALL PRIVILEGES ON messagesDB.* TO 'usuario'@'localhost';

FLUSH PRIVILEGES;

USE messagesDB;