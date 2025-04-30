package com.example.progetto_informatica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;

public class championshipsMenùController implements Initializable {
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

        if(!championshipName.isEmpty()) {
            championships.add(new Championship(championshipName.get(), Year.now().getValue()));
            addTournamentCard(String.valueOf(Year.now().getValue()), championshipName.get(), "0", true, championships.get(championships.size()-1));
            //saveChampionships();
        }
    }

    @FXML
    private void handleEditTournament() {
        // Implementation for editing a tournament
        System.out.println("Edit functionality to be implemented");
    }

    private void addAllChampionshipsCards() throws IOException {
        for(Championship c : championships)
        {
            addTournamentCard(Integer.toString(c.getChampionshipYear()), c.getChampionshipName(), Integer.toString(c.getChampionshipParticipantsNumber()), c.isChampionshipOpen(), c);
        }
    }

    private void addTournamentCard(String year, String title, String participants, boolean status, Championship championshipReference) throws IOException {
        VBox tournamentContainer = new VBox();
        tournamentContainer.getStyleClass().add("tournament-container");
        tournamentContainer.setSpacing(5);

        Label yearLabel = new Label(year);
        yearLabel.getStyleClass().add("year-label");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("tournament-title");

        Label participantsLabel = new Label(participants + " partecipanti");
        participantsLabel.getStyleClass().add("participants-label");

        Label statusLabel = new Label(status ? "Aperto" : "Terminato");
        statusLabel.getStyleClass().add("status-label");
        statusLabel.getStyleClass().add(status ? "in-progress" : "completed");

        tournamentContainer.getChildren().addAll(yearLabel, titleLabel, participantsLabel, statusLabel);

        tournamentContainer.setOnMouseClicked(event -> {
            main.openRacesMenù(championshipReference);
        });

        championshipAnchor.getChildren().add(0, tournamentContainer);

        // Add separator if not the first tournament
        if (championshipAnchor.getChildren().size() > 1) {
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            championshipAnchor.getChildren().add(1, separator); // Add after the new tournament
        }
    }

    private void getChampionships() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVEPATH));
        championships = (ArrayList<Championship>) in.readObject();
    }

    public static void saveChampionships() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVEPATH));
            out.writeObject(championships);
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}