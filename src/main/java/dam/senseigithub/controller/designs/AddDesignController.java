package dam.senseigithub.controller.designs;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.dao.DesignDAO;
import dam.senseigithub.model.entity.Client;
import dam.senseigithub.model.entity.Design;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddDesignController implements Initializable {
    @FXML
    private HBox hbox;
    @FXML
    private ImageView preview;
    @FXML
    private TextField name;
    @FXML
    private TextField size;
    @FXML
    private ComboBox<String> clientComboBox;

    private byte[] imageToDB;

    private DesignDAO designDAO = new DesignDAO();

    private ClientDAO clientDAO = new ClientDAO();

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

    private void loadClientNames() {
        clientComboBox.getItems().clear();
        clientDAO.getAllClients().forEach(client -> clientComboBox.getItems().add(client.getName()));
    }
    @FXML
    public void uploadImage() {
        String selectedClientName = clientComboBox.getValue();
        String designName = name.getText();
        String sizeText = size.getText();

        // Verificar si no se seleccionó un cliente
        if (selectedClientName == null) {
            showAlert("Error", "Cliente no seleccionado", "Por favor, seleccione un cliente.");
        }

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

        // Obtener el cliente por su nombre
        Client client = clientDAO.getClientByName(selectedClientName);
        if (client == null) {
            showAlert("Error", "Cliente no seleccionado", "Por favor, seleccione un cliente.");
            return;
        }

        // Verificar si se ha seleccionado una imagen
        if (imageToDB == null) {
            showAlert("Error", "Imagen no seleccionada", "Por favor, seleccione una imagen.");
            return;
        }

        // Crear el objeto Design
        Design design = new Design();
        client.setName(selectedClientName);
        design.setName(designName);
        design.setSize(designSize);
        design.setImagen(imageToDB);

        // Agregar el diseño usando el DesignDAO
        designDAO.addDesign(design, client);

        // Mostrar una alerta de éxito
        showAlertConfirm("Éxito", "Diseño agregado", "El diseño se ha agregado con éxito.");

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

    private void showAlertConfirm(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadClientNames();
    }
}
