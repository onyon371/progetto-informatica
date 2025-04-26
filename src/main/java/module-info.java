module com.example.progetto_informatica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.progetto_informatica to javafx.fxml;
    exports com.example.progetto_informatica;
    exports com.example.progetto_informatica.controller;
    opens com.example.progetto_informatica.controller to javafx.fxml;
}