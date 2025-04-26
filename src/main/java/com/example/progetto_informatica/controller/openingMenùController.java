package com.example.progetto_informatica.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.Year;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;

public class openingMenùController implements Initializable {
    @FXML
    private VBox tournamentsList;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization if needed
    }

    @FXML
    private void handleAddTournament() {
        // Create dialog to get tournament details
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Campionato");
        dialog.setHeaderText("Inserisci i dettagli del nuovo campionato");
        dialog.setContentText("Nome Campionato:");

        Optional<String> nomeCampionato = dialog.showAndWait();

        if(!nomeCampionato.isEmpty()) {
            addTournamentCard(String.valueOf(Year.now().getValue()), nomeCampionato.get(), "0", "In Corso");
        }
    }

    private void addTournamentCard(String year, String title, String participants, String status) {
        VBox tournamentContainer = new VBox();
        tournamentContainer.getStyleClass().add("tournament-container");
        tournamentContainer.setSpacing(5);

        Label yearLabel = new Label(year);
        yearLabel.getStyleClass().add("year-label");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("tournament-title");

        Label participantsLabel = new Label(participants + " partecipanti");
        participantsLabel.getStyleClass().add("participants-label");

        Label statusLabel = new Label(status);
        statusLabel.getStyleClass().add("status-label");
        statusLabel.getStyleClass().add(status.equals("In Corso") ? "in-progress" : "completed");

        tournamentContainer.getChildren().addAll(yearLabel, titleLabel, participantsLabel, statusLabel);
        tournamentsList.getChildren().add(0, tournamentContainer); // Add at the beginning

        // Add separator if not the first tournament
        if (tournamentsList.getChildren().size() > 1) {
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            tournamentsList.getChildren().add(1, separator); // Add after the new tournament
        }
    }

    @FXML
    private void handleEditTournament() {
        // Implementation for editing a tournament
        System.out.println("Edit functionality to be implemented");
    }

    @FXML
    private void handleDeleteTournament() {
        // Implementation for deleting a tournament
        System.out.println("Delete functionality to be implemented");
    }
}