package dam.senseigithub.controller;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.DesignDAO;
import dam.senseigithub.model.entity.Design;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane imageGridPane;

    private DesignDAO designDAO = new DesignDAO(); // Suponiendo que tienes un DesignDAO para acceder a las imágenes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar el espacio entre las celdas del GridPane
        imageGridPane.setHgap(120);
        imageGridPane.setVgap(50);

        try {
            // Obtener la lista de diseños de la base de datos
            List<Design> designs = designDAO.getAllDesigns();

            // Iterar sobre la lista de diseños y crear un ImageView para cada uno
            int column = 0;
            int row = 0;
            for (Design design : designs) {
                Image image = new Image(design.getImageInputStream()); // Suponiendo que tienes un método para obtener un InputStream de la imagen
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(230); // Ajusta el ancho según tus necesidades
                imageView.setFitHeight(200); // Ajusta la altura según tus necesidades

                // Agrega el ImageView al GridPane
                imageGridPane.add(imageView, column, row);

                // Agregar evento de doble clic a cada ImageView
                imageView.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        handleDoubleClick(design);
                    }
                });

                // Avanza a la siguiente fila si se alcanza el límite de columnas
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }

    private void refreshDesignList() {
        try {
            List<Design> designs = designDAO.getAllDesigns();
            imageGridPane.getChildren().clear(); // Limpiar el GridPane
            int column = 0;
            int row = 0;
            for (Design design : designs) {
                Image image = new Image(design.getImageInputStream());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(230);
                imageView.setFitHeight(200);
                imageView.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        handleDoubleClick(design);
                    }
                });
                imageGridPane.add(imageView, column, row);
                // Avanzar a la siguiente fila si se alcanza el límite de columnas
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleDoubleClick(Design design) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Seguro que quieres borrar este diseño?");
        alert.setContentText("Esta acción no se puede deshacer.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    designDAO.deleteDesign(design);
                    refreshDesignList(); // Actualizar la lista de diseños y refrescar la vista
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
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

    @FXML
    private void switchToAddAppointment() throws IOException {
        App.setRoot("AddAppointment");
    }

    @FXML
    private void switchToDeleteAppointment() throws IOException {
        App.setRoot("DeleteAppointment");
    }
}
