package dam.senseigithub.model.connection;

import dam.senseigithub.utils.XMLManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMariaDB {
    private final static String FILE="connection.xml";
    private static ConnectionMariaDB _instance;
    private static Connection conn;

    /**
     * Containe la configuracion de la base de datos.
     */
    private ConnectionMariaDB(){
        ConnectionProperties properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(),FILE);

        try {
            conn = DriverManager.getConnection(properties.getURL(),properties.getUser(),properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            conn=null;
        }
    }

    /**
     * Hace una instacia de la coneccion.
     * @return
     */
    public static Connection getConnection(){
        if(_instance==null){
            _instance = new ConnectionMariaDB();
        }
        return conn;
    }

    /**
     * Cierra la coneccion.
     */
    public static void closeConnection(){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}