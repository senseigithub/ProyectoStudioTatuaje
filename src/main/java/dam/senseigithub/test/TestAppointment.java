package dam.senseigithub.test;

import dam.senseigithub.model.dao.AppointmentDAO;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;

import java.time.LocalDateTime;
import java.util.List;

public class TestAppointment {
    public static void main(String[] args) {
        // Crear una instancia del DAO de citas
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        // ID del cliente para el que deseas recuperar las citas
        int clientId = 5;

        // Recuperar las citas del cliente con ID 5
        List<Appointment> appointments = appointmentDAO.getAppointmentsByClient(clientId);

        // Imprimir las citas recuperadas
        System.out.println("Citas para el cliente con ID " + clientId + ":");
        if (appointments.isEmpty()) {
            System.out.println("Este cliente no tiene citas.");
        } else {
            for (Appointment appointment : appointments) {
                LocalDateTime date = appointment.getDate();
                System.out.println("ID de cita: " + appointment.getIdAppointment() + ", Fecha: " + date);
            }
        }
    }
}
