package com.example.main_s2024.StageHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StageHandler {
    private Stage stage;


    public StageHandler(Stage stage) {
        this.stage = stage;
    }

    public void openNewStage(String fxmlFile, String title, boolean modal, boolean resizeable) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlFile);
            if (fxmlUrl == null) {
                throw new IOException("Cannot find FXML file: " + fxmlFile);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            if (modal) {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(stage);
            }
            stage.setResizable(resizeable);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchScene(String fxmlPath, String title) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                throw new IOException("Cannot find FXML file: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resizeWindow(int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public void setFullScreen(boolean value) {
        stage.setFullScreen(value);
    }

    public void showStage() {
        stage.show();
    }

    public void hideStage() {
        stage.hide();
    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public void setResizable(boolean resizable) {
        stage.setResizable(resizable);
    }

    public void showModalWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle(title);
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeStage() {
        stage.close();
    }

    public void setSceneUserAgentStylesheet(String stylesheet) {
        stage.getScene().getStylesheets().add(stylesheet);
    }


}
