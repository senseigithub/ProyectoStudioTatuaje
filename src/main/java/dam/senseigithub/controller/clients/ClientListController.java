package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class ClientListController {
    @FXML
    private void backToMainView() throws IOException {
        App.setRoot("mainView");
    }

    @FXML
    private void switchToClientList() throws IOException {
        App.setRoot("ClientList");
    }

    @FXML
    private void switchToAddUser() throws IOException {
        App.setRoot("AddClient");
    }

    @FXML
    private void switchToDeleteClient() throws IOException {
        App.setRoot("DeleteClient");
    }

    @FXML
    private void switchToAddDesign() throws IOException {
        App.setRoot("AddDesign");
    }
}