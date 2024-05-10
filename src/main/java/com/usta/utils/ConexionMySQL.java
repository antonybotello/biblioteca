package com.usta.utils;

//https://phoenixnap.com/kb/install-maven-windows

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_bd";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void desconectar(Connection conexion, Statement sentencia, ResultSet resultados) {
        try {
            if (resultados != null) resultados.close();
            if (sentencia != null) sentencia.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
