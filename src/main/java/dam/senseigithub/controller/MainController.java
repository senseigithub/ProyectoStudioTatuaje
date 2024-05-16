package dam.senseigithub.controller;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.dao.DesignDAO;
import dam.senseigithub.model.entity.Client;
import dam.senseigithub.model.entity.Design;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField textFieldBusqueda;

    private DesignDAO designDAO = new DesignDAO();

    private final ClientDAO clientDAO = new ClientDAO();

    /**
     * Buscar un cliente
     */
    @FXML
    private void search() {
        String terminoBusqueda = textFieldBusqueda.getText().trim();
        Client resultadoBusqueda = clientDAO.getClientByName(terminoBusqueda);

        if (resultadoBusqueda != null) {
            clientDetails(resultadoBusqueda);
        } else {
            mostrarMensajeAlerta("Cliente no encontrado", "No se encontró ningún cliente con el nombre: " + terminoBusqueda);
        }
        textFieldBusqueda.clear();
    }

    /**
     * Te muestra los detalles del cliente.
     * @param cliente recibe el cliente buscado.
     */
    private void clientDetails(Client cliente) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Detalles del cliente");
        alerta.setHeaderText(null);
        alerta.setContentText("ID: " + cliente.getIdClient() + "\n" +
                "Nombre: " + cliente.getName() + "\n" +
                "DNI: " + cliente.getDnie() + "\n" +
                "Email: " + cliente.getEmail() + "\n" +
                "Teléfono: " + cliente.getPhone());
        alerta.showAndWait();
    }

    private void mostrarMensajeAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Inicializa la pantalla con las fotos de los diseños de los clientes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageGridPane.setHgap(120);
        imageGridPane.setVgap(50);

        try {
            List<Design> designs = designDAO.getAllDesigns();
            int column = 0;
            int row = 0;
            for (Design design : designs) {
                Image image = new Image(design.getImageInputStream());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(230);
                imageView.setFitHeight(200);
                imageGridPane.add(imageView, column, row);

                imageView.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        handleDoubleClick(design);
                    }
                });

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

    /**
     * Resfresca los diseños cuando añades uno a un cliente.
     */
    private void refreshDesignList() {
        try {
            List<Design> designs = designDAO.getAllDesigns();
            imageGridPane.getChildren().clear();
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

    /**
     * Acción cuando haces doble clic en un diseño
     * @param design el diseño que has pickeado.
     */
    private void handleDoubleClick(Design design) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Seguro que quieres borrar este diseño?");
        alert.setContentText("Esta acción no se puede deshacer.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    designDAO.deleteDesign(design);
                    refreshDesignList();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Cambiar a la lista de clientes.
     * @throws IOException
     */
    @FXML
    private void switchToClientList() throws IOException {
        App.setRoot("ClientList");
    }

    /**
     * Cambiar a la vista de agregar un cliente.
     * @throws IOException
     */
    @FXML
    private void switchToAddUser() throws IOException {
        App.setRoot("AddClient");
    }

    /**
     * Cambias a la vista de borrar un cliente.
     * @throws IOException
     */
    @FXML
    private void switchToDeleteClient() throws IOException {
        App.setRoot("DeleteClient");
    }

    /**
     * Cambiar a la vista de agregar un diseño.
     * @throws IOException
     */
    @FXML
    private void switchToAddDesign() throws IOException {
        App.setRoot("AddDesign");
    }

    /**
     * Cambiar a la vista de agregar una cita.
     * @throws IOException
     */
    @FXML
    private void switchToAddAppointment() throws IOException {
        App.setRoot("AddAppointment");
    }
}
