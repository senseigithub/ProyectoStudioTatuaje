package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Client;
import dam.senseigithub.model.entity.Tattoo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TattooDAO {
    private static final String INSERT = "INSERT INTO tatuaje (Id_Tatuaje, Id_Cliente, Id_Diseno, Foto_final, Precio_final, Tipo, Estado) VALUES (?, ?, ?, ?, ?, ?)";

    public void addTattoo(Tattoo tattoo, Client client) {
        if (tattoo == null || client == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, tattoo.getIdTattoo());
            pst.setInt(2, client.getIdClient());
            pst.setInt(3, tattoo.getDesign().getIdDesign());
            pst.setBytes(4, tattoo.getFinishedImage());
            pst.setFloat(5, tattoo.getPrice());
            pst.setString(6, tattoo.getType());
            pst.setString(7, tattoo.getStatus());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
