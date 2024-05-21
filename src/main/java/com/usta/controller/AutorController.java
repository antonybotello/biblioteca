package com.usta.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.usta.dao.baseDAO;
import com.usta.dao.Autor.AutorDAOImpl;
import com.usta.model.Autor;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import com.usta.model.Pais;

public class AutorController extends BaseController {

    private Autor autorSeleccionado;
    private ObservableList<Autor> autoresData = FXCollections.observableArrayList(); // La lista observable de autores}
    private FilteredList<Autor> autoresFiltrados; // Lista filtrada de autores

    @FXML
    private TableView<Autor> autoresTable; // La tabla de autores en la interfaz

    // ------------------------- Campos de Texto ------------//
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField nacionalidadField;
    @FXML
    private TextField anioNacimientoField;
    // ------------------------------------------------------//

    // ------------------------- Checks de filtro ------------//
    @FXML
    private CheckBox nombreChk;
    @FXML
    private CheckBox apellidoChk;
    @FXML
    private CheckBox nacionalidadChk;
    @FXML
    private CheckBox anioNacimientoChk;
    // ------------------------------------------------------//
    @FXML
    private TableColumn<Autor, String> nombreCol;
    @FXML
    private TableColumn<Autor, String> apellidoCol;
    @FXML
    private TableColumn<Autor, Pais> nacionalidadCol;
    @FXML
    private TableColumn<Autor, Integer> anioNacimientoCol;
    @FXML
    private Button editarAutorBtn;
    @FXML
    private Button cancelarAutorBtn;
    @FXML
    private Button agregarAutorBtn;
    @FXML
    private TextField filtroTxt;

    @FXML
    private ComboBox<Pais> paisCBx;

    public AutorController() {
        super(); // Llama al constructor de la clase base para inicializar autoresData
        this.autoresFiltrados = new FilteredList<>(autoresData, p -> true);

    }

    public void initialize() {

        paisCBx.getItems().addAll(Pais.values());
        // Configurar las celdas de la tabla
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        nacionalidadCol.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        anioNacimientoCol.setCellValueFactory(new PropertyValueFactory<>("anioNacimiento"));
        cargarDatosAutores(autoresData);
        // Establecer los datos en la tabla
        autoresTable.setItems(autoresData);
    }

    @FXML
    public void filtrarPaises() {
        String filtro = paisCBx.getEditor().getText().toLowerCase();
        paisCBx.getItems().setAll(Pais.values());
        paisCBx.getItems().removeIf(pais -> !pais.toString().toLowerCase().contains(filtro));
        paisCBx.show();
    }

    @FXML
    public void filtrarAutores() {
        if (autoresFiltrados == null) {
            autoresFiltrados = new FilteredList<>(autoresData, p -> true);
        }
        String filtroTexto = filtroTxt.getText().toLowerCase();

        autoresFiltrados.setPredicate(autor -> {
            // Si el filtro está vacío, mostrar todos los elementos
            if (filtroTexto == null || filtroTexto.isEmpty()) {
                return true;
            }

            boolean nombreMatch = nombreChk.isSelected() && autor.getNombre().toLowerCase().contains(filtroTexto);
            boolean apellidoMatch = apellidoChk.isSelected() && autor.getApellido().toLowerCase().contains(filtroTexto);
            boolean nacionalidadMatch = nacionalidadChk.isSelected()
                    && autor.getNacionalidad().toString().toLowerCase().contains(filtroTexto);
            boolean anioNacimientoMatch = anioNacimientoChk.isSelected()
                    && String.valueOf(autor.getAnioNacimiento()).contains(filtroTexto);

            // Verificar si el autor coincide con alguno de los campos seleccionados
            return nombreMatch || apellidoMatch || nacionalidadMatch || anioNacimientoMatch;
        });

        autoresTable.setItems(autoresFiltrados);
    }

    @FXML
    public void agregarAutor() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        System.out.println(paisCBx.getValue());
        Pais nacionalidad = paisCBx.getValue();
        int anioNacimiento = Integer.parseInt(anioNacimientoField.getText());

        Autor nuevoAutor = new Autor(nombre, apellido, nacionalidad, anioNacimiento);
        autorDAO.insertar(nuevoAutor);
        autoresData.add(nuevoAutor);
        dialogoExito("se ha agregado el autor de manera exitosa");
        actualizarTabla();

    }

    @FXML
    public void dialogoExito(String msg) {
        var dialog = new TextInputDialog("Exito!");
        dialog.setTitle("Exito al agregar");
        dialog.setHeaderText(msg);
        dialog.showAndWait();
    }

    public void editarPaisOn() {
        paisCBx.setEditable(true);
    }

    public void editarPaisOff() {
        paisCBx.setEditable(false);
    }

    public void eliminarAutor(Autor autor) {
        autoresData.remove(autor);
    }

    @FXML
    public void cargarEditarAutor() {
        autorSeleccionado = autoresTable.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            nombreField.setText(autorSeleccionado.getNombre());
            apellidoField.setText(autorSeleccionado.getApellido());
            paisCBx.setValue(autorSeleccionado.getNacionalidad());
            anioNacimientoField.setText(String.valueOf(autorSeleccionado.getAnioNacimiento()));
        }
        editarAutorBtn.setVisible(true);
        agregarAutorBtn.setVisible(false);
        cancelarAutorBtn.setVisible(true);

    }

    @FXML
    public void cancelar() {
        limpiarCampos();
        editarAutorBtn.setVisible(false);
        agregarAutorBtn.setVisible(true);
        cancelarAutorBtn.setVisible(false);

    }

    @FXML
    public void editarAutor() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        Pais nacionalidad = paisCBx.getValue();
        int anioNacimiento = Integer.parseInt(anioNacimientoField.getText());
        autorSeleccionado.setNombre(nombre);
        autorSeleccionado.setApellido(apellido);
        autorSeleccionado.setNacionalidad(nacionalidad);
        autorSeleccionado.setAnioNacimiento(anioNacimiento);
        autorDAO.actualizar(autorSeleccionado);
        super.cargarDatosAutores(autoresData);

        actualizarTabla();
    }

    public void actualizarTabla() {
        System.out.println("Contenido de autoresData: " + autoresData);
        autoresTable.setItems(autoresData);
        limpiarCampos();

    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        paisCBx.getSelectionModel().clearSelection();
        anioNacimientoField.clear();
    }

    public void limpiarTabla() {
        autoresData.clear();
    }

}
