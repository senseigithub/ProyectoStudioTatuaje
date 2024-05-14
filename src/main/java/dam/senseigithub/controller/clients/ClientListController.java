package dam.senseigithub.controller.clients;

import dam.senseigithub.App;
import dam.senseigithub.controller.Controller;
import dam.senseigithub.model.dao.AppointmentDAO;
import dam.senseigithub.model.dao.ClientDAO;
import dam.senseigithub.model.entity.Appointment;
import dam.senseigithub.model.entity.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import java.util.ResourceBundle;

public class ClientListController extends Controller implements Initializable {

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
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientTableView.setEditable(true);
        dniColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getDnie()));
        nameColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getName()));
        emailColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getEmail()));
        phoneColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getPhone()));
        List<Client> clients = clientDAO.getAllClients();
        ObservableList<Client> observableClients = FXCollections.observableArrayList(clients);
        clientTableView.setItems(observableClients);
        clientTableView.setRowFactory(tableView -> {
            final TableRow<Client> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem viewAppointmentsItem = new MenuItem("Ver Citas");
            viewAppointmentsItem.setOnAction(event -> {
                Client client = row.getItem();
                if (client != null) {
                    showAppointmentsDialog(client);
                }
            });

            final MenuItem deleteAppointmentsItem = new MenuItem("Borrar todas las citas");
            deleteAppointmentsItem.setOnAction(event -> {
                Client client = row.getItem();
                if (client != null) {
                    appointmentDAO.deleteAllAppointmentsByClientName(client.getName());
                }
            });

            contextMenu.getItems().addAll(viewAppointmentsItem, deleteAppointmentsItem);
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }
    private void showAppointmentsDialog(Client client) {
        List<Appointment> appointments = appointmentDAO.getAppointmentsByClientId(client.getIdClient());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Citas de " + client.getName());
        alert.setHeaderText(null);
        if (appointments.isEmpty()) {
            alert.setContentText("Este cliente no tiene citas.");
        } else {
            StringBuilder content = new StringBuilder("Citas de " + client.getName() + ":\n\n");
            for (Appointment appointment : appointments) {
                content.append("- ").append(appointment.getDate()).append("\n");
            }
            alert.setContentText(content.toString());
        }
        alert.showAndWait();
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
    public void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}
