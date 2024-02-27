package com.example.main_s2024;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    private DbHandler connection;

    @FXML
    protected void onHelloButtonClick() {
        connection = new DbHandler();
        welcomeText.setText(connection.getInfo());
    }
}