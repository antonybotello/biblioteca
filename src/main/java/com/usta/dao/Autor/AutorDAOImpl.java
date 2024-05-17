package com.usta.dao.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usta.dao.baseDAO;
import com.usta.model.Autor;
import com.usta.model.Pais;
import com.usta.utils.ConexionPOSTGRES;

public class AutorDAOImpl implements baseDAO<Autor> {
    private Connection conexion;

    public AutorDAOImpl() {
        try {
            //this.conexion = ConexionMySQL.obtenerConexion();
            this.conexion = ConexionPOSTGRES.obtenerConexion();
        } catch (SQLException e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos", e);
        }
    }
    
    // Implementaciones de métodos CRUD
    @Override
    public void insertar(Autor autor) {
        // Lógica para insertar un autor en la base de datos
        String sql = "INSERT INTO autor (nombre, apellido, nacionalidad, anio_nacimiento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, autor.getNombre());
            statement.setString(2, autor.getApellido());
            statement.setString(3, autor.getNacionalidad().toString());
            statement.setInt(4, autor.getAnioNacimiento());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Autor buscarPorId(int id) {
        // Lógica para buscar un autor por ID en la base de datos
        String sql = "SELECT * FROM autor WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Autor autor = new Autor();
                    autor.setId(resultSet.getInt("id"));
                    autor.setNombre(resultSet.getString("nombre"));
                    autor.setApellido(resultSet.getString("apellido"));
                    autor.setNacionalidad(Pais.valueOf(resultSet.getString("nacionalidad").toUpperCase()));
                    autor.setAnioNacimiento(resultSet.getInt("anio_nacimiento"));
                    return autor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Autor> buscarTodos() {
        // Lógica para buscar todos los autores en la base de datos
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autor";
        try (PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Autor autor = new Autor();
                autor.setId(resultSet.getInt("id"));
                autor.setNombre(resultSet.getString("nombre"));
                autor.setApellido(resultSet.getString("apellido"));
                autor.setNacionalidad(Pais.valueOf(resultSet.getString("nacionalidad").toUpperCase()));
                autor.setAnioNacimiento(resultSet.getInt("anio_nacimiento"));
                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }

    @Override
    public void actualizar(Autor autor) {
        // Lógica para actualizar un autor en la base de datos
        String sql = "UPDATE autor SET nombre = ?, apellido = ?, nacionalidad = ?, anio_nacimiento = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, autor.getNombre());
            statement.setString(2, autor.getApellido());
            statement.setString(3, autor.getNacionalidad().toString());
            statement.setInt(4, autor.getAnioNacimiento());
            statement.setInt(5,autor.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        // Lógica para eliminar un autor por ID en la base de datos
        String sql = "DELETE FROM autor WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
