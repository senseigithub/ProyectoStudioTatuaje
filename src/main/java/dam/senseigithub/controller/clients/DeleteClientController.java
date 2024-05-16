package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import dam.senseigithub.controller.Controller;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class DeleteClientController extends Controller {

    @FXML
    private TextField dni;

    @FXML
    private TextField name;

    private ClientDAO clientDAO = new ClientDAO();

    /**
     * Borrar un cliente.
     * @throws IOException
     */
    @FXML
    private void deleteClient() throws IOException {
        String dni = this.dni.getText();
        String name = this.name.getText();

        if (!validateFields(dni, name)) {
            return;
        }

        Client client = new Client();
        client.setDnie(dni);
        client.setName(name);
        clientDAO.deleteClient(client);
        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente eliminado", "El cliente se ha eliminado correctamente.");
        App.setRoot("mainView");
    }

    /**
     * Valida que el dni y el nombre no esten vacios.
     * @param dni
     * @param name
     * @return
     */
    private boolean validateFields(String dni, String name) {
        if (dni.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Datos de cliente incompletos", "Por favor, ingrese el DNI y el nombre del cliente.");
            return false;
        }

        if (!Pattern.matches("\\d{8}[A-Za-z]", dni)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Formato de DNI inválido", "El DNI debe tener 8 dígitos seguidos de una letra.");
            return false;
        }

        return true;
    }

    /**
     * La alerta se muestra en la ventana emergente.
     * @param alertType
     * @param title
     * @param header
     * @param content
     */
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    public void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}