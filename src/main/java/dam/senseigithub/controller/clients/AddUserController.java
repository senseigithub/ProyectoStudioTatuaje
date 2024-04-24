package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import dam.senseigithub.controller.MainController;
import javafx.fxml.FXML;

import java.io.IOException;

public class AddUserController {

    @FXML
    private void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}
