package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static String URL = "jdbc:mariadb://localhost:3306/gestion_documentos";
    private static String USER = "root";
    private static String PASSWORD = "123456";
    private static Connection conexion = null;
    public static Connection getConexion(){
        try {
            if (conexion == null || conexion.isClosed()){
                conexion = DriverManager.getConnection(URL,USER, PASSWORD );
            }
            return conexion;
        }catch (SQLException e){
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    public static void cerrarConexion(){
        try {
            conexion.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error en al intentar cerrar la BBDD",e);
        }
    }
}
