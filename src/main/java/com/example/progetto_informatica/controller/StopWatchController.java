package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.Championship;
import com.example.progetto_informatica.model.Main;
import com.example.progetto_informatica.model.Race;
import com.example.progetto_informatica.model.Throws;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class StopWatchController implements Initializable {

    @FXML
    private Label minutesLabel, secondsLabel, millisecondsLabel; // Etichette per visualizzare il tempo (minuti, secondi, millisecondi)

    @FXML
    private Button startButton, stopButton; // Pulsanti per avviare e fermare il timer

    private long startTime; // Variabile per memorizzare l'orario di inizio
    private AnimationTimer timer; // Timer che aggiorna il tempo ogni fotogramma

    private boolean running = false; // Flag per sapere se il timer è in esecuzione

    private Race raceReference;
    private int pilotIndex;

    private Championship championshipReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stopButton.setDisable(true); // Disabilita il pulsante di stop all'inizio
    }

    // Metodo per inizializzare i dati del pilota e del suo punto
    public void init(Race raceReference, int pilotIndex, Championship championshipReference) {
        this.raceReference = raceReference;
        this.pilotIndex = pilotIndex;
        this.championshipReference = championshipReference;
    }

    // Metodo per gestire l'inizio del timer
    @FXML
    private void handleStart() {
        if (running) return; // Se il timer è già in esecuzione, non fare nulla

        running = true; // Imposta il flag a true per indicare che il timer è avviato
        startTime = System.nanoTime(); // Registra il tempo di inizio in nanosecondi
        stopButton.setDisable(false); // Abilita il pulsante di stop
        startButton.setDisable(true); // Disabilita il pulsante di start per evitare di premere più volte

        // Crea e avvia il timer che aggiornerà i valori di tempo ogni fotogramma
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedNano = now - startTime; // Calcola il tempo trascorso in nanosecondi
                long totalMillis = elapsedNano / 1_000_000; // Converti il tempo trascorso in millisecondi

                // Calcola minuti, secondi e centesimi (decimi di secondo)
                int minutes = (int) (totalMillis / 60000);
                int seconds = (int) ((totalMillis % 60000) / 1000);
                int centesimi = (int) ((totalMillis % 1000) / 10);

                // Imposta il tempo visualizzato nelle etichette
                minutesLabel.setText(String.format("%02d", minutes));
                secondsLabel.setText(String.format("%02d", seconds));
                millisecondsLabel.setText(String.format("%02d", centesimi));
            }
        };
        timer.start(); // Avvia il timer
    }

    // Metodo per gestire l'arresto del timer
    @FXML
    private void handleStop() {
        if (!running) return; // Se il timer non è in esecuzione, non fare nulla

        running = false; // Imposta il flag a false per indicare che il timer è fermo
        timer.stop(); // Ferma il timer
        stopButton.setDisable(true); // Disabilita il pulsante di stop
        startButton.setDisable(false); // Abilita il pulsante di start per poter ripartire

        // Calcola il tempo trascorso in millisecondi
        long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;
        long totalSeconds = elapsedMillis / 1000;
        int centesimi = (int) ((elapsedMillis % 1000) / 10);

        // Arrotonda il tempo in secondi, se i centesimi sono superiori o uguali a 50, aggiungi un secondo
        if (centesimi >= 50) {
            totalSeconds += 1;
        }

        int minutes = (int)totalSeconds / 60;
        totalSeconds = totalSeconds-minutes*60;
        raceReference.getThrows().get(pilotIndex).addNewThrow(LocalTime.of(0,minutes,(int)totalSeconds,0));
        Main.openShowThrowsView(pilotIndex, raceReference, championshipReference);
        Main.tryAndCloseSavedOtherView();
    }
}
