package dam.senseigithub.controller.designs;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.DesignDAO;
import dam.senseigithub.model.entity.Design;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AddDesignController {
    @FXML
    private HBox hbox;
    @FXML
    private ImageView preview;
    @FXML
    private TextField name;
    @FXML
    private TextField size;

    private byte[] imageToDB;

    private DesignDAO designDAO = new DesignDAO();

    @FXML
    public void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona la imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images Files", "*.jpeg", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(hbox.getScene().getWindow());
        if (selectedFile != null) {
            preview.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
            preview.setVisible(true);
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                imageToDB = new byte[(int) selectedFile.length()];
                fis.read(imageToDB);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void uploadImage() {
        String designName = name.getText();
        String sizeText = size.getText();

        // Verificar que los campos de texto no estén vacíos
        if (designName.isEmpty() || sizeText.isEmpty()) {
            showAlert("Error", "Campos incompletos", "Por favor, complete todos los campos.");
            return;
        }

        // Verificar si el tamaño es un número flotante válido
        float designSize;
        try {
            designSize = Float.parseFloat(sizeText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Tamaño inválido", "El tamaño debe ser un número válido.");
            return;
        }

        // Verificar si se ha seleccionado una imagen
        if (imageToDB == null) {
            showAlert("Error", "Imagen no seleccionada", "Por favor, seleccione una imagen.");
            return;
        }

        // Crear el objeto Design
        Design design = new Design();
        design.setName(designName);
        design.setSize(designSize);
        design.setImagen(imageToDB);

        // Agregar el diseño usando el DesignDAO
        designDAO.addDesign(design);

        // Mostrar una alerta de éxito
        showAlert("Éxito", "Diseño agregado", "El diseño se ha agregado con éxito.");

        // Volver al menú principal
        try {
            App.setRoot("mainView"); // Suponiendo que tienes un método en la clase App para cambiar la vista al menú principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
