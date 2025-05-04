package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;

public class AllChampionshipsMenùController implements Initializable {

    // Container principale per le schede dei campionati
    @FXML
    private VBox championshipAnchor;

    // Scroll pane contenente la lista dei campionati
    @FXML
    private ScrollPane scrollPane;

    // Lista statica di tutti i campionati
    private static ArrayList<Championship> championships;

    // Percorso del file di salvataggio dei campionati
    private final static String SAVEPATH = "save.bin";

    // Metodo chiamato automaticamente all’avvio dell'interfaccia
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (championships == null) {
            championships = new ArrayList<>();
            try {
                getChampionships(); // Carica i campionati dal file
                addAllChampionshipsCards(); // Mostra i campionati nell'interfaccia
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                addAllChampionshipsCards();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Gestisce la creazione di un nuovo campionato tramite dialog
    @FXML
    private void handleAddTournament() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Campionato");
        dialog.setHeaderText("Inserisci i dettagli del nuovo campionato");
        dialog.setContentText("Nome Campionato:");

        Optional<String> championshipName = dialog.showAndWait();

        if (championshipName.isPresent() && !championshipName.get().isEmpty()) {
            Championship newChampionship = new Championship(championshipName.get(), Year.now().getValue());
            championships.add(newChampionship);
            addTournamentCard(
                    String.valueOf(newChampionship.getChampionshipYear()),
                    newChampionship.getChampionshipName(),
                    "0",
                    true,
                    newChampionship
            );
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore creazione campionato");
            alert.setHeaderText("Campionato non creato correttamente");
            alert.setContentText("Inserire un nome al campionato!");
            alert.showAndWait();
        }
    }

    // Elimina un campionato specifico dalla lista
    @FXML
    private void deleteTournament(Championship championshipReference) {
        championships.remove(championshipReference);
        addAllChampionshipsCards(); // Aggiorna la lista visiva
    }

    // Aggiunge tutte le schede dei campionati presenti
    private void addAllChampionshipsCards() {
        championshipAnchor.getChildren().clear();
        try {
            for (Championship c : championships) {
                addTournamentCard(
                        Integer.toString(c.getChampionshipYear()),
                        c.getChampionshipName(),
                        Integer.toString(c.getChampionshipParticipantsNumber()),
                        c.isChampionshipOpen(),
                        c
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Crea e aggiunge una scheda visuale per un campionato
    private void addTournamentCard(String year, String title, String participants, boolean status, Championship championshipReference) throws IOException {
        VBox contentBox = new VBox();
        contentBox.setSpacing(5);
        contentBox.getStyleClass().add("tournament-content");

        Label yearLabel = new Label(year);
        yearLabel.getStyleClass().add("year-label");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("tournament-title");

        Label participantsLabel = new Label(participants + " partecipanti");
        participantsLabel.getStyleClass().add("participants-label");

        Label statusLabel = new Label(status ? "Aperto" : "Terminato");
        statusLabel.getStyleClass().add("status-label");
        statusLabel.getStyleClass().add(status ? "in-progress" : "completed");

        contentBox.getChildren().addAll(yearLabel, titleLabel, participantsLabel, statusLabel);

        // Crea il menu con le opzioni "Modifica" e "Elimina"
        //MenuItem editItem = new MenuItem("Modifica");
        MenuItem deleteItem = new MenuItem("Elimina");
        /*
        editItem.setOnAction(e -> {
            System.out.println("Modifica " + title);
            // TODO: implementare la logica di modifica
        });*/

        deleteItem.setOnAction(e -> deleteTournament(championshipReference));

        MenuButton optionsButton = new MenuButton("⋮", null, deleteItem);
        optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
        optionsButton.getStyleClass().add("three-dots");

        BorderPane tournamentPane = new BorderPane();
        tournamentPane.setCenter(contentBox);
        tournamentPane.setRight(optionsButton);
        tournamentPane.setStyle("-fx-padding: 10;");
        tournamentPane.getStyleClass().add("tournament-container");

        // Apertura del singolo campionato al click sulla scheda
        tournamentPane.setOnMouseClicked(event -> Main.openSingleChampionshipMenù(championshipReference));

        // Evita che il click sul menu interferisca con l’apertura della scheda
        optionsButton.setOnMouseClicked(e -> e.consume());

        championshipAnchor.getChildren().add(0, tournamentPane);

        // Aggiunge separatore visivo se ci sono più schede
        if (championshipAnchor.getChildren().size() > 1) {
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            championshipAnchor.getChildren().add(1, separator);
        }
    }

    // Carica i campionati salvati dal file
    private void getChampionships() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVEPATH));
        championships = (ArrayList<Championship>) in.readObject();
        in.close();
    }

    // Salva i campionati correnti nel file
    public static void saveChampionships() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVEPATH));
            out.writeObject(championships);
            out.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
