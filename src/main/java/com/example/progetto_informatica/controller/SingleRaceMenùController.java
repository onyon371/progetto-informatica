package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleRaceMenùController implements Initializable {

    @FXML
    private VBox pilotsAnchor; // Contenitore per le card dei piloti
    @FXML
    private ImageView searchIcon; // Immagine associata alla gara (non utilizzata nel codice fornito)
    @FXML
    private TextField searchField; // Campo di ricerca per cercare i piloti

    private Championship championshipReference; // Riferimento al campionato
    private Race raceReference; // Riferimento alla gara corrente

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchField.setPromptText("Cerca pilota..."); // Imposta il testo di suggerimento per il campo di ricerca
        // Carica l’immagine della lente
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/progetto_informatica/lente.png")));
        searchIcon.setImage(img);
        // Listener per la ricerca: ogni volta che l'utente cambia il testo nella ricerca, viene eseguito il metodo renderPilotCards
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (raceReference != null) {
                renderPilotCards(newVal); // Mostra o filtra le card dei piloti in base al testo inserito
            }
        });

    }

    // Metodo che inizializza il riferimento alla gara e al campionato, e visualizza i piloti
    public void initRaceReference(Race raceReference, Championship championshipReference) {
        this.raceReference = raceReference;
        this.championshipReference = championshipReference;
        renderPilotCards(""); // Mostra tutti i piloti all'avvio
    }

    // Metodo che rende le card dei piloti filtrando in base al testo di ricerca
    private void renderPilotCards(String filter) {
        pilotsAnchor.getChildren().clear(); // Pulisce le card dei piloti esistenti

        Set<String> nomiVisualizzati = new HashSet<>(); // Set per evitare duplicati nei nomi dei piloti
        AtomicInteger counter = new AtomicInteger(0); // Conta per numerare le posizioni dei piloti

        // Cicla sui piloti della gara e crea una card per ogni pilota che soddisfa il filtro di ricerca
        for (int i = 0; i < raceReference.getPilots().size(); i++) {
            String pilotName = raceReference.getPilots().get(i).toString().toLowerCase();

            // Verifica se il nome del pilota contiene il filtro di ricerca e non è stato già visualizzato
            if ((filter == null || filter.isEmpty() || pilotName.contains(filter.toLowerCase()))
                    && !nomiVisualizzati.contains(pilotName)) {

                nomiVisualizzati.add(pilotName); // Aggiungi il nome del pilota al set per evitare duplicati

                HBox card = createPilotCard(i, counter.incrementAndGet()); // Crea la card del pilota
                pilotsAnchor.getChildren().add(card); // Aggiungi la card al contenitore dei piloti
            }
        }
    }

    // Metodo che crea una card per un pilota specifico
    private HBox createPilotCard(int i, int rank) {
        HBox card = new HBox(20); // Contenitore orizzontale per la card del pilota
        card.setPadding(new Insets(15)); // Imposta il padding per la card
        card.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10; -fx-border-radius: 10;"); // Stile della card

        Label rankLabel = new Label(rank + "."); // Etichetta per la posizione del pilota
        rankLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Stile per la posizione

        VBox infoBox = new VBox(5); // Contenitore verticale per le informazioni del pilota
        Label nameLabel = new Label(raceReference.getPilots().get(i).toString()); // Nome del pilota
        HBox stats = new HBox(15); // Contenitore orizzontale per le statistiche del pilota

        // Aggiungi le statistiche del pilota: lanci effettuati, miglior tempo, punti totali
        stats.getChildren().addAll(
                new Label("Lanci Effettuati: " + raceReference.getThrowsCompletedOfSpecificPilot(i) + "/" + raceReference.getMaxThrows()),
                new Label(raceReference.getBestTimeOfSpecificPilot(i) != LocalTime.of(0, 0, 0, 0)
                        ? "Miglior Tempo: " + raceReference.getBestTimeOfSpecificPilot(i)
                        : "Miglior Tempo: Nessun Lancio Registrato!"),
                new Label("Punti Totali: " + raceReference.getPointsOfSpecificPilot(i) + "/" + raceReference.getMaxPoints())
        );

        infoBox.getChildren().addAll(nameLabel, stats); // Aggiungi nome e statistiche alla card
        card.getChildren().addAll(rankLabel, infoBox); // Aggiungi la posizione e le informazioni alla card

        DropShadow shadow = new DropShadow(10, 5, 5, Color.DARKGRAY); // Effetto ombra per hover
        // Gestione degli eventi per il mouse
        card.setOnMouseClicked(e -> {
            Main.openShowThrowsView(i, raceReference, championshipReference); // Apre la vista per i lanci del pilota
            System.out.println(i);
        });

        // Effetto hover sulla card: cambia colore di sfondo e aggiungi ombra quando il mouse passa sopra
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #f8a400; -fx-background-radius: 10;");
            card.setEffect(shadow);
        });

        // Rimuove l'effetto hover quando il mouse esce dalla card
        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10;");
            card.setEffect(null);
        });

        return card; // Restituisce la card creata
    }

    // Metodo per tornare al menu del campionato principale
    @FXML
    private void handleBackToChampionshipMenù() {
        Main.openSingleChampionshipMenù(championshipReference); // Apre la vista del campionato
    }
}
