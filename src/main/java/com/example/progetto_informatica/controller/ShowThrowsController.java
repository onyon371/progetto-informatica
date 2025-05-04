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
    private VBox throwsAnchor; // Contenitore principale per i lanci

    private Pilot pilotReference; // Riferimento al pilota
    private Championship championshipReference; // Riferimento al campionato
    private Race raceReference; // Riferimento alla gara
    private List<Throws> throwsList; // Lista dei lanci effettuati

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Metodo chiamato durante l'inizializzazione del controller (non utilizzato qui, ma può essere utile per altre operazioni)
    }

    // Metodo per inizializzare i dati necessari: pilota, campionato, gara e lista dei lanci
    public void initThrowsData(Pilot pilotReference, Championship championshipReference, Race raceReference, List<Throws> throwsList) {
        this.pilotReference = pilotReference;
        this.championshipReference = championshipReference;
        this.raceReference = raceReference;
        this.throwsList = throwsList;

        // Chiamata per aggiungere le "cards" che mostrano i lanci
        addThrowsCards();
    }

    // Metodo per aggiungere le "cards" dei lanci al contenitore
    private void addThrowsCards() {
        throwsAnchor.getChildren().clear(); // Pulisce i contenuti esistenti nel contenitore

        // Ciclo per aggiungere le cards per i 4 lanci
        for (int i = 0; i < 4; i++) {
            // Crea un HBox per ogni card
            HBox card = new HBox(20);
            card.setPadding(new Insets(15)); // Distanza interna nella card
            card.setStyle("-fx-background-color: #d9eaf7; -fx-background-radius: 10; -fx-border-radius: 10;"); // Stile della card

            // Crea un'etichetta per il numero del lancio
            Label throwLabel = new Label("Lancio " + (i + 1));
            throwLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Stile per il numero del lancio

            // Variabili per tempo e punti
            Label timeLabel;
            Label pointsLabel;

            // Controlla se ci sono dati per il lancio corrente (se esiste un lancio nella lista)
            if (i < throwsList.size()) {
                Throws singleThrow = throwsList.get(i); // Ottieni il lancio specifico dalla lista
                timeLabel = new Label("Tempo: "); // Mostra il tempo del lancio
                pointsLabel = new Label("Punti: "); // Mostra i punti del lancio
            } else {
                // Se non ci sono più lanci, mostra un valore di default ("-")
                timeLabel = new Label("Tempo: -");
                pointsLabel = new Label("Punti: -");
            }

            // Aggiungi le etichette alla card
            card.getChildren().addAll(throwLabel, timeLabel, pointsLabel);
            // Aggiungi la card al contenitore
            throwsAnchor.getChildren().add(card);
        }
    }

    // Metodo per gestire il ritorno alla vista precedente
    @FXML
    private void handleBackToSavedPilotsView() {
        // Torna alla vista dei lanci precedenti
        Main.openShowThrowsView(pilotReference, raceReference, championshipReference, throwsList);
    }
}
