package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AppointmentDAO {
    private final static String INSERT = "INSERT INTO cita (Id_Cliente, Fecha) VALUES ((SELECT Id_Cliente FROM cliente WHERE Nombre = ?), ?)";
    private static final String DELETE_BY_DNIE_AND_DATE = "DELETE FROM cita WHERE Id_Cliente = (SELECT Id_Cliente FROM cliente WHERE Dnie = ?) AND Fecha = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM Cita WHERE Id_Cita = ?";
    private static final String SELECT_BY_CLIENT_ID = "SELECT * FROM Cita WHERE Id_Cliente = ?";

    public static List<Appointment> getAppointmentsByClient(int clientId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Cita WHERE Id_Cliente = ?");
        ) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int appointmentId = resultSet.getInt("Id_Cita");
                    LocalDateTime appointmentDate = resultSet.getTimestamp("Fecha").toLocalDateTime();

                    // Aquí podrías recuperar más información sobre la cita si es necesario, como el cliente asociado

                    Appointment appointment = new Appointment();
                    appointment.setIdAppointment(appointmentId);
                    appointment.setDate(appointmentDate);

                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }


    public void addAppointment(Client client, Appointment appointment) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
            pst.setString(1, client.getName());
            pst.setString(2, appointment.getDate().toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
