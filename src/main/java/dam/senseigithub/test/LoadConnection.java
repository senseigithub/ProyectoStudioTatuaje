package dam.senseigithub.test;

import dam.senseigithub.model.connection.ConnectionProperties;
import dam.senseigithub.utils.XMLManager;

public class LoadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
