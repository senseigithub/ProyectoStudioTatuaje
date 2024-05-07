package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private final static String INSERT = "INSERT INTO cliente (Dnie, Nombre, Email, Telefono) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE cliente SET Dnie=?, Nombre=?, Email=?, Telefono=? WHERE Id_Cliente=?";
    private final static String FINDALL = "SELECT * FROM cliente";
    private static final String SELECT_BY_NAME = "SELECT * FROM cliente WHERE Nombre = ?";
    private final static String FINDBYID = "SELECT * FROM cliente WHERE Id_Cliente=?";
    private final static String DELETE = "DELETE FROM cliente WHERE Dnie=? AND Nombre=?";
    private static final String SELECT_ALL_NAMES = "SELECT Nombre FROM cliente";

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

    public List<String> getAllClientNames() {
        List<String> clientNames = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(SELECT_ALL_NAMES)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                clientNames.add(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientNames;
    }

    public Client getClientByName(String name) {
        Client client = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(SELECT_BY_NAME)) {
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

    public void updateClient(Client client) {
        if (client == null) return;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, client.getDnie());
            pst.setString(2, client.getName());
            pst.setString(3, client.getEmail());
            pst.setString(4, client.getPhone());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public Client findById(int id) {
        Client client = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("Id_Cliente"));
                client.setDnie(rs.getString("Dnie"));
                client.setName(rs.getString("Nombre"));
                client.setEmail(rs.getString("Email"));
                client.setPhone(rs.getString("Telefono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }


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
