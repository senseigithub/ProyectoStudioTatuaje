package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DeleteClientController {

    @FXML
    private TextField dni;

    @FXML
    private TextField name;

    // Instancia del DAO ClientDAO
    private ClientDAO clientDAO = new ClientDAO();

    @FXML
    private void deleteClient() throws IOException {
        String dni = this.dni.getText();
        String name = this.name.getText();

        // Validar que se hayan ingresado valores válidos
        if (dni.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Datos de cliente incompletos", "Por favor, ingrese el DNI y el nombre del cliente.");
            return;
        }

        // Intentar eliminar el cliente
        Client client = new Client();
        client.setDnie(dni);
        client.setName(name);
        clientDAO.deleteClient(client);
        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente eliminado", "El cliente se ha eliminado correctamente.");
        App.setRoot("mainView");
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}