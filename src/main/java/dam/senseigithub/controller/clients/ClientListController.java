package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class ClientListController {

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, String> dniColumn;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    private ClientDAO clientDAO = new ClientDAO();

    @FXML
    public void initialize() {
        System.out.println("Inicializando ClientListController...");
        // Configurar las columnas de la TableView para que obtengan los datos de las propiedades de la clase Client
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dnie"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Obtener la lista de clientes del DAO
        List<Client> clients = clientDAO.getAllClients();

        // Convertir la lista de clientes a un ObservableList (necesario para la TableView)
        ObservableList<Client> observableClients = FXCollections.observableArrayList(clients);

        // Asignar los datos a la TableView
        clientTableView.setItems(observableClients);
    }

    @FXML
    private void backToMainView() throws IOException {
        App.setRoot("mainView");
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
