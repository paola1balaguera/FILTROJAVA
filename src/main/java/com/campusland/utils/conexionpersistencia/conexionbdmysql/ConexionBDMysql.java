package com.campusland.utils.conexionpersistencia.conexionbdmysql;

import com.campusland.utils.Configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.campusland.utils.Configuracion;
public class ConexionBDMysql {

    private static String url = "jdbc:mysql://localhost:3306/dabase_factura?serverTimezone=America/Santiago";
    private static String username = "administrador";
    private static String password = "pepe123";
//    private static String url = Configuracion.obtenerValor("db.url");
//    private static String username = Configuracion.obtenerValor("db.username");
//    private static String password = Configuracion.obtenerValor("db.password");
    private static Connection connection;

    public static Connection getInstance() throws SQLException {

        return connection = DriverManager.getConnection(url, username, password);

    }

}