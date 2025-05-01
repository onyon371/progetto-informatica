package com.example.progetto_informatica;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleRaceMenùController implements Initializable {

    @FXML
    private VBox pilotsAnchor;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField searchField;

    private Race raceReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //immagine ancora da implementare
        //Image image = new Image(getClass().getResource("/com/example/progetto_informatica/lente.png").toExternalForm());
        //imageView.setImage(image);


        // Listener per la ricerca
        /*searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateCardList(newVal);
        });*/
    }

    public void initRaceReference(Race raceReference)
    {
        this.raceReference = raceReference;
        addPilotsCards();
    }

/*
    // Aggiorna le card filtrando per nome
    private void updateCardList(String filter) {
        cardContainer.getChildren().clear();
        for (Player p : allPlayers) {
            if (p.name.toLowerCase().contains(filter.toLowerCase())) {
                cardContainer.getChildren().add(createPlayerCard(p));
            }
        }
    }*/

    // Crea la card visiva per un giocatore
    private void addPilotsCards() {

        pilotsAnchor.getChildren().clear();
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < raceReference.getPilots().size(); i++) {
            HBox card = new HBox(20);
            card.setPadding(new Insets(15));
            card.setStyle("-fx-background-color: #ccf5fc; -fx-background-radius: 10; -fx-border-radius: 10;");

            Label rankLabel = new Label(counter.incrementAndGet() + ".");
            rankLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            VBox infoBox = new VBox(5);
            Label nameLabel = new Label(raceReference.getPilots().get(i).toString());
            HBox stats = new HBox(15);
            stats.getChildren().addAll(
                    new Label("Lanci Effettuati: " + raceReference.getThrowsCompletedOfSpecificPilot(i) + "/" + raceReference.getMaxThrows()),

                    new Label(raceReference.getBestTimeOfSpecificPilot(i) != LocalTime.of(0, 0, 0, 0) ? "Miglior Tempo: " + raceReference.getBestTimeOfSpecificPilot(i) : "Miglior Tempo: Nessun Lancio Registrato!"),

                    new Label("Punti Totali: " + raceReference.getPointsOfSpecificPilot(i) + "/" + raceReference.getMaxPoints())
            );

            infoBox.getChildren().addAll(nameLabel, stats);
            card.getChildren().addAll(rankLabel, infoBox);
            pilotsAnchor.getChildren().add(card);
        }
    }
}

