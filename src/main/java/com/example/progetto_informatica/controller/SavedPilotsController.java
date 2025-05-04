package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class SavedPilotsController implements Initializable {

    @FXML
    private VBox pilotsAnchor; // Container per la visualizzazione dei piloti salvati

    private SingleChampionshipMenùController singleChampionshipMenùControllerReference; // Riferimento al controller del campionato
    private Championship championshipReference; // Campionato attualmente in uso

    private static ArrayList<Pilot> savedPilots; // Lista di tutti i piloti salvati

    private static final String SAVEPATH = "pilots.bin"; // Percorso file per salvataggio piloti

    // Metodo chiamato all'inizializzazione del controller
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championshipReference = null;
    }

    // Inizializza il controller con i riferimenti necessari e carica i piloti
    public void initChampionship(Championship championshipReference, SingleChampionshipMenùController singleChampionshipMenùControllerReference) {
        this.championshipReference = championshipReference;
        this.singleChampionshipMenùControllerReference = singleChampionshipMenùControllerReference;

        try {
            getSavedPilots();  // Carica piloti dal file
            addPilotsCard();   // Mostra i piloti nella GUI
        } catch (Exception e) {
            e.printStackTrace();
            savedPilots = new ArrayList<>();
            savePilots(); // Crea file se non esiste
        }
    }

    // Gestisce l'aggiunta di un nuovo pilota tramite input utente
    @FXML
    void handleCreateNewPilot() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Pilota");
        dialog.setHeaderText("Inserisci i dati del pilota che vuoi aggiungere!");
        dialog.setContentText("Nome Pilota:");

        Optional<String> pilotName = dialog.showAndWait();

        if (pilotName.isPresent() && !pilotName.get().isBlank()) {
            dialog = new TextInputDialog();
            dialog.setTitle("Aggiungi Pilota");
            dialog.setHeaderText("Inserisci i dati del pilota che vuoi aggiungere!");
            dialog.setContentText("Cognome Pilota:");

            Optional<String> pilotSurname = dialog.showAndWait();

            if (pilotSurname.isPresent() && !pilotSurname.get().isBlank()) {
                Pilot newPilot = new Pilot(pilotName.get(), pilotSurname.get());

                if (!savedPilots.contains(newPilot)) {
                    try {
                        savedPilots.add(newPilot);
                        addPilotsCard(); // Aggiorna la lista visuale
                        savePilots();    // Salva nel file
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showError("Pilota già esistente!");
                }
            }
        }
    }

    // Stub per future funzioni di modifica piloti
    @FXML
    void handleEditPilots() {
        // TODO: Implementare modifica piloti
    }

    // Stub per future funzioni di cancellazione piloti
    @FXML
    void handleDeletePilots(Pilot p) {
        savedPilots.remove(p);
        savePilots();
        addPilotsCard();
    }

    // Mostra nella GUI tutti i piloti salvati
    private void addPilotsCard() {
        pilotsAnchor.getChildren().clear();
        AtomicInteger counter = new AtomicInteger(0);

        savedPilots.forEach(pilot -> {
            HBox pilotBox = new HBox();

            Label pilotNumber = new Label(Integer.toString(counter.incrementAndGet()));
            pilotNumber.getStyleClass().add("pilot-number");

            Label pilotData = new Label(pilot.toString());
            pilotData.getStyleClass().add("pilot-data");

            Button addPilotToChampionship = new Button("Aggiungi");

            // Quando cliccato, aggiunge il pilota al campionato se non è già presente
            addPilotToChampionship.setOnMouseClicked(_ -> {
                if (!championshipReference.getPilots().contains(pilot)) {
                    championshipReference.addPilot(pilot);
                    singleChampionshipMenùControllerReference.setParticipantsContainer();
                    singleChampionshipMenùControllerReference.addRacesCard();
                } else {
                    showError("Pilota già presente nel campionato!");
                }
            });

            // Crea il menu con le opzioni "Modifica" e "Elimina"
            //MenuItem editItem = new MenuItem("Modifica");
            MenuItem deleteItem = new MenuItem("Elimina");
            /*
            editItem.setOnAction(e -> {
                System.out.println("Modifica ");
                handleEditPilots();
            });*/

            deleteItem.setOnAction(e->
            {
                handleDeletePilots(pilot);
            });

            MenuButton optionsButton = new MenuButton("⋮", null, deleteItem);
            optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
            optionsButton.getStyleClass().add("three-dots");


            pilotBox.getChildren().addAll(pilotNumber, pilotData, addPilotToChampionship);


            BorderPane savedPilotsPane = new BorderPane();
            savedPilotsPane.setCenter(pilotBox);
            savedPilotsPane.setRight(optionsButton);
            savedPilotsPane.setStyle("-fx-padding: 10;");
            savedPilotsPane.getStyleClass().add("pilot-container");

            optionsButton.setOnMouseClicked(e -> e.consume());

            pilotsAnchor.getChildren().add(savedPilotsPane);
        });
    }

    // Carica i piloti salvati da file
    private void getSavedPilots() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVEPATH));
            savedPilots = (ArrayList<Pilot>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.err.println("pilots.bin NOT FOUND");
        }
    }

    // Salva i piloti correnti su file
    public static void savePilots() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVEPATH));
            out.writeObject(savedPilots);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mostra un alert di errore con messaggio personalizzato
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
