package dam.senseigithub.controller.appointments;

import dam.senseigithub.model.dao.AppointmentDAO;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.LocalTimeStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label hour;

    @FXML
    private Label minute;

    private ClientDAO clientDAO = new ClientDAO();

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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


        // Parsear la hora introducida por el usuario
        LocalTime time = new LocalTimeStringConverter().fromString(hour.getText() + ":" + minute.getText());

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

    }

    @FXML
    private void addHour() {
        String current = hour.getText();
        int currentHour = Integer.parseInt(current);
        if (currentHour < 23) {
            currentHour++;
            if ((currentHour + "").length() == 1) {
                hour.setText("0" + Integer.toString(currentHour));
            } else {
                hour.setText(Integer.toString(currentHour));
            }

        }
    }

    @FXML
    private void substractHour() {
        String current = hour.getText();
        int currentHour = Integer.parseInt(current);
        if (currentHour > 0) {
            currentHour--;
            if ((currentHour + "").length() == 1) {
                hour.setText("0" + Integer.toString(currentHour));
            } else {
                hour.setText(Integer.toString(currentHour));
            }
        }
    }

    @FXML
    private void addMinute() {
        String current = minute.getText();
        int currentMinute = Integer.parseInt(current);
        if (currentMinute < 59) {
            currentMinute++;
            if ((currentMinute + "").length() == 1) {
                minute.setText("0" + Integer.toString(currentMinute));
            } else {
                minute.setText(Integer.toString(currentMinute));
            }
        }
    }

    @FXML
    private void substractMinute() {
        String current = minute.getText();
        int currentMinute = Integer.parseInt(current);
        if (currentMinute > 0) {
            currentMinute--;
            if ((currentMinute + "").length() == 1) {
                minute.setText("0" + Integer.toString(currentMinute));
            } else {
                minute.setText(Integer.toString(currentMinute));
            }
        }
    }
}
