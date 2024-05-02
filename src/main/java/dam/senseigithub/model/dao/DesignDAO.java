package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Design;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DesignDAO {
    private final static String INSERT = "INSERT INTO diseno (Nombre, Tamano, Imagen) VALUES (?, ?, ?)";
    private final static String DELETE = "DELETE FROM diseno WHERE Nombre=?";
    private final static String SELECT_ALL = "SELECT * FROM diseno";

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

    public List<Design> getAllDesigns() throws SQLException {
        List<Design> designs = new ArrayList<>();
        try (Statement stmt = ConnectionMariaDB.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int id = rs.getInt("Id_Diseno");
                String name = rs.getString("Nombre");
                float size = rs.getFloat("Tamano");
                byte[] imageData = rs.getBytes("Imagen");

                // Crear una instancia de Design con los datos recuperados de la base de datos
                Design design = new Design();
                design.setIdDesign(id);
                design.setName(name);
                design.setSize(size);
                design.setImageInputStream(new ByteArrayInputStream(imageData));

                designs.add(design);
            }
        }
        return designs;
    }
}
