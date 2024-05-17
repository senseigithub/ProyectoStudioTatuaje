package dam.senseigithub.model.dao;

import dam.senseigithub.model.entity.Client;

import java.util.List;

public interface IClientDAO {

    public void addClient(Client client);

    public Client getClientByName(String name);

    public void updateClient(Client client);

    public void deleteClient(Client client);

    public List<Client> getAllClients();
}
