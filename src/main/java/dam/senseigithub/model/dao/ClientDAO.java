package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements IClientDAO {
    private final static String INSERT = "INSERT INTO cliente (Dnie, Nombre, Email, Telefono) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE cliente SET Dnie=?, Nombre=?, Email=?, Telefono=? WHERE Dnie=?";
    private static final String FINDBYNAME = "SELECT * FROM cliente WHERE Nombre = ?";
    private static final String FINDALL = "SELECT * FROM cliente";
    private final static String DELETE = "DELETE FROM cliente WHERE Dnie=? AND Nombre=?";

    /**
     * Este metodo añade un nuevo cliente a la Base de datos.
     *
     * @param client recibe el cliente que se quiere añadir.
     */
    @Override
    public void addClient(Client client) {
        if (client == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, client.getDnie());
            pst.setString(2, client.getName());
            pst.setString(3, client.getEmail());
            pst.setString(4, client.getPhone());
            pst.executeUpdate();
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setIdClient(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to get the generated ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Te devuelve un cliente a partir de su nombre.
     *
     * @param name el parametro a introducir es el nombre del cliente.
     * @return devuelve el cliente del que has introducido el nombre.
     */
    @Override
    public Client getClientByName(String name) {
        Client client = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("Id_Cliente"));
                client.setName(rs.getString("Nombre"));
                client.setDnie(rs.getString("Dnie"));
                client.setEmail(rs.getString("Email"));
                client.setPhone(rs.getString("Telefono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * Actualiza un cliente de la Base de datos.
     *
     * @param client recibe el cliente que quieres actualizar.
     */
    @Override
    public void updateClient(Client client) {
        if (client == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, client.getDnie());
            pst.setString(2, client.getName());
            pst.setString(3, client.getEmail());
            pst.setString(4, client.getPhone());
            pst.setString(5, client.getDnie());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra un cliente de la Base de datos.
     *
     * @param client recibe el cliente que quieres borrar.
     */
    @Override
    public void deleteClient(Client client) {
        if (client == null || client.getDnie() == null || client.getName() == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1, client.getDnie());
            pst.setString(2, client.getName());
            int rowsAffected = pst.executeUpdate();
            System.out.println("Se eliminaron " + rowsAffected + " filas.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve todos los clientes de la Base de datos.
     *
     * @return Los devuelve.
     */
    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setIdClient(rs.getInt("Id_Cliente"));
                client.setDnie(rs.getString("Dnie"));
                client.setName(rs.getString("Nombre"));
                client.setEmail(rs.getString("Email"));
                client.setPhone(rs.getString("Telefono"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
