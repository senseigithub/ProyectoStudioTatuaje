package dam.senseigithub.controller;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.DesignDAO;
import dam.senseigithub.model.entity.Design;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane imageGridPane;

    private DesignDAO designDAO = new DesignDAO(); // Suponiendo que tienes un DesignDAO para acceder a las imágenes

    @FXML
    public void initialize() {
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
