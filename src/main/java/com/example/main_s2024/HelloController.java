package com.example.main_s2024;

import com.example.main_s2024.DbHandler.DbHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;
    private DbHandler connection;

    private StageHandler stageHandler;

    @FXML
    protected void onHelloButtonClick() {
        connection = new DbHandler();
        Stage currentStage = (Stage) welcomeText.getScene().getWindow();
        stageHandler = new StageHandler(currentStage);

        stageHandler.switchScene("landing-page.fxml", "Landing Page");


        welcomeText.setText(connection.getInfo());
    }
}