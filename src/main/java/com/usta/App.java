package com.usta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.usta.dao.Autor.AutorDAO;
import com.usta.dao.Autor.AutorDAOImpl;
import com.usta.utils.ConexionMySQL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        Connection conexion = null;
        try {
            conexion = ConexionMySQL.conectar();

            // Crear el DAO con la conexión establecida
            AutorDAO autorDAO = new AutorDAOImpl(conexion);

            // Utilizar el DAO para realizar operaciones en la base de datos...
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión cuando ya no sea necesaria
            ConexionMySQL.desconectar(conexion,null,null);
        }
    }

}