package com.example.main_s2024;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LandingPage {

    @FXML
    private TextField landing_text_fld;
    @FXML
    private Button back_btn;
    private StageHandler stageHandler;

    @FXML
    public void initialize(){
        landing_text_fld.setText("Welcome to the landing page!");
    }

    public void onBackButtonClicked() {;
        Stage currentStage = (Stage) landing_text_fld.getScene().getWindow();
        stageHandler = new StageHandler(currentStage);
        stageHandler.switchScene("hello-view.fxml", "Hello!");
    }

}
