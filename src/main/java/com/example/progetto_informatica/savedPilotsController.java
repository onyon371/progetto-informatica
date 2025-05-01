package com.example.progetto_informatica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class savedPilotsController implements Initializable {

    @FXML
    private VBox pilotsAnchor;

    private Championship championshipReference;
    private ArrayList<Pilot> savedPilots;

    final String SAVEPATH = "pilots.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championshipReference = null;
    }

    public void initChampionship(Championship championshipReference)
    {
        this.championshipReference = championshipReference;

        try {

        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private void addPilotsCard() {
        pilotsAnchor.getChildren().clear();

        savedPilots.forEach(pilot -> {
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
                main.openSpecificRaceMenù(race);
            });

            detailsBox.getChildren().addAll(participantsLabel, winnerLabel, statusLabel);
            raceBox.getChildren().addAll(headerBox, detailsBox);
            racesContainer.getChildren().add(raceBox);
        });
    }


    private void getSavedPilots() {
        try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVEPATH));
        savedPilots = (ArrayList<Pilot>) in.readObject();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void savePilots() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVEPATH));
            out.writeObject(savedPilots);
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

}
