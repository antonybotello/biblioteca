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
        String sql = "INSERT INTO autor (nombre, apellido, nacionalidad, anio_nacimiento, documento, foto) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, autor.getNombre());
            statement.setString(2, autor.getApellido());
            statement.setString(3, autor.getNacionalidad().toString());
            statement.setInt(4, autor.getAnioNacimiento());
            statement.setString(5, autor.getDocumento());
            statement.setString(6, autor.getFoto());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Autor buscarPorId(int id) {
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
                    autor.setDocumento(resultSet.getString("documento"));
                    autor.setFoto(resultSet.getString("foto"));
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
                autor.setDocumento(resultSet.getString("documento"));
                autor.setFoto(resultSet.getString("foto"));
                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }

    @Override
    public void actualizar(Autor autor) {
        String sql = "UPDATE autor SET nombre = ?, apellido = ?, nacionalidad = ?, anio_nacimiento = ?, documento = ?, foto = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, autor.getNombre());
            statement.setString(2, autor.getApellido());
            statement.setString(3, autor.getNacionalidad().toString());
            statement.setInt(4, autor.getAnioNacimiento());
            statement.setString(5, autor.getDocumento());
            statement.setString(6, autor.getFoto());
            statement.setInt(7, autor.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM autor WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
