package dam.senseigithub.controller.appointments;

import dam.senseigithub.model.dao.AppointmentDAO;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.List;

public class AddAppointmentController {

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private DatePicker datePicker;

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

        // Verificar si no se seleccionó un cliente
        if (selectedClientName == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Por favor, seleccione un cliente.");
            alert.showAndWait();
            return;
        }

        // Verificar si no se seleccionó una fecha
        if (selectedDate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Por favor, seleccione una fecha.");
            alert.showAndWait();
            return;
        }

        // Obtener el cliente por su nombre
        Client client = clientDAO.getClientByName(selectedClientName);
        if (client == null) {
            // Mostrar una alerta si no se encontró el cliente
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se encontró el cliente.");
            alert.showAndWait();
            return;
        }

        // Crear la cita con el cliente y la fecha seleccionada
        Appointment appointment = new Appointment();
        appointment.setDate(selectedDate.atStartOfDay());

        // Guardar la cita en la base de datos
        appointmentDAO.addAppointment(client, appointment);

        // Mostrar una alerta de éxito
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("La cita se ha agregado correctamente.");
        alert.showAndWait();

        // Limpiar los campos después de guardar la cita
        clientComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }


}
