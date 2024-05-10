package com.usta.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.usta.dao.Autor.AutorDAO;
import com.usta.dao.Autor.AutorDAOImpl;
import com.usta.model.Autor;
import com.usta.utils.ConexionMySQL;

import javafx.collections.ObservableList;

public abstract class BaseController {

    protected AutorDAO autorDAO;
    protected Connection conexion;
    
    public BaseController() {
        abrirConexion();
        this.autorDAO = new AutorDAOImpl(conexion);
    }


    protected void abrirConexion() {
        try {
            conexion = ConexionMySQL.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void cargarDatosAutores(ObservableList<Autor> autoresData) {
        autoresData.clear();
        List<Autor> autoresDesdeBD = autorDAO.buscarTodos();
        autoresData.addAll(autoresDesdeBD);
    }
}
