package com.example.progetto_informatica;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class openingSceneController {
        @FXML
        private HBox championshipsContainer;

        // Metodo per aggiungere un nuovo campionato
        public void addChampionship(String championshipName) {
            // Crea il quadrato del campionato
            VBox championshipBox = new VBox();
            championshipBox.getStyleClass().add("championship-square");
            championshipBox.setAlignment(Pos.CENTER);

            // Aggiungi il nome del campionato
            Label nameLabel = new Label(championshipName);
            nameLabel.getStyleClass().add("championship-title");

            // Aggiungi eventuali altri elementi (es. icona, numero partecipanti)
            Label detailsLabel = new Label("0 partecipanti");
            detailsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

            championshipBox.getChildren().addAll(nameLabel, detailsLabel);

            // Aggiungi il quadrato al container
            championshipsContainer.getChildren().add(championshipBox);

            // Aggiungi gestore di eventi per il click
            championshipBox.setOnMouseClicked(event -> {
                openChampionshipDetails(championshipName);
            });
        }

        private void openChampionshipDetails(String championshipName) {
            // Logica per aprire i dettagli del campionato
            System.out.println("Aperto campionato: " + championshipName);
        }
        @FXML
        private void handleAddChampionship() {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nuovo Campionato");
            dialog.setHeaderText("Crea un nuovo campionato");
            dialog.setContentText("Nome del campionato:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                addChampionship(name);
            }
        });
    }
}