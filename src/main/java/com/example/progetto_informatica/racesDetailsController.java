package com.example.progetto_informatica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class racesDetailsController implements Initializable {

    @FXML
    private VBox cardContainer;

    @FXML
    private ImageView imageView;
    @FXML
    private TextField searchField;

    private List<Player> allPlayers = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //immagine ancora da implementare
        //Image image = new Image(getClass().getResource("/com/example/progetto_informatica/lente.png").toExternalForm());
        //imageView.setImage(image);
        // Popola la lista
        allPlayers = List.of(
                new Player("Nicolò Cipollini", 4, "1,30", 600, 1),
                new Player("Mateus De Silva", 4, "1,20", 600, 2),
                new Player("Alexandra Dolea", 3, "1,45", 500, 3),
                new Player("Lorenzo Donati", 4, "2,00", 480, 4)
        );

        updateCardList("");

        // Listener per la ricerca
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateCardList(newVal);
        });
    }

    // Aggiorna le card filtrando per nome
    private void updateCardList(String filter) {
        cardContainer.getChildren().clear();
        for (Player p : allPlayers) {
            if (p.name.toLowerCase().contains(filter.toLowerCase())) {
                cardContainer.getChildren().add(createPlayerCard(p));
            }
        }
    }

    // Crea la card visiva per un giocatore
    private HBox createPlayerCard(Player p) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #ccf5fc; -fx-background-radius: 10; -fx-border-radius: 10;");

        Label rankLabel = new Label(p.rank + ".");
        rankLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox infoBox = new VBox(5);
        Label nameLabel = new Label(p.name);
        HBox stats = new HBox(15);
        stats.getChildren().addAll(
                new Label("Completed throws: " + p.completedThrows + "/4"),
                new Label("Best time: " + p.bestTime),
                new Label("Total score: " + p.totalScore + "/600")
        );

        infoBox.getChildren().addAll(nameLabel, stats);
        card.getChildren().addAll(rankLabel, infoBox);

        return card;
    }

    // Classe dati "temporanei"
    static class Player {
        String name;
        int completedThrows;
        String bestTime;
        int totalScore;
        int rank;

        Player(String name, int completedThrows, String bestTime, int totalScore, int rank) {
            this.name = name;
            this.completedThrows = completedThrows;
            this.bestTime = bestTime;
            this.totalScore = totalScore;
            this.rank = rank;
        }
    }


}
