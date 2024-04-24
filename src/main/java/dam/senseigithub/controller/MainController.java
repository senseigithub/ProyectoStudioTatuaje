package dam.senseigithub.controller;

import dam.senseigithub.App;
import javafx.fxml.FXML;
import java.io.IOException;

public class MainController {

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
    public static void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}
