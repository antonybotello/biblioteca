package com.usta.controller;

import java.io.IOException;

import com.usta.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
public class MainController {

    @FXML
    private Label infoLbl;
    @FXML
    private HBox autorRoot;
    @FXML
    private HBox bibliotecaRoot;
    @FXML
    private HBox usuarioRoot;
    @FXML
    private HBox prestamoRoot;
    @FXML
    private HBox libroRoot;
    
    
    public void initialize(){
        AutorController autorController = new AutorController();
        LibroController libroController = new LibroController();

        cargarInterfaz(autorRoot, "/com/usta/view/autor.fxml",autorController);
        // cargarInterfaz(usuarioRoot, "/com/usta/view/usuario.fxml");
        // cargarInterfaz(prestamoRoot, "/com/usta/view/prestamo.fxml");
        cargarInterfaz(libroRoot, "/com/usta/view/libro.fxml",libroController);
        
    }
 
    public void cargarInterfaz(HBox root, String ruta, Object controller ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            loader.setController(controller);
            HBox content = loader.load();
            infoLbl.setText("Éxito al cargar interfaz de objeto");
            root.getChildren().add(content);
        } catch (IOException e) {
            infoLbl.setText("Error al cargar cargar interfaz de ruta "+ ruta );
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
