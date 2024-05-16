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

public class AddUserController extends Controller {

    @FXML
    private TextField dni;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    private ClientDAO clientDAO = new ClientDAO();


    /**
     * Este metodo añade un cliente al sistema.
     * @throws IOException
     */
    @FXML
    private void addClient() throws IOException {
        String dni = this.dni.getText();
        String name = this.name.getText();
        String email = this.email.getText();
        String phone = this.phone.getText();

        if (validateFields(dni, name, email, phone)) {
            Client client = new Client();
            client.setDnie(dni);
            client.setName(name);
            client.setEmail(email);
            client.setPhone(phone);
            clientDAO.addClient(client);
            clearFields();
            App.setRoot("mainView");
        }
    }

    /**
     * Lo que hace es validar los campos introducidos por el usuario.
     * @param dni
     * @param name
     * @param email
     * @param phone
     * @return
     */
    private boolean validateFields(String dni, String name, String email, String phone) {
        if (dni == null || dni.trim().isEmpty() || !Pattern.matches("[0-9]{8}[A-Z]", dni)) {
            showAlert("DNI inválido", "Por favor, introduce un DNI válido.");
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            showAlert("Nombre inválido", "El nombre no puede estar vacío.");
            return false;
        }
        if (email == null || email.trim().isEmpty() || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            showAlert("Email inválido", "Por favor, introduce un email válido.");
            return false;
        }
        if (phone == null || phone.trim().isEmpty() || !Pattern.matches("[0-9]+", phone) || phone.length() != 9) {
            showAlert("Teléfono inválido", "Por favor, introduce un número de teléfono válido de 9 dígitos.");
            return false;
        }
        return true;
    }

    /**
     * Muestra un alerta de error.
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Limpia los campos.
     */
    private void clearFields() {
        dni.clear();
        name.clear();
        email.clear();
        phone.clear();
    }

    @FXML
    public void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}
