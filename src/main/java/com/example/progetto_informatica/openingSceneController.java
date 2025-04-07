package com.example.progetto_informatica;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class openingSceneController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}