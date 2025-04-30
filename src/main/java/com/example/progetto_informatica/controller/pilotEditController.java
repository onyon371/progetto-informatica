package com.example.progetto_informatica.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.Year;
import java.util.Optional;

public class pilotEditController {

    @FXML
    private VBox pilotContainer;

    @FXML
    void addPilot(ActionEvent event) {
        // Create dialog to get pilot details
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Pilota");
        dialog.setHeaderText("Inserisci i dati del pilota che vuoi aggiungere!");
        dialog.setContentText("Nome Pilota:");
        dialog.setContentText("Cognome Pilota:");

        Optional<String> pilot = dialog.showAndWait();

        if(!pilot.isEmpty()) {
            handlePilotCard("","");

        }

    }

    @FXML
    void removePilot(ActionEvent event) {

    }
    @FXML
    void handlePilotCard(String name, String surname){
        VBox tournamentContainer = new VBox();
        tournamentContainer.getStyleClass().add("tournament-container");
        tournamentContainer.setSpacing(5);

        // Add separator if not the first pilot
        if (pilotContainer.getChildren().size() > 1) {
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            pilotContainer.getChildren().add(1, separator); // Add after the new pilot
        }
    }

}

