package com.example.progetto_informatica.controller;

import com.example.progetto_informatica.Championship;
import javafx.collections.FXCollections;
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

public class openingMenùController implements Initializable {
    @FXML
    private VBox championshipAnchor;
    @FXML
    private ScrollPane scrollPane;

    private ArrayList<Championship> championships;
    private final String SAVEPATH = "save.bin";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getChampionships();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleAddTournament() throws IOException {
        // Create dialog to get tournament details
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Campionato");
        dialog.setHeaderText("Inserisci i dettagli del nuovo campionato");
        dialog.setContentText("Nome Campionato:");

        Optional<String> nomeCampionato = dialog.showAndWait();

        if(!nomeCampionato.isEmpty()) {
            addTournamentCard(String.valueOf(Year.now().getValue()), nomeCampionato.get(), "0", true);
            saveChampionships();
        }
    }

    private void addTournamentCard(String year, String title, String participants, boolean status) throws IOException {
        VBox tournamentContainer = new VBox();
        tournamentContainer.getStyleClass().add("tournament-container");
        tournamentContainer.setSpacing(5);

        Label yearLabel = new Label(year);
        yearLabel.getStyleClass().add("year-label");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("tournament-title");

        Label participantsLabel = new Label(participants + " partecipanti");
        participantsLabel.getStyleClass().add("participants-label");

        Label statusLabel;
        if(status)
        {
            statusLabel = new Label("Aperto");
        }else
        {
            statusLabel = new Label("Terminato");
        }

        statusLabel.getStyleClass().add("status-label");

        statusLabel.getStyleClass().add(status ? "in-progress" : "completed");

        tournamentContainer.getChildren().addAll(yearLabel, titleLabel, participantsLabel, statusLabel);
        championshipAnchor.getChildren().add(0, tournamentContainer); // Add at the beginning

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

    private void saveChampionships() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVEPATH));
        out.writeObject(championships);
    }

    private void addAllChampionshipsCards() throws IOException {
        for(Championship c : championships)
        {
            addTournamentCard(Integer.toString(c.getChampionshipYear()), c.getChampionshipName(), Integer.toString(c.getChampionshipParticipantsNumber()), c.isChampionshipOpen());
        }
    }

    @FXML
    private void handleEditTournament() {
        // Implementation for editing a tournament
        System.out.println("Edit functionality to be implemented");
    }

    public static class TournamentData implements Serializable {
        private String year;
        private String title;
        private String participants;
        private String status;

        public TournamentData(String year, String title, String participants, String status) {
            this.year = year;
            this.title = title;
            this.participants = participants;
            this.status = status;
        }

        public String getYear() {
            return year;
        }

        public String getTitle() {
            return title;
        }

        public String getParticipants() {
            return participants;
        }

        public String getStatus() {
            return status;
        }
    }





/*
    public class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }

    private void serializeTournament(TournamentData td) throws IOException {
        File file = new File("tournamentSaveFile.bin");
        boolean append = file.exists();

        try (ObjectOutputStream out = append
                ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                : new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(td);
        }
    }


    private void deserializeTournament() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("tournamentSaveFile.bin"))) {
            while (true) {
                try {
                    TournamentData child = (TournamentData) in.readObject();
                    addTournamentCard(child.getYear(), child.getTitle(), child.getParticipants(), child.getStatus());
                } catch (EOFException e) {
                    break;
                }
            }
        }
    }
*/
}