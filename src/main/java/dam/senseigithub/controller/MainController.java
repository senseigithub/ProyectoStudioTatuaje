package dam.senseigithub.controller;

import dam.senseigithub.App;
import javafx.fxml.FXML;
import java.io.IOException;

public class MainController {
    @FXML
    private void switchToAddUser() throws IOException {
        App.setRoot("AddUser");
    }

    @FXML
    private void switchToClientList() throws IOException {
        App.setRoot("ClientList");
    }

    @FXML
    private void switchToDeleteClient() throws IOException {
        App.setRoot("DeleteClient");
    }

    @FXML
    private void switchToModifyClient() throws IOException {
        App.setRoot("ModifyClient");
    }
}
