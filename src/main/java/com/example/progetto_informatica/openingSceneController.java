package com.example.progetto_informatica;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class openingSceneController{

         private Gara gara = new Gara();
        @FXML
        private MenuItem item_aggiungiPartecipante;

        @FXML
        private MenuItem item_rimuoviPartecipante;

        @FXML
        void initialize(){
            item_aggiungiPartecipante.setOnAction(event -> addPilota());
            item_rimuoviPartecipante.setOnAction(event -> removePilota());
        }
        // Metodo che richiama il metodo aggiungi pilota
        public void addPilota(){
            // Finestra per il nome
            TextInputDialog dialogNome = new TextInputDialog();
            dialogNome.setTitle("Nuovo Pilota");
            dialogNome.setHeaderText("Aggiungi un nuovo pilota");
            dialogNome.setContentText("Nome:");
            Optional<String> resultNome = dialogNome.showAndWait();

            // Finestra per il cognome
            TextInputDialog dialogCognome = new TextInputDialog();
            dialogCognome.setTitle("Nuovo Pilota");
            dialogCognome.setHeaderText("Aggiungi un nuovo pilota");
            dialogCognome.setContentText("Cognome:");
            Optional<String> resultCognome = dialogCognome.showAndWait();

            // Se entrambi sono presenti e non vuoti, aggiungiPilota()
            if (resultNome.isPresent() && resultCognome.isPresent()) {
                String nome = resultNome.get().trim();
                String cognome = resultCognome.get().trim();
                if (!nome.isEmpty() && !cognome.isEmpty()) {
                    Pilota pilota = new Pilota(nome,cognome);
                    gara.aggiungiPilota(pilota);
                }
            }
        }
    // Metodo che richiama il metodo rimuoviPilota()
    public void removePilota(){
        // Finestra per il nome
        TextInputDialog dialogNome = new TextInputDialog();
        dialogNome.setTitle("Elimina Pilota");
        dialogNome.setHeaderText("Rimuovi un pilota");
        dialogNome.setContentText("Nome:");
        Optional<String> resultNome = dialogNome.showAndWait();

        // Finestra per il cognome
        TextInputDialog dialogCognome = new TextInputDialog();
        dialogCognome.setTitle("Elimina Pilota");
        dialogCognome.setHeaderText("Rimuovi un pilota");
        dialogCognome.setContentText("Cognome:");
        Optional<String> resultCognome = dialogCognome.showAndWait();

        // Se entrambi sono presenti e non vuoti, rimuovi il pilota
        if (resultNome.isPresent() && resultCognome.isPresent()) {
            String nome = resultNome.get().trim();
            String cognome = resultCognome.get().trim();
            if (!nome.isEmpty() && !cognome.isEmpty()) {
                Pilota pilota = new Pilota(nome,cognome);
                gara.rimuoviPilota(pilota);
            }
        }
    }


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