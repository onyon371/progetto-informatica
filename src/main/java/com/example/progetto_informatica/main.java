package com.example.progetto_informatica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class main extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setTitle("Gestione Tornei");

        openChampionshipsMenù();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void openChampionshipsMenù()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("championshipsMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);
            mainStage.setScene(scene);
            mainStage.show();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public static void openRacesMenù(Championship championshipReference)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("racesMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);

            racesMenùController controller = fxmlLoader.getController();
            controller.initChampionhip(championshipReference);

            mainStage.setScene(scene);
            mainStage.show();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public static void openSpecificRaceMenù(Race raceReference)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("racesMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 600);

            specificRaceMenùController controller = fxmlLoader.getController();
            controller.initRace(raceReference);

            mainStage.setScene(scene);
            mainStage.show();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void stop() {
        championshipsMenùController.saveChampionships();
    }
}




