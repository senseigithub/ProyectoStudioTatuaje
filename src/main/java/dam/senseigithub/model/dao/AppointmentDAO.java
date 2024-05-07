package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private final static String INSERT = "INSERT INTO cita (Id_Cliente, Fecha) VALUES ((SELECT Id_Cliente FROM cliente WHERE Nombre = ?), ?)";
    private static final String DELETE_BY_DNIE_AND_DATE = "DELETE FROM cita WHERE Id_Cliente = (SELECT Id_Cliente FROM cliente WHERE Dnie = ?) AND Fecha = ?";
    private static final String SELECT_APPOINTMENTS_FOR_MONTH = "SELECT Fecha FROM cita WHERE MONTH(Fecha) = ? AND YEAR(Fecha) = ?";

    public void addAppointment(Client client, Appointment appointment) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
            pst.setString(1, client.getName());
            pst.setString(2, appointment.getDate().toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAppointmentsByDNIEAndDate(Client client, Appointment appointment) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE_BY_DNIE_AND_DATE)) {
            pst.setString(1, client.getDnie());
            pst.setString(2, appointment.getDate().toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LocalDate> getAppointmentsForMonth(int year, int month) {
        List<LocalDate> appointments = new ArrayList<>();

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_APPOINTMENTS_FOR_MONTH)) {
            statement.setInt(1, month);
            statement.setInt(2, year);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate appointmentDate = resultSet.getDate("Fecha").toLocalDate();
                    appointments.add(appointmentDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
}
