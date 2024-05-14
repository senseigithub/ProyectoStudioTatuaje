package dam.senseigithub.controller.appointments;

import dam.senseigithub.App;
import dam.senseigithub.controller.Controller;
import dam.senseigithub.model.dao.AppointmentDAO;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointmentController extends Controller implements Initializable {

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

        if (selectedClientName == null) {
            showAlert("Advertencia", "Por favor, seleccione un cliente.");
            return;
        }

        if (selectedDate == null) {
            showAlert("Advertencia", "Por favor, seleccione una fecha.");
            return;
        }

        LocalTime time = new LocalTimeStringConverter().fromString(hour.getText() + ":" + minute.getText());

        LocalDateTime dateTime = LocalDateTime.of(selectedDate, time);

        Client client = clientDAO.getClientByName(selectedClientName);
        if (client == null) {
            showAlert("Error", "No se encontró el cliente.");
            return;
        }

        Appointment appointment = new Appointment();
        appointment.setDate(dateTime);
        appointmentDAO.addAppointment(client, appointment);
        showAlert("Éxito", "La cita se ha agregado correctamente.");
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

    @FXML
    public void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}
