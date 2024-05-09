package dam.senseigithub.controller.appointments;


import dam.senseigithub.model.dao.AppointmentDAO;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.util.List;

public class DeleteAppointmentController {
    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    private ComboBox<String> dateComboBox;

    private ClientDAO clientDAO = new ClientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    public void initialize() {
        // Cargar los nombres de los clientes en el ComboBox
        loadClientNames();
        loadDates();
    }

    private void loadClientNames() {
        clientComboBox.getItems().clear();
        clientDAO.getAllClientNames().forEach(clientComboBox.getItems()::add);
    }

    @FXML
    private void loadDates() {
        String selectedClientName = clientComboBox.getValue();
        if (selectedClientName != null) {
            Client client = clientDAO.getClientByName(selectedClientName);
            if (client != null) {
                List<Appointment> appointments = appointmentDAO.getAppointmentsByClientId(client.getIdClient());
                if (!appointments.isEmpty()) {
                    dateComboBox.getItems().clear();
                    appointments.forEach(appointment -> dateComboBox.getItems().add(appointment.getDate().toString()));
                } else {
                    // Mostrar un mensaje indicando que no hay citas para este cliente
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("No hay citas para este cliente.");
                    alert.showAndWait();
                }
            }
        }
    }


}
