package com.usta.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.usta.dao.baseDAO;
import com.usta.dao.Autor.AutorDAOImpl;
import com.usta.model.Autor;
import com.usta.utils.ConexionPOSTGRES;

import javafx.collections.ObservableList;

public abstract class BaseController {

    protected baseDAO<Autor> autorDAO; // DAO para la entidad Autor
    protected Connection conexion; // Conexión a la base de datos

    public BaseController() {
        this.autorDAO = new AutorDAOImpl(); // Inicializa el DAO para la entidad Autor
    }

    // Método para abrir la conexión a la base de datos
    protected void abrirConexion() {
        try {
            conexion = ConexionPOSTGRES.obtenerConexion(); // Obtiene la conexión desde la clase mejorada
            System.out.println("Conexión establecida correctamente."); // Mensaje de confirmación
        } catch (SQLException e) {
            System.err.println("Error al abrir la conexión: " + e.getMessage()); // Mensaje de error
            e.printStackTrace(); // Imprime el rastreo de la excepción
        }
    }

    // Método para cerrar la conexión a la base de datos
    protected void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close(); // Cierra la conexión si no está cerrada
                System.out.println("Conexión cerrada correctamente."); // Mensaje de confirmación
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage()); // Mensaje de error
            e.printStackTrace(); // Imprime el rastreo de la excepción
        }
    }

    // Método para cargar datos de autores desde la base de datos a una lista observable
    protected void cargarDatosAutores(ObservableList<Autor> autoresData) {
        autoresData.clear(); // Borra los datos existentes en la lista observable

        // Obtiene la lista de autores desde la base de datos utilizando el DAO
        List<Autor> autoresDesdeBD = autorDAO.buscarTodos();
        autoresData.addAll(autoresDesdeBD); // Agrega los autores a la lista observable
    }
}
