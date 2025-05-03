package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.Pilot;
import com.example.progetto_informatica.model.PilotPoint;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerViewController implements Initializable {

    @FXML
    private Label minutesLabel, secondsLabel, millisecondsLabel;

    @FXML
    private Button startButton, stopButton;

    private long startTime;
    private AnimationTimer timer;

    private boolean running = false;
    private Pilot pilotReference;
    private PilotPoint pilotPointReference;

    private static final int MAX_TIME_SECONDS = 240; // 4 minuti

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stopButton.setDisable(true);
    }

    public void initPilotData(Pilot pilot, PilotPoint pilotPoint) {
        this.pilotReference = pilot;
        this.pilotPointReference = pilotPoint;
    }

    @FXML
    private void handleStart() {
        if (running) return;

        running = true;
        startTime = System.nanoTime();
        stopButton.setDisable(false);
        startButton.setDisable(true);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedNano = now - startTime;
                long totalMillis = elapsedNano / 1_000_000;

                int minutes = (int) (totalMillis / 60000);
                int seconds = (int) ((totalMillis % 60000) / 1000);
                int centesimi = (int) ((totalMillis % 1000) / 10);

                minutesLabel.setText(String.format("%02d", minutes));
                secondsLabel.setText(String.format("%02d", seconds));
                millisecondsLabel.setText(String.format("%02d", centesimi));
            }
        };
        timer.start();
    }

    @FXML
    private void handleStop() {
        if (!running) return;

        running = false;
        timer.stop();
        stopButton.setDisable(true);
        startButton.setDisable(false);

        long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;
        long totalSeconds = elapsedMillis / 1000;
        int centesimi = (int) ((elapsedMillis % 1000) / 10);

        // Arrotondamento secondi
        if (centesimi >= 50) {
            totalSeconds += 1;
        }

        int score;
        if (totalSeconds <= MAX_TIME_SECONDS) {
            score = (int) totalSeconds; // 1 punto per ogni secondo
        } else {
            int penalty = (int) (totalSeconds - MAX_TIME_SECONDS) * 2;
            score = MAX_TIME_SECONDS - penalty;
        }

        if (score < 0) score = 0;

        // Aggiorna l'oggetto PilotPoint
        pilotPointReference.setPoints(score);
        System.out.println("Punteggio aggiornato per " + pilotReference + ": " + score + " punti");
    }
}
