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
    @FXML
    private VBox championshipAnchor;
    @FXML
    private ScrollPane scrollPane;

    private static ArrayList<Championship> championships;
    private final static String SAVEPATH = "save.bin";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(championships==null) {
            championships = new ArrayList<Championship>();
            try {
                getChampionships();
                addAllChampionshipsCards();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else {
            try {
                addAllChampionshipsCards();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void handleAddTournament() throws IOException {
        // Create dialog to get tournament details
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Campionato");
        dialog.setHeaderText("Inserisci i dettagli del nuovo campionato");
        dialog.setContentText("Nome Campionato:");

        Optional<String> championshipName = dialog.showAndWait();

        if(championshipName.get() != "") {
            championships.add(new Championship(championshipName.get(), Year.now().getValue()));
            addTournamentCard(String.valueOf(Year.now().getValue()), championshipName.get(), "0", true, championships.get(championships.size()-1));
        }else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore creazione campionato");
            alert.setHeaderText("Campionato non creato correttamente");
            alert.setContentText("Inserire un nome al campionato!");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteTournament(Championship championshipReference) {
        championships.remove(championshipReference);
        addAllChampionshipsCards();
    }

    private void addAllChampionshipsCards() {
        championshipAnchor.getChildren().clear();
        try {
            for (Championship c : championships) {
                addTournamentCard(Integer.toString(c.getChampionshipYear()), c.getChampionshipName(), Integer.toString(c.getChampionshipParticipantsNumber()), c.isChampionshipOpen(), c);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTournamentCard(String year, String title, String participants, boolean status, Championship championshipReference) throws IOException {
        // Contenuto principale (sinistra)
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

        // Tre puntini (destra)
        MenuItem editItem = new MenuItem("Modifica");
        MenuItem deleteItem = new MenuItem("Elimina");

        editItem.setOnAction(e -> {
            System.out.println("Modifica " + title);
            // TODO: implementa modifica
        });

        deleteItem.setOnAction(e -> {
            deleteTournament(championshipReference);
        });

        MenuButton optionsButton = new MenuButton("⋮", null, editItem, deleteItem);
        optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
        optionsButton.getStyleClass().add("three-dots");

        // Layout principale
        BorderPane tournamentPane = new BorderPane();
        tournamentPane.setCenter(contentBox);
        tournamentPane.setRight(optionsButton);
        tournamentPane.setStyle("-fx-padding: 10;");
        tournamentPane.getStyleClass().add("tournament-container");

        tournamentPane.setOnMouseClicked(event -> {
            Main.openSingleChampionshipMenù(championshipReference);
        });

        // Evita conflitto tra click sul pulsante e apertura torneo
        optionsButton.setOnMouseClicked(e -> {
            e.consume(); // blocca la propagazione del click
        });

        championshipAnchor.getChildren().add(0, tournamentPane);

        // Separator
        if (championshipAnchor.getChildren().size() > 1) {
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            championshipAnchor.getChildren().add(1, separator);
        }
    }

    private void getChampionships() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVEPATH));
        championships = (ArrayList<Championship>) in.readObject();
        in.close();
    }

    public static void saveChampionships() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVEPATH));
            out.writeObject(championships);
            out.close();
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}