/* ELIMINAR la base de datos 'messagesDB' si existe */
DROP DATABASE IF EXISTS messagesDB;

/* CREAR la base de datos 'messagesDB' */
CREATE DATABASE messagesDB;

/* CREAR un NUEVO USUARIO (si pretendías crear 'spq' en lugar de usar 'root') */
CREATE USER IF NOT EXISTS 'root´'@'localhost' IDENTIFIED BY 'root';

/*  TODOS LOS PRIVILEGIOS sobre 'messagesDB' AL NUEVO USUARIO */
GRANT ALL PRIVILEGES ON messagesDB.* TO 'root´'@'localhost';

FLUSH PRIVILEGES;
