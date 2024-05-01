package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
        clientTableView.setEditable(true);
        dniColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getDnie()));
        nameColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getName()));
        emailColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getEmail()));
        phoneColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getPhone()));
        List<Client> clients = clientDAO.getAllClients();
        ObservableList<Client> observableClients = FXCollections.observableArrayList(clients);
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
