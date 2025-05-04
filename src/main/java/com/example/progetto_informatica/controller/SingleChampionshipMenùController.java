package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleChampionshipMenùController implements Initializable {
    @FXML private VBox pilotsRankingContainer; // Contenitore per la classifica dei piloti
    @FXML private VBox racesContainer; // Contenitore per la visualizzazione delle gare
    @FXML private VBox pilotsParticipantsContainer; // Contenitore per visualizzare i partecipanti

    private Championship championshipReference; // Riferimento al campionato corrente

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championshipReference = null; // Inizializzazione con campionato nullo
    }

    // Metodo che inizializza il campionato e carica tutte le informazioni (partecipanti, classifica, gare)
    public void initChampionhip(Championship championshipReference) {
        this.championshipReference = championshipReference;

        try {
            setParticipantsContainer(); // Aggiungi i partecipanti alla UI
            setPilotsRankingContainer(); // Aggiungi la classifica piloti alla UI
            addRacesCard(); // Aggiungi le gare alla UI
        } catch (Exception e) {
            e.printStackTrace(); // Gestione errore durante il caricamento
        }
    }

    // Metodo che aggiorna la lista dei partecipanti nel contenitore
    public void setParticipantsContainer() {
        pilotsParticipantsContainer.getChildren().clear(); // Pulisce i partecipanti già visualizzati

        AtomicInteger counter = new AtomicInteger(0); // Variabile per numerare i partecipanti

        if(championshipReference.getPilots().isEmpty()) { // Se non ci sono partecipanti, mostra il messaggio
            Label pilotLabel = new Label("Nessun Partecipante!"); // Etichetta per nessun partecipante
            pilotLabel.getStyleClass().add("winner-name"); // Aggiungi stile al testo
            pilotsParticipantsContainer.getChildren().add(pilotLabel); // Aggiungi l'etichetta alla UI
            return;
        }

        // Per ogni pilota nel campionato, aggiungi un'etichetta con il nome
        championshipReference.getPilots().forEach(pilot -> {
            Label pilotLabel = new Label(counter.incrementAndGet() + ". " + pilot.toString());
            pilotLabel.getStyleClass().add("winner-name"); // Applica stile
            pilotsParticipantsContainer.getChildren().add(pilotLabel); // Aggiungi alla UI
        });
    }

    // Metodo che aggiorna la classifica dei piloti nel contenitore
    private void setPilotsRankingContainer() {
        pilotsRankingContainer.getChildren().clear(); // Pulisce la classifica esistente
        ArrayList<PilotPoint> bestPilots = championshipReference.getBestPilotsAndPoints(); // Ottiene i piloti e i punti

        AtomicInteger counter = new AtomicInteger(0); // Variabile per numerare i piloti nella classifica

        if(bestPilots.isEmpty()) { // Se non ci sono piloti nella classifica, mostra il messaggio
            Label pilotLabel = new Label("Classifica Vuota!"); // Etichetta per classifica vuota
            pilotLabel.getStyleClass().add("winner-name"); // Aggiungi stile al testo
            pilotsRankingContainer.getChildren().add(pilotLabel); // Aggiungi l'etichetta alla UI
            return;
        }

        // Per ogni pilota nella classifica, aggiungi il suo nome e punti
        bestPilots.forEach(pilot -> {
            Label pilotLabel = new Label(counter.incrementAndGet() + ". " + pilot.getP().toString() + " Punti: " + pilot.getPoints());
            pilotLabel.getStyleClass().add("winner-name"); // Applica stile
            pilotsRankingContainer.getChildren().add(pilotLabel); // Aggiungi alla UI
        });
    }

    // Metodo che rimuove una gara dal campionato e aggiorna la UI
    private void removeRace(Race raceReference) {
        championshipReference.getRaces().remove(raceReference); // Rimuove la gara dalla lista
        addRacesCard(); // Ricarica le gare nella UI
        setPilotsRankingContainer();
    }

    // Metodo che aggiunge una card per ogni gara nel contenitore delle gare
    public void addRacesCard() {
        racesContainer.getChildren().clear(); // Pulisce le gare esistenti

        AtomicInteger counter = new AtomicInteger(0); // Variabile per numerare le gare

        if (championshipReference.getRaces().isEmpty()) { // Se non ci sono gare, non fare nulla
            // Eventualmente mostra un messaggio per indicare che non ci sono gare
        }

        // Per ogni gara nel campionato, crea una card con i dettagli della gara
        championshipReference.getRaces().forEach(race -> {
            BorderPane racePane = new BorderPane(); // Contenitore principale per la gara
            racePane.getStyleClass().add("race-container"); // Applica stile alla card della gara

            VBox raceBox = new VBox(); // Contenitore per i dettagli della gara
            raceBox.setSpacing(10);

            // Box per il titolo e la data della gara
            HBox headerBox = new HBox(10);
            headerBox.getStyleClass().add("race-header");

            Label titleLabel = new Label(race.getName()); // Nome della gara
            titleLabel.getStyleClass().add("race-title");

            Label dateLabel = new Label(race.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Data della gara
            dateLabel.getStyleClass().add("race-date");

            headerBox.getChildren().addAll(titleLabel, dateLabel); // Aggiungi nome e data

            // Box per i dettagli della gara
            HBox detailsBox = new HBox(20);
            detailsBox.getStyleClass().add("race-details");

            Label participantsLabel = new Label("Partecipanti: " + race.getPilots().size()); // Numero di partecipanti
            participantsLabel.getStyleClass().add("race-info");

            Label winnerLabel = null;
            try {
                winnerLabel = new Label("Primo Classificato: " + race.getBestPilotsAndPoints().getFirst().getP().toString()); // Vincitore
                winnerLabel.getStyleClass().addAll("race-info", "winner-info");
            } catch (Exception e) {
                System.err.println("Tentativo di aggiungere vincitore ma non presente");
            }

            Label statusLabel = new Label(race.isRaceOpen() ? "In corso" : "Terminata"); // Stato della gara
            statusLabel.getStyleClass().add("race-status");
            if (!race.isRaceOpen()) {
                statusLabel.getStyleClass().add("completed"); // Applica lo stile se la gara è terminata
            } else {
                statusLabel.getStyleClass().add("in-progress"); // Applica lo stile se la gara è in corso
            }

            detailsBox.getChildren().add(participantsLabel); // Aggiungi numero partecipanti
            if (winnerLabel != null) {
                detailsBox.getChildren().add(winnerLabel); // Aggiungi vincitore, se presente
            }
            detailsBox.getChildren().add(statusLabel); // Aggiungi stato

            raceBox.getChildren().addAll(headerBox, detailsBox); // Aggiungi tutto nel contenitore della gara

            // Menu a tre puntini per modificare o eliminare la gara
            //MenuItem editItem = new MenuItem("Modifica");
            MenuItem deleteItem = new MenuItem("Elimina");
            /*
            editItem.setOnAction(e -> {
                System.out.println("Modifica gara: " + race.getName()); // Placeholder per l'azione di modifica
            });*/

            deleteItem.setOnAction(e -> {
                removeRace(race); // Rimuovi la gara quando cliccato
            });

            MenuButton optionsButton = new MenuButton("⋮", null, deleteItem); // Crea il menu a tre puntini
            optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black;"); // Stile del pulsante
            optionsButton.getStyleClass().add("three-dots"); // Applica la classe CSS

            optionsButton.setOnMouseClicked(e -> e.consume()); // Blocca la propagazione del click sul menu

            racePane.setCenter(raceBox); // Imposta il contenuto della gara
            racePane.setRight(optionsButton); // Imposta il pulsante delle opzioni a destra
            racePane.setStyle("-fx-padding: 10;"); // Aggiungi padding alla card

            racePane.setOnMouseClicked(event -> {
                Main.openSpecificRaceMenù(race, championshipReference); // Apre il menu della gara quando cliccato
            });

            racesContainer.getChildren().add(racePane); // Aggiungi la card della gara alla UI
        });
    }

    // Aggiunge una nuova gara al campionato
    @FXML
    private void handleAddNewRace() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi gara");
        dialog.setHeaderText("Inserisci i dettagli della nuova gara");
        dialog.setContentText("Nome gara:");

        Optional<String> raceName = dialog.showAndWait(); // Chiede il nome della gara

        if(!raceName.isEmpty()) { // Se il nome è valido, aggiungi la gara
            championshipReference.addRace(raceName.get());
            addRacesCard(); // Ricarica la lista delle gare
        }
    }

    // Torna al menu del campionato principale
    @FXML
    private void handleBackToChampionshipMenù() {
        Main.openChampionshipsMenù(); // Apre il menu delle informazioni sul campionato
    }

    // Aggiunge un nuovo pilota al campionato
    @FXML
    private void handleAddNewPilot() {
        Main.openSavedPilotsView(championshipReference, this); // Apre la vista dei piloti salvati
    }
}
