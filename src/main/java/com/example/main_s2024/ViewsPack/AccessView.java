package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.StageHandler.StageHandler;
import com.example.main_s2024.Utils.EnvLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AccessView implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private Consumer<Void> onLoginSuccessCallback;

    private boolean isPasswordVisible = false;


    public void setOnLoginSuccessCallback(Consumer<Void> onLoginSuccessCallback) {
        this.onLoginSuccessCallback = onLoginSuccessCallback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(this::login);
    }

    private void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String validUsername = EnvLoader.dotenv.get("adminUsername");
        String validPassword = EnvLoader.dotenv.get("adminPassword");


        if (validUsername.equals(username) && validPassword.equals(password)) {
            if (onLoginSuccessCallback != null) {
                onLoginSuccessCallback.accept(null);
            }

            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                StageHandler stageHandler = new StageHandler(currentStage);

                stageHandler.openNewStage("/com/example/main_s2024/report_view.fxml", "Reports", false,
                        false);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    @FXML
    public void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordField.setManaged(true);
            passwordField.setVisible(true);
            isPasswordVisible = false;
        } else {
            passwordField.setManaged(false);
            passwordField.setVisible(false);
            isPasswordVisible = true;
        }
    }


}
