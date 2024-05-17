-- Crear la base de datos
CREATE DATABASE biblioteca_bd;

-- Conectar a la base de datos
\c biblioteca_bd;

-- Crear la tabla Autor
CREATE TABLE Autor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    nacionalidad VARCHAR(255) NOT NULL,
    anio_nacimiento INT NOT NULL
);

-- Crear la tabla Libro
CREATE TABLE Libro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor_id INT REFERENCES Autor(id),
    anioPublicacion INT NOT NULL,
    isbn VARCHAR(50) NOT NULL,
    genero VARCHAR(100) NOT NULL
);
