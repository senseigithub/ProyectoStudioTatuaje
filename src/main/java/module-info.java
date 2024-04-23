module dam.senseigithub {
    requires javafx.controls;
    requires javafx.fxml;

    opens dam.senseigithub to javafx.fxml;
    exports dam.senseigithub;
}
