module dam.senseigithub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens senseigithub to javafx.fxml;
    opens senseigithub.model.connection to java.xml.bind;

    exports dam.senseigithub;
    exports dam.senseigithub.controller;
    opens senseigithub.controller to javafx.fxml;
}

