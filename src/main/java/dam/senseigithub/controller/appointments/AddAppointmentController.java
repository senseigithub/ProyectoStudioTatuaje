package dam.senseigithub.controller.appointments;

import dam.senseigithub.model.dao.AppointmentDAO;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddAppointmentController {

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField; // Nuevo campo para introducir la hora

    private ClientDAO clientDAO = new ClientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    public void initialize() {
        // Cargar los nombres de los clientes en el ComboBox
        loadClientNames();
    }

    private void loadClientNames() {
        clientComboBox.getItems().clear();
        clientDAO.getAllClients().forEach(client -> clientComboBox.getItems().add(client.getName()));
    }

    @FXML
    private void saveAppointment() {
        String selectedClientName = clientComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timeField.getText(); // Obtener la hora introducida por el usuario

        // Verificar si no se seleccionó un cliente
        if (selectedClientName == null) {
            showAlert("Advertencia", "Por favor, seleccione un cliente.");
            return;
        }

        // Verificar si no se seleccionó una fecha
        if (selectedDate == null) {
            showAlert("Advertencia", "Por favor, seleccione una fecha.");
            return;
        }

        // Verificar si no se introdujo una hora
        if (selectedTime.isEmpty()) {
            showAlert("Advertencia", "Por favor, introduzca la hora de la cita.");
            return;
        }

        // Parsear la hora introducida por el usuario
        LocalTime time;
        try {
            time = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            showAlert("Advertencia", "Por favor, introduzca la hora en formato HH:mm.");
            return;
        }

        // Combinar fecha y hora para crear la fecha y hora de la cita
        LocalDateTime dateTime = LocalDateTime.of(selectedDate, time);

        // Obtener el cliente por su nombre
        Client client = clientDAO.getClientByName(selectedClientName);
        if (client == null) {
            // Mostrar una alerta si no se encontró el cliente
            showAlert("Error", "No se encontró el cliente.");
            return;
        }

        // Crear la cita con el cliente y la fecha seleccionada
        Appointment appointment = new Appointment();
        appointment.setDate(dateTime);

        // Guardar la cita en la base de datos
        appointmentDAO.addAppointment(client, appointment);

        // Mostrar una alerta de éxito
        showAlert("Éxito", "La cita se ha agregado correctamente.");

        // Limpiar los campos después de guardar la cita
        clearFields();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        clientComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        timeField.clear();
    }
}
