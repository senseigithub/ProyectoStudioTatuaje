package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Client;
import dam.senseigithub.model.entity.Design;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DesignDAO implements IDesignDAO {
    private final static String INSERT = "INSERT INTO diseno (Id_Cliente, Nombre, Tamano, Imagen) VALUES ((SELECT Id_Cliente FROM cliente WHERE Nombre = ?), ?, ?, ?)";
    private final static String UPDATE = "UPDATE diseno SET Imagen = ? WHERE Id_Diseno = ?";
    private final static String DELETE = "DELETE FROM diseno WHERE Nombre=?";
    private final static String SELECT_ALL = "SELECT * FROM diseno";

    /**
     * Añadir diseño por el nombre del cliente
     * @param design recibe el diseño a introducir.
     * @param client recibe el cliente al que le quieres introducir el diseño.
     */
    @Override
    public void addDesign(Design design, Client client) {
        if (design == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, client.getName());
            pst.setString(2, design.getName());
            pst.setFloat(3, design.getSize());
            pst.setBytes(4, design.getImagen());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el diseño en la base de datos.
     * @param design recibe el diseño a actualizar.
     * @throws SQLException lanza la excepción.
     */
    public void updateDesign(Design design) throws SQLException {
        if (design == null || design.getImageInputStream() == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setBinaryStream(1, design.getImageInputStream());
            pst.setInt(2, design.getIdDesign());
            pst.executeUpdate();
        }
    }

    /**
     * Borra el diseño
     * @param design recibe el diseño que queremos borrar.
     * @throws SQLException lanza la excepcion.
     */
    public void deleteDesign(Design design) throws SQLException {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1, design.getName());
            pst.executeUpdate();
        }
    }

    /**
     * Coge todos los diseños de la base de datos.
     * @return te devuelve todos los diseños de la base de datos.
     * @throws SQLException
     */
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
