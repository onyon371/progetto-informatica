package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowThrowsController implements Initializable {

    @FXML
    private VBox throwsAnchor;

    private Pilot pilotReference;
    private Championship championshipReference;
    private Race raceReference;
    private List<Throws> throwsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initThrowsData(Pilot pilotReference,Championship championshipReference, Race raceReference, List<Throws> throwsList) {
        this.pilotReference = pilotReference;
        this.championshipReference = championshipReference;
        this.raceReference = raceReference;
        this.throwsList = throwsList;

        addThrowsCards();
    }

    private void addThrowsCards() {
        throwsAnchor.getChildren().clear();

        for (int i = 0; i < 4; i++) {
            HBox card = new HBox(20);
            card.setPadding(new Insets(15));
            card.setStyle("-fx-background-color: #d9eaf7; -fx-background-radius: 10; -fx-border-radius: 10;");

            Label throwLabel = new Label("Lancio " + (i + 1));
            throwLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label timeLabel;
            Label pointsLabel;

            if (i < throwsList.size()) {
                Throws singleThrow = throwsList.get(i);
                timeLabel = new Label("Tempo: " );
                pointsLabel = new Label("Punti: " );
            } else {
                timeLabel = new Label("Tempo: -");
                pointsLabel = new Label("Punti: -");
            }

            card.getChildren().addAll(throwLabel, timeLabel, pointsLabel);
            throwsAnchor.getChildren().add(card);
        }
    }

    @FXML
    private void handleBackToSavedPilotsView() {
        Main.openShowThrowsView(pilotReference, raceReference,championshipReference, throwsList);
    }
}
