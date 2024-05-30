package com.usta.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import com.usta.dao.baseDAO;
import com.usta.dao.Autor.AutorDAOImpl;
import com.usta.model.Autor;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.awt.image.BufferedImage;

import com.usta.model.Pais;

public class AutorController extends BaseController {
    FileOutputStream salida;
    private Autor autorSeleccionado;
    private ObservableList<Autor> autoresData = FXCollections.observableArrayList(); // La lista observable de autores}
    private FilteredList<Autor> autoresFiltrados; // Lista filtrada de autores

    @FXML
    private TableView<Autor> autoresTable; // La tabla de autores en la interfaz
    @FXML
    private Label urlLbl;
    @FXML
    private ImageView fotoImgV;
    File archivoFoto;
    // ------------------------- Campos de Texto ------------//
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField nacionalidadField;
    @FXML
    private TextField anioNacimientoField;
    @FXML
    private TextField documentoField;
    // ------------------------------------------------------//

    // ------------------------- Checks de filtro ------------//
    @FXML
    private CheckBox documentoChk;
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
    private TableColumn<Autor, String> documentoCol;
    @FXML
    private TableColumn<Autor, String> nombreCol;
    @FXML
    private TableColumn<Autor, String> apellidoCol;
    @FXML
    private TableColumn<Autor, Pais> nacionalidadCol;
    @FXML
    private TableColumn<Autor, Integer> anioNacimientoCol;
    @FXML
    private TableColumn<Autor, String> fotoCol;

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

    public void ventana() {
        // Crear una ventana emergente de información
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Información");
        alert.setHeaderText("Este es un mensaje de información");
        alert.setContentText("Bienvenido a la ventana emergente de información.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
    }

    public void initialize() {
        paisCBx.getItems().addAll(Pais.values());
        // Configurar las celdas de la tabla
        documentoCol.setCellValueFactory(new PropertyValueFactory<>("documento"));

        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        nacionalidadCol.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        anioNacimientoCol.setCellValueFactory(new PropertyValueFactory<>("anioNacimiento"));
        fotoCol.setCellValueFactory(new PropertyValueFactory<>("foto"));
        fotoCol.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    File file = new File(item);
                    Image image;
                    if (file.exists()) {
                        image = new Image(file.toURI().toString());
                    } else {
                        image = new Image(new File("src\\main\\java\\com\\usta\\view\\img\\usuarios\\default-user.jpg")
                                .toURI().toString());
                    }
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });
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

        String filtroTexto = filtroTxt.getText().toLowerCase();
        if (autoresFiltrados == null) {
            autoresFiltrados = new FilteredList<>(autoresData, p -> true);
        }
        autoresFiltrados.setPredicate(autor -> {
            // Si el filtro está vacío, mostrar todos los elementos
            if (filtroTexto == null || filtroTexto.isEmpty()) {
                return true;
            }
            boolean documentoMatch = documentoChk.isSelected()
                    && autor.getDocumento().toLowerCase().contains(filtroTexto);
            boolean nombreMatch = nombreChk.isSelected()
                    && autor.getNombre().toLowerCase().contains(filtroTexto);
            boolean apellidoMatch = apellidoChk.isSelected()
                    && autor.getApellido().toLowerCase().contains(filtroTexto);
            boolean nacionalidadMatch = nacionalidadChk.isSelected()
                    && autor.getNacionalidad().toString().toLowerCase().contains(filtroTexto);
            boolean anioNacimientoMatch = anioNacimientoChk.isSelected()
                    && String.valueOf(autor.getAnioNacimiento()).contains(filtroTexto);

            // Verificar si el autor coincide con alguno de los campos seleccionados
            return documentoMatch || nombreMatch || apellidoMatch || nacionalidadMatch || anioNacimientoMatch;
        });

        autoresTable.setItems(autoresFiltrados);
    }

    @FXML
    public void agregarAutor() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        Pais nacionalidad = paisCBx.getValue();
        int anioNacimiento = Integer.parseInt(anioNacimientoField.getText());
        String documento = documentoField.getText();
        String foto = "src\\main\\java\\com\\usta\\view\\img\\usuarios\\" + documento + ".jpg";
        Autor nuevoAutor;
        if (fotoImgV.getImage() != null) {
            nuevoAutor = new Autor(nombre, apellido, nacionalidad, anioNacimiento, documento, foto);
            guardarImagen(convertirImagenABytes());
        } else {

            nuevoAutor = new Autor(nombre, apellido, nacionalidad, anioNacimiento, documento);
        }

        autorDAO.insertar(nuevoAutor);
        autoresData.add(nuevoAutor);

        actualizarTabla();

    }

    @FXML
    public void agregarFoto() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Subir Imagen");
        fc.getExtensionFilters().add(new ExtensionFilter("Imágenes", "*.jpg"));
        archivoFoto = fc.showOpenDialog(null);

        if (archivoFoto != null) {
            urlLbl.setText("" + archivoFoto.getAbsolutePath());
            Image image = new Image(archivoFoto.toURI().toString());
            fotoImgV.setImage(image);
        }

    }

    /* Guardar imagen */
    public String guardarImagen(byte[] bytesImg) {

        String respuesta = null;
        try {
            salida = new FileOutputStream(
                    new File("src\\main\\java\\com\\usta\\view\\img\\usuarios\\" + documentoField.getText() + ".jpg"));
            salida.write(bytesImg);
            respuesta = "La imagen se guardo con exito.";
        } catch (Exception e) {
            System.out.println(e);
        }
        return respuesta;
    }

    public byte[] convertirImagenABytes() {

        // Inicializar el arreglo de bytes
        byte[] bytesImg = new byte[(int) archivoFoto.length()];

        try {
            // Crear un flujo de entrada para leer el archivo
            FileInputStream fis = new FileInputStream(archivoFoto);

            // Leer el contenido del archivo en el arreglo de bytes
            fis.read(bytesImg);

            // Cerrar el flujo de entrada
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Devolver el arreglo de bytes
        return bytesImg;
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
            documentoField.setText(autorSeleccionado.getDocumento());

            nombreField.setText(autorSeleccionado.getNombre());
            apellidoField.setText(autorSeleccionado.getApellido());
            paisCBx.setValue(autorSeleccionado.getNacionalidad());
            anioNacimientoField.setText(String.valueOf(autorSeleccionado.getAnioNacimiento()));
            // Cargar la imagen del autor si existe
            File file = new File(autorSeleccionado.getFoto() != null ? autorSeleccionado.getFoto()
                    : "src\\main\\java\\com\\usta\\view\\img\\usuarios\\default-user.jpg");
            Image image;
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
                image = new Image(getClass().getResourceAsStream("/com/usta/view/img/usuarios/default-user.jpg"));
            }
            fotoImgV.setImage(image);
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
        if (fotoImgV.getImage() != null) {
            String foto = "src\\main\\java\\com\\usta\\view\\img\\usuarios\\" + documentoField.getText() + ".jpg";
            autorSeleccionado.setFoto(foto);
            guardarImagen(convertirImagenABytes());
        }
        autorDAO.actualizar(autorSeleccionado);

        super.cargarDatosAutores(autoresData);

        actualizarTabla();
    }

    public void actualizarTabla() {
        autoresTable.setItems(autoresData);
        limpiarCampos();

    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        paisCBx.getSelectionModel().clearSelection();
        anioNacimientoField.clear();
        documentoField.clear();
        fotoImgV.setImage(null);
        urlLbl.setText("Seleccione su foto");
    }

    public void limpiarTabla() {
        autoresData.clear();
    }

}
