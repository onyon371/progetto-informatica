package com.example.progetto_informatica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
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
    private VBox pilotsAnchor;

    private Championship championshipReference;
    private static ArrayList<Pilot> savedPilots;

    private static final String SAVEPATH = "pilots.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championshipReference = null;
    }

    public void initChampionship(Championship championshipReference)
    {
        this.championshipReference = championshipReference;
        //savedPilots = new ArrayList<Pilot>();

        try {
            getSavedPilots();
            addPilotsCard();
        }catch (Exception e)
        {
            e.printStackTrace();
            savedPilots = new ArrayList<Pilot>();
            savePilots();
        }
    }

    @FXML
    void handleCreateNewPilot()
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Aggiungi Pilota");
        dialog.setHeaderText("Inserisci i dati del pilota che vuoi aggiungere!");
        dialog.setContentText("Nome Pilota:");

        Optional<String> pilotName = dialog.showAndWait();

        if(!pilotName.isEmpty()) {
            dialog.setTitle("Aggiungi Pilota");
            dialog.setHeaderText("Inserisci i dati del pilota che vuoi aggiungere!");
            dialog.setContentText("Cognome Pilota:");

            Optional<String> pilotSurname = dialog.showAndWait();

            if(!pilotSurname.isEmpty() && !savedPilots.contains(new Pilot(pilotName.get(), pilotSurname.get()))) {
                try {
                    savedPilots.add(new Pilot(pilotName.get(), pilotSurname.get()));
                    addPilotsCard();
                    savePilots();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore creazione pilota");
                alert.setHeaderText("Pilota non creato correttamente");
                alert.setContentText("Pilota già esistente!");
                alert.showAndWait();
            }
        }
    }


    @FXML
    void handleEditPilots()
    {

    }

    @FXML
    void handleDeletePilots()
    {

    }

    private void addPilotsCard() {
        pilotsAnchor.getChildren().clear();

        AtomicInteger counter = new AtomicInteger(0);

        savedPilots.forEach(pilot -> {
            HBox pilotBox = new HBox();
            pilotBox.getStyleClass().add("pilot-container");

            Label pilotNumber = new Label(Integer.toString(counter.incrementAndGet()));
            pilotNumber.getStyleClass().add("pilot-number");

            Label pilotData = new Label(pilot.toString());
            pilotNumber.getStyleClass().add("pilot-data");

            Button addPilotToChampionship = new Button("Aggiungi");

            addPilotToChampionship.setOnMouseClicked(event -> {
                if(!championshipReference.getPilots().contains(pilot)) {
                    championshipReference.addPilot(pilot);
                }else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore aggiunta pilota");
                    alert.setHeaderText("Pilota non aggiunto correttamente");
                    alert.setContentText("Pilota già presente nel campionato!");
                    alert.showAndWait();
                }
            });

            pilotBox.getChildren().addAll(pilotNumber, pilotData, addPilotToChampionship);
            pilotsAnchor.getChildren().add(pilotBox);
        });
    }

    private void getSavedPilots() {
        try {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVEPATH));
        savedPilots = (ArrayList<Pilot>) in.readObject();
        in.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void savePilots() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVEPATH));
            out.writeObject(savedPilots);
            out.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
