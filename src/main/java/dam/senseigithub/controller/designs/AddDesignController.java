package dam.senseigithub.controller.designs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddDesignController implements Initializable {
    @FXML
    private HBox hbox;
    @FXML
    private ImageView preview;

    private byte[] imageToDB;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void addDesign(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona la imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images Files", "*.jpeg", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            System.out.println(selectedFile);
            System.out.println(preview);
            preview.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
            preview.setVisible(true);
            try(FileInputStream fis = new FileInputStream(selectedFile)){
                imageToDB = new byte[(int) selectedFile.length()];
                fis.read(imageToDB);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //hacer lo que tengas que hacer que será guardarlo en una variable y
            //mostrarlo en el preview

        }
    }
}
