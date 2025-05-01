package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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

    private void removeRace(Race raceReference)
    {
        championshipReference.getRaces().remove(raceReference);
        addRacesCard();
    }

    private void addRacesCard() {
        racesContainer.getChildren().clear();

        AtomicInteger counter = new AtomicInteger(0);

        if (championshipReference.getRaces().isEmpty()) {
            // eventualmente mostra un messaggio
        }

        championshipReference.getRaces().forEach(race -> {
            // Contenitore principale con stile del rettangolo
            BorderPane racePane = new BorderPane();
            racePane.getStyleClass().add("race-container");

            // VBox con i contenuti originali
            VBox raceBox = new VBox();
            raceBox.setSpacing(10);

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
            try {
                winnerLabel = new Label("Primo Classificato: " + race.getBestPilotsAndPoints().getFirst().getP().toString());
                winnerLabel.getStyleClass().addAll("race-info", "winner-info");
            } catch (Exception e) {
                System.err.println("Tentativo di aggiungere vincitore ma non presente");
            }

            Label statusLabel = new Label(race.isRaceOpen() ? "In corso" : "Terminata");
            statusLabel.getStyleClass().add("race-status");
            if (!race.isRaceOpen()) {
                statusLabel.getStyleClass().add("completed");
            } else {
                statusLabel.getStyleClass().add("in-progress");
            }

            detailsBox.getChildren().add(participantsLabel);
            if (winnerLabel != null) {
                detailsBox.getChildren().add(winnerLabel);
            }
            detailsBox.getChildren().add(statusLabel);

            raceBox.getChildren().addAll(headerBox, detailsBox);

            // Menu a tre puntini
            MenuItem editItem = new MenuItem("Modifica");
            MenuItem deleteItem = new MenuItem("Elimina");

            editItem.setOnAction(e -> {
                System.out.println("Modifica gara: " + race.getName());
            });

            deleteItem.setOnAction(e -> {
                removeRace(race);
            });

            MenuButton optionsButton = new MenuButton("⋮", null, editItem, deleteItem);
            optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
            optionsButton.getStyleClass().add("three-dots");

            // Blocca propagazione clic sul menu
            optionsButton.setOnMouseClicked(e -> e.consume());

            // Assembla nel BorderPane
            racePane.setCenter(raceBox);
            racePane.setRight(optionsButton);
            racePane.setStyle("-fx-padding: 10;");

            // Clic sulla card
            racePane.setOnMouseClicked(event -> {
                Main.openSpecificRaceMenù(race, championshipReference);
            });

            racesContainer.getChildren().add(racePane);
        });
    }

   @FXML
    private void handleAddNewRace() {

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
    private void handleBackToChampionshipMenù()
    {
        Main.openChampionshipsMenù();
    }

    @FXML
    private void handleAddNewPilot()
    {
        Main.openSavedPilotsView(championshipReference, this);
    }
}