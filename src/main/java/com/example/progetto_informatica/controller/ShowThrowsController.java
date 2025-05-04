package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    private Championship championshipReference; // Riferimento al campionato
    private Race raceReference; // Riferimento alla gara
    private int pilotIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Metodo chiamato durante l'inizializzazione del controller (non utilizzato qui, ma può essere utile per altre operazioni)
    }

    // Metodo per inizializzare i dati necessari: pilota, campionato, gara e lista dei lanci
    public void initThrowsData(Championship championshipReference, Race raceReference, int i) {
        this.championshipReference = championshipReference;
        this.raceReference = raceReference;
        this.pilotIndex = i;

        // Chiamata per aggiungere le "cards" che mostrano i lanci
        addThrowsCards();
    }

    // Metodo per aggiungere le "cards" dei lanci al contenitore
    private void addThrowsCards() {
        throwsAnchor.getChildren().clear(); // Pulisce i contenuti esistenti nel contenitore


        // Ciclo per aggiungere le cards per i lanci
        for (int i = 0; i < Throws.nThrows; i++) {
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
            if(i < raceReference.getThrows().get(pilotIndex).getThrowsDone())
            {
                timeLabel = new Label("Tempo: " + raceReference.getThrows().get(pilotIndex).getTimes().get(i)); // Mostra il tempo del lancio
                pointsLabel = new Label("Punti: " + raceReference.getThrows().get(pilotIndex).getPoints().get(i)); // Mostra i punti del lancio
            }else
            {
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
        Main.openSpecificRaceMenù(raceReference,championshipReference);
    }

    @FXML
    private void handleExecuteThrow()
    {
        if(raceReference.getThrows().get(pilotIndex).getThrowsDone() < Throws.getMaxThrows())
        {
        Main.openStopWatchView(raceReference, pilotIndex, championshipReference);
        }else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore lancio");
            alert.setHeaderText("Impossibile avviare il lancio");
            alert.setContentText("Il pilota ha già effettuato il numero massimo di lanci!");
            alert.showAndWait();
        }

    }
}
