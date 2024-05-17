package com.usta.dao.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usta.dao.baseDAO;
import com.usta.dao.Autor.AutorDAOImpl;
import com.usta.model.Autor;
import com.usta.model.Libro;
import com.usta.model.Pais;
import com.usta.utils.ConexionPOSTGRES;

public class LibroDAOImpl implements baseDAO<Libro> {
    private Connection conexion;
    private AutorDAOImpl autorDao = new AutorDAOImpl();;

    public LibroDAOImpl() {
        try {
            // this.conexion = ConexionMySQL.obtenerConexion();
            this.conexion = ConexionPOSTGRES.obtenerConexion();
        } catch (SQLException e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos", e);
        }
    }

    // Implementaciones de métodos CRUD
    @Override
    public void insertar(Libro libro) {
        // Lógica para insertar un libro en la base de datos
        String sql = "INSERT INTO libro (titulo, autor_id, aniopublicacion, isbn, genero) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, libro.getTitulo());
            statement.setInt(2, libro.getAutor().getId());
            statement.setInt(3, libro.getAñoPublicacion());
            statement.setString(4, libro.getIsbn());
            statement.setString(5, libro.getGenero());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Libro buscarPorId(int id) {
        // Lógica para buscar un libro por ID en la base de datos
        String sql = "SELECT * FROM libro WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setId(resultSet.getInt("id"));
                    libro.setTitulo(resultSet.getString("titulo"));
                    libro.setAutor(autorDao.buscarPorId(resultSet.getInt("autor_id")));
                    libro.setAñoPublicacion(resultSet.getInt("aniopublicacion"));
                    libro.setIsbn(resultSet.getString("isbn"));
                    libro.setGenero(resultSet.getString("genero"));

                    return libro;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Libro> buscarTodos() {
        // Lógica para buscar todos los autores en la base de datos
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro";
        try (PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Libro libro = new Libro();
                libro.setId(resultSet.getInt("id"));
                libro.setTitulo(resultSet.getString("titulo"));
                libro.setAutor(autorDao.buscarPorId(resultSet.getInt("autor_id")));
                libro.setAñoPublicacion(resultSet.getInt("aniopublicacion"));
                libro.setIsbn(resultSet.getString("isbn"));
                libro.setGenero(resultSet.getString("genero"));

                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    @Override
    public void actualizar(Libro libro) {
        // Lógica para actualizar un libro en la base de datos
        String sql = "UPDATE libro SET titulo = ?, autor_id = ?, aniopublicacion = ?, isbn = ?,genero = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, libro.getTitulo());
            statement.setInt(2, libro.getAutor().getId());
            statement.setInt(3, libro.getAñoPublicacion());
            statement.setString(4, libro.getIsbn());
            statement.setString(5, libro.getGenero());
            statement.setInt(6,libro.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        // Lógica para eliminar un libro por ID en la base de datos
        String sql = "DELETE FROM libro WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
