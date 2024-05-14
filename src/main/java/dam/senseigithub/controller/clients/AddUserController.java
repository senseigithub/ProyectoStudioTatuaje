package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import dam.senseigithub.controller.Controller;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

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


    @FXML
    private void addClient() throws IOException {
        String dni = this.dni.getText();
        String name = this.name.getText();
        String email = this.email.getText();
        String phone = this.phone.getText();
        Client client = new Client();
        client.setDnie(dni);
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        clientDAO.addClient(client);
        clearFields();
        App.setRoot("mainView");
    }

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
