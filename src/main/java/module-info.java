module com.example.progetto_informatica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;


    opens com.example.progetto_informatica to javafx.fxml;
    exports com.example.progetto_informatica;
}