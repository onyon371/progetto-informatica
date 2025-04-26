package com.example.progetto_informatica.controller;

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
    private VBox tournamentsList;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            deserializeTournament();
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
            addTournamentCard(String.valueOf(Year.now().getValue()), nomeCampionato.get(), "0", "In Corso");

            try {
                serializeTournament(new TournamentData(String.valueOf(Year.now().getValue()), nomeCampionato.get(), "0", "In Corso"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addTournamentCard(String year, String title, String participants, String status) throws IOException {
        VBox tournamentContainer = new VBox();
        tournamentContainer.getStyleClass().add("tournament-container");
        tournamentContainer.setSpacing(5);

        Label yearLabel = new Label(year);
        yearLabel.getStyleClass().add("year-label");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("tournament-title");

        Label participantsLabel = new Label(participants + " partecipanti");
        participantsLabel.getStyleClass().add("participants-label");

        Label statusLabel = new Label(status);
        statusLabel.getStyleClass().add("status-label");
        statusLabel.getStyleClass().add(status.equals("In Corso") ? "in-progress" : "completed");

        tournamentContainer.getChildren().addAll(yearLabel, titleLabel, participantsLabel, statusLabel);
        tournamentsList.getChildren().add(0, tournamentContainer); // Add at the beginning

        // Add separator if not the first tournament
        if (tournamentsList.getChildren().size() > 1) {
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            tournamentsList.getChildren().add(1, separator); // Add after the new tournament
        }
    }

    @FXML
    private void handleEditTournament() {
        // Implementation for editing a tournament
        System.out.println("Edit functionality to be implemented");
    }

    @FXML
    private void handleDeleteTournament() {
        // Implementation for deleting a tournament
        System.out.println("Delete functionality to be implemented");
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
                    break; // Fine del file
                }
            }
        }
    }

}