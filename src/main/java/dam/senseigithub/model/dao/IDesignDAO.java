package dam.senseigithub.model.dao;

import dam.senseigithub.model.entity.Client;
import dam.senseigithub.model.entity.Design;

public interface IDesignDAO {

    public void addDesign(Design design, Client client);
    
}
