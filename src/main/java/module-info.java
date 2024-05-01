module dam.senseigithub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens dam.senseigithub to javafx.fxml;
    opens dam.senseigithub.model.connection to java.xml.bind;

    exports dam.senseigithub;
    exports dam.senseigithub.controller;
    opens dam.senseigithub.controller to javafx.fxml;
    exports dam.senseigithub.controller.clients;
    opens dam.senseigithub.controller.clients to javafx.fxml;
    exports dam.senseigithub.controller.designs;
    opens dam.senseigithub.controller.designs to javafx.fxml;
}

