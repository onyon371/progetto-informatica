package com.example.progetto_informatica.model;
import com.example.progetto_informatica.controller.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private static Stage mainStage;
    private static Stage pilotViewStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setTitle("Gestione Tornei");

        openChampionshipsMenù();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void openChampionshipsMenù() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/championshipsMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
            mainStage.setScene(scene);
            mainStage.show();

            tryAndCloseSavedPilotsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSingleChampionshipMenù(Championship championshipReference) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/racesMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);

            SingleChampionshipMenùController controller = fxmlLoader.getController();
            controller.initChampionhip(championshipReference);

            mainStage.setScene(scene);
            mainStage.show();

            tryAndCloseSavedPilotsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSpecificRaceMenù(Race raceReference, Championship championshipReference) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/SingleRaceMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);

            SingleRaceMenùController controller = fxmlLoader.getController();
            controller.initRaceReference(raceReference, championshipReference);

            mainStage.setScene(scene);
            mainStage.show();

            tryAndCloseSavedPilotsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSavedPilotsView(Championship championshipReference, SingleChampionshipMenùController singleChampionshipMenùControllerReference) {
        if (pilotViewStage == null) {
            try {
                pilotViewStage = new Stage();
                pilotViewStage.setTitle("Gestione Piloti");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/savedPilotsView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 300, 300);

                SavedPilotsController controller = fxmlLoader.getController();
                controller.initChampionship(championshipReference, singleChampionshipMenùControllerReference);

                pilotViewStage.setScene(scene);
                pilotViewStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openShowThrowsView(Pilot pilotReference, Race raceReference, Championship championshipReference, List<Throws> throwsList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/showThrows.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            ShowThrowsController controller = fxmlLoader.getController();
            controller.initThrowsData(pilotReference, championshipReference, raceReference, throwsList);

            mainStage.setScene(scene);
            mainStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tryAndCloseSavedPilotsView() {
        if (pilotViewStage != null) {
            pilotViewStage.close();
            pilotViewStage = null;
        }
    }

    @Override
    public void stop() {
        AllChampionshipsMenùController.saveChampionships();
        //SavedPilotsController.savePilots();
    }
}




