package dam.senseigithub.model.connection;

import dam.senseigithub.utils.XMLManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMariaDB {
    private final static String FILE="connection.xml";
    private static ConnectionMariaDB _instance;
    private static Connection conn;

    private ConnectionMariaDB(){
        ConnectionProperties properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(),FILE);

        try {
            conn = DriverManager.getConnection(properties.getURL(),properties.getUser(),properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            conn=null;
        }
    }

    public static Connection getConnection(){
        if(_instance==null){
            _instance = new ConnectionMariaDB();
        }
        return conn;
    }

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