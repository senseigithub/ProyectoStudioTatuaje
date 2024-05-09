package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private final static String INSERT = "INSERT INTO cita (Id_Cliente, Fecha) VALUES ((SELECT Id_Cliente FROM cliente WHERE Nombre = ?), ?)";
    private static final String DELETE_BY_NAME_AND_DATE = "DELETE FROM cita WHERE Id_Cliente = (SELECT Id_Cliente FROM cliente WHERE Nombre = ?) AND Fecha = ?";
    private static final String SELECT_BY_CLIENT_ID = "SELECT * FROM cita WHERE Id_Cliente = ?";

    /**
     * Añade una cita a la base de datos.
     * @param client recibe el cliente al que le quieres añadir la cita.
     * @param appointment recibe la fecha de la cita.
     */
    public void addAppointment(Client client, Appointment appointment) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
            pst.setString(1, client.getName());
            pst.setString(2, appointment.getDate().toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAppointmentsByClientNameAndDate(String clientName, String date) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE_BY_NAME_AND_DATE)) {
            pst.setString(1, clientName);
            pst.setString(2, date);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recoge las citas de un cliente por su id.
     * @param clientId recibe el id del cliente.
     * @return Devuelve una lista de citas.
     */
    public List<Appointment> getAppointmentsByClientId(int clientId) {
        List<Appointment> appointments = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement("SELECT * FROM Cita WHERE Id_Cliente = ?")) {
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setIdAppointment(rs.getInt("Id_Cita"));

                appointment.setDate(rs.getTimestamp("Fecha").toLocalDateTime());

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

}
