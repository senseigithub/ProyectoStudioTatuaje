package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Design;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DesignDAO {
    private final static String INSERT = "INSERT INTO diseno (Nombre, Tamano, Imagen) VALUES (?, ?, ?)";
    private final static String DELETE = "DELETE FROM diseno WHERE Nombre=?";

    public void addDesign(Design design) {
        if (design == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, design.getName());
            pst.setFloat(2, design.getSize());
            pst.setBytes(3, design.getImagen());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
