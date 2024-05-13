package com.usta.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.usta.dao.baseDAO;
import com.usta.dao.Autor.AutorDAOImpl;
import com.usta.model.Autor;
import com.usta.model.Libro;
import com.usta.model.Pais;
import com.usta.utils.ConexionMySQL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LibroController extends BaseController {


    @FXML
    private TextField tituloField;
    @FXML
    private ComboBox<Autor> autorCBx;
    @FXML
    private TextField añoPublicacionField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField generoField;

    private ObservableList<Libro> librosData;
    private ObservableList<Autor> autoresData = FXCollections.observableArrayList(); ;


    public LibroController() {
        super();
    }

    public void initialize() {
        super.abrirConexion();
        // Configurar ComboBox de autores
        cargarDatosAutores(autoresData);
        autorCBx.getItems().setAll(autoresData);
    }
  

    @FXML
    public void guardarLibro() {
        // Obtener los valores de los campos
        String titulo = tituloField.getText();
        Autor autor = autorCBx.getValue();
        int añoPublicacion = Integer.parseInt(añoPublicacionField.getText());
        String isbn = isbnField.getText();
        String genero = generoField.getText();

        // Aquí puedes hacer algo con los valores, como guardar el libro en una base de datos
        System.out.println("Libro guardado:");
        System.out.println("Título: " + titulo);
        System.out.println("Libro: " + autor);
        System.out.println("Año de Publicación: " + añoPublicacion);
        System.out.println("ISBN: " + isbn);
        System.out.println("Género: " + genero);

        // Limpiar los campos después de guardar el libro
        limpiarCampos();
    }

    @FXML
    public void limpiarCampos() {
        tituloField.clear();
        autorCBx.getSelectionModel().clearSelection();
        añoPublicacionField.clear();
        isbnField.clear();
        generoField.clear();
    }
}
