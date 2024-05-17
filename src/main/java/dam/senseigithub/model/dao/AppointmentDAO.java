package dam.senseigithub.model.dao;

import dam.senseigithub.model.connection.ConnectionMariaDB;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements IAppointmentDAO {
    private final static String INSERT = "INSERT INTO cita (Id_Cliente, Fecha) VALUES ((SELECT Id_Cliente FROM cliente WHERE Nombre = ?), ?)";
    private static final String DELETE_BY_NAME = "DELETE FROM cita WHERE Id_Cliente = (SELECT Id_Cliente FROM cliente WHERE Nombre = ?)";
    private static final String SELECT_BY_CLIENT_ID = "SELECT * FROM Cita WHERE Id_Cliente = ?";
    private static final String UPDATE = "UPDATE cita SET Fecha = ? WHERE Id_Cliente = (SELECT Id_Cliente FROM cliente WHERE Nombre = ?)";

    /**
     * Añade una cita a la base de datos.
     * @param client recibe el cliente al que le quieres añadir la cita.
     * @param appointment recibe la fecha de la cita.
     */
    @Override
    public void addAppointment(Client client, Appointment appointment) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
            pst.setString(1, client.getName());
            pst.setString(2, appointment.getDate().toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la fecha de una cita para un cliente dado.
     * @param clientName recibe el nombre del cliente.
     * @param newDate recibe la nueva fecha de la cita.
     */
    @Override
    public void updateAppointment(String clientName, Timestamp newDate) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setTimestamp(1, newDate);
            pst.setString(2, clientName);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra las citas utilizando el nombre del cliente.
     * @param clientName recibe el nombre del cliente.
     */
    @Override
    public void deleteAllAppointmentsByClientName(String clientName) {
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE_BY_NAME)) {
            pst.setString(1, clientName);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Esto es un eager, porque lo traigo de la base de datos.
    /**
     * Recoge las citas de un cliente por su id.
     * @param clientId recibe el id del cliente.
     * @return Devuelve una lista de citas.
     */
    @Override
    public List<Appointment> getAppointmentsByClientId(int clientId) {
        List<Appointment> appointments = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(SELECT_BY_CLIENT_ID)) {
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

    public static AppointmentDAO build(){
        return new AppointmentDAO();
    }
}
class ClientLazy extends Client {

    @Override
    public List<Appointment> getAppointments() {
        if(super.getAppointments()==null){
            setAppointments(AppointmentDAO.build().getAppointmentsByClientId(getIdClient()));
        }
        return super.getAppointments();
    }
}
