module com.example.progetto_informatica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;


    exports com.example.progetto_informatica.controller;
    opens com.example.progetto_informatica.controller to javafx.fxml;
    exports com.example.progetto_informatica.model;
    opens com.example.progetto_informatica.model to javafx.fxml;
}