/* ELIMINAR la base de datos 'messagesDB' si existe */
DROP DATABASE IF EXISTS messagesDB;

/* CREAR la base de datos 'messagesDB' */
CREATE DATABASE messagesDB;

/* CREAR un NUEVO USUARIO (si pretendías crear 'spq' en lugar de usar 'root') */
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'root';

/*  TODOS LOS PRIVILEGIOS sobre 'messagesDB' AL NUEVO USUARIO */
GRANT ALL PRIVILEGES ON messagesDB.* TO 'root'@'localhost';

FLUSH PRIVILEGES;

USE messagesDB;

CREATE TABLE IF NOT EXISTS Flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    airline VARCHAR(100) NOT NULL,
    plane VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    price FLOAT NOT NULL
);

INSERT INTO Flights (code, origin, destination, airline, plane, duration, price)
VALUES
    ('FL001', 'New York', 'Los Angeles', 'Delta Airlines', 'Boeing 737', 360, 300.50),
    ('FL002', 'Los Angeles', 'Chicago', 'American Airlines', 'Airbus A320', 240, 250.75),
    ('FL003', 'Chicago', 'Miami', 'United Airlines', 'Boeing 787', 180, 400.25),
    ('FL004', 'Miami', 'Houston', 'Southwest Airlines', 'Boeing 737', 120, 180.00),
    ('FL005', 'Houston', 'Denver', 'Frontier Airlines', 'Airbus A319', 180, 220.00);