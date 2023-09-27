module com.example.gridstuff {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gridstuff to javafx.fxml;
    exports com.example.gridstuff;
}