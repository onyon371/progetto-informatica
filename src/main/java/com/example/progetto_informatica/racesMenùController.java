package com.example.progetto_informatica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class racesMenùController implements Initializable {
    @FXML private VBox pilotsRankingContainer;
    @FXML private VBox racesContainer;

    private Championship championshipReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championshipReference = null;
    }

    public void initChampionhip(Championship championshipReference)
    {
        this.championshipReference = championshipReference;

        try {
            setPilotsRankingContainer();
            addRacesCard();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private void setPilotsRankingContainer() {
        pilotsRankingContainer.getChildren().clear();
        ArrayList<PilotPoint> bestPilots = championshipReference.getBestPilotsAndPoints();

        AtomicInteger counter = new AtomicInteger(1);

        bestPilots.forEach(pilot -> {
            Label pilotLabel = new Label(counter.incrementAndGet() + " " + pilot.getP().toString() + " Punti: " + pilot.getPoints());
            pilotLabel.getStyleClass().add("winner-name");
            pilotsRankingContainer.getChildren().add(pilotLabel);
        });
    }

    private void addRacesCard() {
        racesContainer.getChildren().clear();

        AtomicInteger counter = new AtomicInteger(0);

        championshipReference.getRaces().forEach(race -> {
            VBox raceBox = new VBox();
            raceBox.getStyleClass().add("race-container");

            HBox headerBox = new HBox(10);
            headerBox.getStyleClass().add("race-header");

            Label titleLabel = new Label(race.getName());
            titleLabel.getStyleClass().add("race-title");

            Label dateLabel = new Label(race.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            dateLabel.getStyleClass().add("race-date");

            headerBox.getChildren().addAll(titleLabel, dateLabel);

            HBox detailsBox = new HBox(20);
            detailsBox.getStyleClass().add("race-details");

            Label participantsLabel = new Label("Partecipanti: " + race.getPilots().size());
            participantsLabel.getStyleClass().add("race-info");

            String temp = "";

            try
            {
                temp = race.getBestPilotsAndPoints().getFirst().getP().toString();
            }catch (Exception e)
            {
                System.err.println(e.getMessage());
            }

            Label winnerLabel = new Label("Primo Classificato: " + temp);
            winnerLabel.getStyleClass().addAll("race-info", "winner-info");

            Label statusLabel = new Label(race.isRaceOpen() ? "In corso" : "Terminata");
            statusLabel.getStyleClass().add("race-status");

            if (!race.isRaceOpen()) {
                statusLabel.getStyleClass().add("completed");
            } else {
                statusLabel.getStyleClass().add("in-progress");
            }

            raceBox.setOnMouseClicked(event -> {
                Main.openSpecificRaceMenù(race);
            });

            detailsBox.getChildren().addAll(participantsLabel, winnerLabel, statusLabel);
            raceBox.getChildren().addAll(headerBox, detailsBox);
            racesContainer.getChildren().add(raceBox);
        });
    }

   @FXML
    private void handleAddRace() {

       TextInputDialog dialog = new TextInputDialog();
       dialog.setTitle("Aggiungi gara");
       dialog.setHeaderText("Inserisci i dettagli della nuova gara");
       dialog.setContentText("Nome gara:");

       Optional<String> raceName = dialog.showAndWait();

       if(!raceName.isEmpty()) {
           championshipReference.addRace(raceName.get());
           addRacesCard();
       }
    }

    @FXML
    private void handleEditRace() {

    }

    @FXML
    private void handleDeleteRace() {

    }

    @FXML
    private void handleBackToChampionshipMenù()
    {
        Main.openChampionshipsMenù();
    }

    @FXML
    private void handleAddPilot()
    {
        Main.openSavedPilotsView(championshipReference);
    }
}