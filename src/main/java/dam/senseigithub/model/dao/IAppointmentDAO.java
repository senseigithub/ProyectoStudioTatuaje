package dam.senseigithub.model.dao;

import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;

import java.sql.Timestamp;
import java.util.List;

public interface IAppointmentDAO {

    public void addAppointment(Client client, Appointment appointment);

    public void deleteAllAppointmentsByClientName(String clientName);

    public List<Appointment> getAppointmentsByClientId(int clientId);

    public void updateAppointment(String clientName, Timestamp newDate);

}
