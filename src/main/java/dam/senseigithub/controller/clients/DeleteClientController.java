package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class DeleteClientController {

    @FXML
    private void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}