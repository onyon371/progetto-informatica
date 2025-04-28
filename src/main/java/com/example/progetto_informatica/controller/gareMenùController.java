package com.example.progetto_informatica.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class gareMenùController implements Initializable {
    @FXML private VBox winnersContainer;
    @FXML private VBox racesContainer;

    private ObservableList<String> winners = FXCollections.observableArrayList();
    private ObservableList<Race> races = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize with sample data
        winners.addAll("Nicolò Cipollini", "Nicolò Cipollini");
        refreshWinners();

        races.addAll(
                new Race("GARA NUMERO 1", "25/04/2025", 25, "Nicolò Cipollini", "Terminata"),
                new Race("GARA NUMERO 2", "", 0, "", "")
        );
        refreshRaces();
    }

    private void refreshWinners() {
        winnersContainer.getChildren().clear();
        winners.forEach(winner -> {
            Label winnerLabel = new Label(winner);
            winnerLabel.getStyleClass().add("winner-name");
            winnersContainer.getChildren().add(winnerLabel);
        });
    }

    private void refreshRaces() {
        racesContainer.getChildren().clear();
        races.forEach(race -> {
            VBox raceBox = new VBox();
            raceBox.getStyleClass().add("race-container");

            HBox headerBox = new HBox(10);
            headerBox.getStyleClass().add("race-header");

            Label titleLabel = new Label(race.getTitle());
            titleLabel.getStyleClass().add("race-title");

            Label dateLabel = new Label(race.getDate());
            dateLabel.getStyleClass().add("race-date");

            headerBox.getChildren().addAll(titleLabel, dateLabel);

            HBox detailsBox = new HBox(20);
            detailsBox.getStyleClass().add("race-details");

            Label participantsLabel = new Label(race.getParticipants() > 0 ?
                    "Partecipanti: " + race.getParticipants() : "");
            participantsLabel.getStyleClass().add("race-info");

            Label winnerLabel = new Label(race.getWinner().isEmpty() ?
                    "" : "Vincitore: " + race.getWinner());
            winnerLabel.getStyleClass().addAll("race-info", "winner-info");

            Label statusLabel = new Label(race.getStatus());
            statusLabel.getStyleClass().add("race-status");

            if (race.getStatus().equalsIgnoreCase("Terminata")) {
                statusLabel.getStyleClass().add("completed");
            } else if (race.getStatus().equalsIgnoreCase("In corso")) {
                statusLabel.getStyleClass().add("in-progress");
            } else if (!race.getStatus().isEmpty()) {
                statusLabel.getStyleClass().add("upcoming");
            }

            detailsBox.getChildren().addAll(participantsLabel, winnerLabel, statusLabel);
            raceBox.getChildren().addAll(headerBox, detailsBox);
            racesContainer.getChildren().add(raceBox);
        });
    }

    @FXML
    private void handleAddRace() {
        // Create a dialog to add new race
        Dialog<Race> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi Nuova Gara");

        // Set up dialog buttons
        ButtonType addButton = new ButtonType("Aggiungi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        TextField dateField = new TextField();
        Spinner<Integer> participantsField = new Spinner<>(1, 100, 1);
        TextField winnerField = new TextField();
        ComboBox<String> statusField = new ComboBox<>();
        statusField.getItems().addAll("Terminata", "In corso", "Pianificata");

        grid.add(new Label("Titolo:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Data:"), 0, 1);
        grid.add(dateField, 1, 1);
        grid.add(new Label("Partecipanti:"), 0, 2);
        grid.add(participantsField, 1, 2);
        grid.add(new Label("Vincitore:"), 0, 3);
        grid.add(winnerField, 1, 3);
        grid.add(new Label("Stato:"), 0, 4);
        grid.add(statusField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Convert result to Race object
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new Race(
                        titleField.getText(),
                        dateField.getText(),
                        participantsField.getValue(),
                        winnerField.getText(),
                        statusField.getValue()
                );
            }
            return null;
        });

        Optional<Race> result = dialog.showAndWait();
        result.ifPresent(race -> {
            races.add(0, race);
            if (!race.getWinner().isEmpty()) {
                winners.add(0, race.getWinner());
                if (winners.size() > 5) {
                    winners.remove(winners.size() - 1);
                }
                refreshWinners();
            }
            refreshRaces();
        });
    }

    @FXML
    private void handleEditRace() {
        // Implementation for editing a race
        // Would need selection logic
    }

    @FXML
    private void handleDeleteRace() {
        // Implementation for deleting a race
        // Would need selection logic
    }

    // Race data model class
    public static class Race {
        private String title;
        private String date;
        private int participants;
        private String winner;
        private String status;

        public Race(String title, String date, int participants, String winner, String status) {
            this.title = title;
            this.date = date;
            this.participants = participants;
            this.winner = winner;
            this.status = status;
        }

        // Getters and setters
        public String getTitle() { return title; }
        public String getDate() { return date; }
        public int getParticipants() { return participants; }
        public String getWinner() { return winner; }
        public String getStatus() { return status; }
    }
}