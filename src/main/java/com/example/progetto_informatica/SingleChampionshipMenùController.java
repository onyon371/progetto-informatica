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

public class SingleChampionshipMenùController implements Initializable {
    @FXML private VBox pilotsRankingContainer;
    @FXML private VBox racesContainer;
    @FXML private VBox pilotsParticipantsContainer;

    private Championship championshipReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championshipReference = null;
    }

    public void initChampionhip(Championship championshipReference)
    {
        this.championshipReference = championshipReference;

        try {
            setParticipantsContainer();
            setPilotsRankingContainer();
            addRacesCard();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setParticipantsContainer()
    {
        pilotsParticipantsContainer.getChildren().clear();

        AtomicInteger counter = new AtomicInteger(0);

        if(championshipReference.getPilots().isEmpty())
        {
            Label pilotLabel = new Label("Nessun Partecipante!");
            pilotLabel.getStyleClass().add("winner-name");
            pilotsParticipantsContainer.getChildren().add(pilotLabel);
            return;
        }

        championshipReference.getPilots().forEach(pilot -> {
            Label pilotLabel = new Label(counter.incrementAndGet() + ". " + pilot.toString());
            pilotLabel.getStyleClass().add("winner-name");
            pilotsParticipantsContainer.getChildren().add(pilotLabel);
        });
    }

    private void setPilotsRankingContainer() {
        pilotsRankingContainer.getChildren().clear();
        ArrayList<PilotPoint> bestPilots = championshipReference.getBestPilotsAndPoints();

        AtomicInteger counter = new AtomicInteger(0);

        if(bestPilots.isEmpty())
        {
            Label pilotLabel = new Label("Classifica Vuota!");
            pilotLabel.getStyleClass().add("winner-name");
            pilotsRankingContainer.getChildren().add(pilotLabel);
            return;
        }

        bestPilots.forEach(pilot -> {
            Label pilotLabel = new Label(counter.incrementAndGet() + " " + pilot.getP().toString() + " Punti: " + pilot.getPoints());
            pilotLabel.getStyleClass().add("winner-name");
            pilotsRankingContainer.getChildren().add(pilotLabel);
        });
    }

    private void addRacesCard() {
        racesContainer.getChildren().clear();

        AtomicInteger counter = new AtomicInteger(0);

        if(championshipReference.getRaces().isEmpty())
        {

        }

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

            Label winnerLabel = null;

            try
            {
                winnerLabel = new Label("Primo Classificato: " + race.getBestPilotsAndPoints().getFirst().getP().toString());
                winnerLabel.getStyleClass().addAll("race-info", "winner-info");
            }catch (Exception e)
            {
                System.err.println("SingleChampionshipMenùController: Tentativo di aggiungere i migliori classificati di una gara ma non è presente");
            }

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

            detailsBox.getChildren().add(participantsLabel);
            if(winnerLabel!=null)
            {
                detailsBox.getChildren().add(winnerLabel);
            }
            detailsBox.getChildren().add(statusLabel);

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