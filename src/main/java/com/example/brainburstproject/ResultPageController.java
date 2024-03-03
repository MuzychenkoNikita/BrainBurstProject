package com.example.brainburstproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.brainburstproject.MenuPageController.myObject;

public class ResultPageController implements Initializable {

    @FXML
    private ProgressBar ProgressBar;

    @FXML
    private Button goToMenu;

    @FXML
    private Button playAgain;

    @FXML
    private Label quizCompletedLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    void onGoToMenuClick(ActionEvent event) throws IOException {
        goToMenu.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class
                .getResource("menuPage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Brain Burst");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.showAndWait();
    }

    @FXML
    void onPlayAgainButtonClick(ActionEvent event) throws IOException {
        playAgain.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class
                .getResource("quizPage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Brain Burst");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoreLabel.setText("Your score is: " + myObject.getResult() + "/" + myObject.getQuizLength());
    }
}
