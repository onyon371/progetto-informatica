package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleRaceMenùController implements Initializable {

    @FXML
    private VBox pilotsAnchor;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField searchField;

    private Championship championshipReference;
    private Race raceReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.setPromptText("Cerca pilota...");
        // Listener per la ricerca
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (raceReference != null) {
                renderPilotCards(newVal);
            }
        });

    }

    public void initRaceReference(Race raceReference, Championship championshipReference) {
        this.raceReference = raceReference;
        this.championshipReference = championshipReference;
        renderPilotCards(""); // Mostra tutti I piloti all'avvio
    }

    private void renderPilotCards(String filter) {
        pilotsAnchor.getChildren().clear();
        Set<String> nomiVisualizzati = new HashSet<>();
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < raceReference.getPilots().size(); i++) {
            String pilotName = raceReference.getPilots().get(i).toString().toLowerCase();

            if ((filter == null || filter.isEmpty() || pilotName.contains(filter.toLowerCase()))
                    && !nomiVisualizzati.contains(pilotName)) {

                nomiVisualizzati.add(pilotName); // Evita duplicati

                HBox card = createPilotCard(i, counter.incrementAndGet());
                pilotsAnchor.getChildren().add(card);
            }
        }
    }

    private HBox createPilotCard(int i, int rank) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10;");

        Label rankLabel = new Label(rank + ".");
        rankLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox infoBox = new VBox(5);
        Label nameLabel = new Label(raceReference.getPilots().get(i).toString());
        HBox stats = new HBox(15);
        stats.getChildren().addAll(
                new Label("Lanci Effettuati: " + raceReference.getThrowsCompletedOfSpecificPilot(i) + "/" + raceReference.getMaxThrows()),
                new Label(raceReference.getBestTimeOfSpecificPilot(i) != LocalTime.of(0, 0, 0, 0)
                        ? "Miglior Tempo: " + raceReference.getBestTimeOfSpecificPilot(i)
                        : "Miglior Tempo: Nessun Lancio Registrato!"),
                new Label("Punti Totali: " + raceReference.getPointsOfSpecificPilot(i) + "/" + raceReference.getMaxPoints())
        );

        infoBox.getChildren().addAll(nameLabel, stats);
        card.getChildren().addAll(rankLabel, infoBox);
        DropShadow shadow = new DropShadow(10, 5, 5, Color.DARKGRAY);  // Effetto ombra
        // Aggiungi effetto hover
        // Gestione degli eventi mouse
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #f8a400; -fx-background-radius: 10;");
            card.setEffect(shadow);
        });
        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10;");
            card.setEffect(null);
        });

        return card;
    }

    @FXML
    private void handleBackToChampionshipMenù()
    {
        Main.openSingleChampionshipMenù(championshipReference);
    }
}

