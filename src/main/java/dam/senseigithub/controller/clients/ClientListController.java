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
import javafx.scene.control.cell.TextFieldTableCell;

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

    @FXML
    private TextField textFieldBusqueda;

    private ClientDAO clientDAO = new ClientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientTableView.setEditable(true);
        dniColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getDnie()));
        nameColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getName()));
        emailColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getEmail()));
        phoneColumn.setCellValueFactory(client -> new SimpleStringProperty(client.getValue().getPhone()));
        configureColumnEditable(dniColumn, "dnie");
        configureColumnEditable(nameColumn, "name");
        configureColumnEditable(emailColumn, "email");
        configureColumnEditable(phoneColumn, "phone");
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


    /**
     * Configura el campo de texto de una columna editable.
     * @param column Columna a configurar
     * @param fieldName Campo a configurar
     */
    private void configureColumnEditable(TableColumn<Client, String> column, String fieldName) {
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            Client cliente = event.getRowValue();
            switch (fieldName) {
                case "dnie":
                    cliente.setDnie(newValue);
                    break;
                case "name":
                    cliente.setName(newValue);
                    break;
                case "email":
                    cliente.setEmail(newValue);
                    break;
                case "phone":
                    cliente.setPhone(newValue);
                    break;
                default:
                    break;
            }
            clientDAO.updateClient(cliente);
            refreshTable();
        });
    }

    /**
     * Refresca la tabla de clientes, y cada vez que hay un cliente nuevo.
     */
    private void refreshTable() {
        List<Client> clients = clientDAO.getAllClients();
        clientTableView.getItems().setAll(clients);
    }

    /**
     * Muestra un JDialog con las citas que tiene un cliente.
     * @param client
     */
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

    /**
     * Buscas un cliente por su nombre y muestra sus detalles.
     */
    @FXML
    private void buscar() {
        String terminoBusqueda = textFieldBusqueda.getText().trim();
        Client resultadoBusqueda = clientDAO.getClientByName(terminoBusqueda);

        if (resultadoBusqueda != null) {
            mostrarDetallesCliente(resultadoBusqueda);
        } else {
            mostrarMensajeAlerta("Cliente no encontrado", "No se encontró ningún cliente con el nombre: " + terminoBusqueda);
        }
        textFieldBusqueda.clear();
    }

    /**
     * Muestra todos los atributos del cliente, que has buscado.
     * @param cliente
     */
    private void mostrarDetallesCliente(Client cliente) {
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

    /**
     * Muestra una alerta de error
     * @param titulo
     * @param mensaje
     */
    private void mostrarMensajeAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Cambia a la lista de clientes
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
     * Cambiar a añadir diseño.
     * @throws IOException
     */
    @FXML
    private void switchToAddDesign() throws IOException {
        App.setRoot("AddDesign");
    }

    /**
     * Volver a la vista principal.
     * @throws IOException
     */
    @FXML
    public void backToMainView() throws IOException {
        App.setRoot("mainView");
    }
}
