package com.example.progetto_informatica.model;

import com.example.progetto_informatica.controller.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    // Stage principale dell'applicazione (finestra principale)
    private static Stage mainStage;

    // Finestra separata per la visualizzazione dei piloti salvati
    private static Stage pilotViewStage;

    @Override
    public void start(Stage stage) throws IOException {
        // Metodo chiamato all'avvio dell'applicazione JavaFX
        mainStage = stage;
        stage.setTitle("Gestione Tornei");

        // Carica il menù principale dei campionati
        openChampionshipsMenù();
    }

    public static void main(String[] args) {
        // Punto di ingresso dell'applicazione
        launch();
    }

    public static void openChampionshipsMenù() {
        // Apre la schermata del menù principale dei campionati
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/championshipsMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
            mainStage.setScene(scene);
            mainStage.show();

            // Chiude la finestra dei piloti salvati se è aperta
            tryAndCloseSavedPilotsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSingleChampionshipMenù(Championship championshipReference) {
        // Apre il menù di un singolo campionato selezionato
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/racesMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 800);

            // Inizializza il controller con il riferimento al campionato
            SingleChampionshipMenùController controller = fxmlLoader.getController();
            controller.initChampionhip(championshipReference);

            mainStage.setScene(scene);
            mainStage.show();

            // Chiude la finestra dei piloti salvati se è aperta
            tryAndCloseSavedPilotsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSpecificRaceMenù(Race raceReference, Championship championshipReference) {
        // Apre il menù di una gara specifica all'interno di un campionato
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/SingleRaceMenù.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);

            // Inizializza il controller con i riferimenti alla gara e al campionato
            SingleRaceMenùController controller = fxmlLoader.getController();
            controller.initRaceReference(raceReference, championshipReference);

            mainStage.setScene(scene);
            mainStage.show();

            // Chiude la finestra dei piloti salvati se è aperta
            tryAndCloseSavedPilotsView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSavedPilotsView(Championship championshipReference, SingleChampionshipMenùController singleChampionshipMenùControllerReference) {
        // Apre una finestra secondaria per gestire i piloti salvati
        if (pilotViewStage == null) {
            try {
                pilotViewStage = new Stage();
                pilotViewStage.setTitle("Gestione Piloti");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/savedPilotsView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 500, 500);

                // Inizializza il controller con il campionato e controller di riferimento
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
        // Apre la vista per mostrare i lanci di un pilota in una gara specifica
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/progetto_informatica/viewFiles/showThrows.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            // Inizializza il controller con i dati dei lanci
            ShowThrowsController controller = fxmlLoader.getController();
            controller.initThrowsData(pilotReference, championshipReference, raceReference, throwsList);

            mainStage.setScene(scene);
            mainStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tryAndCloseSavedPilotsView() {
        // Chiude la finestra dei piloti salvati se è aperta
        if (pilotViewStage != null) {
            pilotViewStage.close();
            pilotViewStage = null;
        }
    }

    @Override
    public void stop() {
        // Metodo chiamato alla chiusura dell'applicazione, salva i dati dei campionati
        AllChampionshipsMenùController.saveChampionships();
    }
}