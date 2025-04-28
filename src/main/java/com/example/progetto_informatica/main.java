package com.example.progetto_informatica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

public class main extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setTitle("Programma");

        changeScene("gareMenù.fxml", "gareMenùStyle.css");
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String sceneName, String cssName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(main.class.getResource(cssName).toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();
    }
}




