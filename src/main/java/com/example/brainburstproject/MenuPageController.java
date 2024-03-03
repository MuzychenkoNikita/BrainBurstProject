package com.example.brainburstproject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class MenuPageController implements Initializable{

    @FXML
    private Button checkScoreButton;

    @FXML
    private Label chosenQuizLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label informationLabel;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Button playChosenQuizButton;

    @FXML
    private ScrollBar quizScrollBar;

    @FXML
    private ListView<String> quizTablesListView;

    @FXML
    private Button signOutButton;

    @FXML
    void onCheckScoreButtonClick(ActionEvent event) {
        informationLabel.setText(handler.getUserQuizScore());
    }

    @FXML
    void onLeaderboardButtonClick(ActionEvent event) {
        informationLabel.setText(handler.getLeaderboard());
    }

    @FXML
    void onSignOutButtonClick(ActionEvent event) throws IOException {
        signOutButton.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class
                .getResource("welcomePage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Brain Burst");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.showAndWait();
    }

    @FXML
    void onPlayChosenQuizButtonClick(ActionEvent event) throws IOException {
        if (!(currentTable == null)) {
            playChosenQuizButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class
                    .getResource("quizPage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Brain Burst");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.showAndWait();
        } else
            errorLabel.setText("You didn't choose the Quiz!");
    }

    DatabaseHandler handler = new DatabaseHandler();
    ArrayList<String> quizNames = new ArrayList<>(handler.getQuizTables());
    String currentTable;

    public static User myObject = new User();

    public void updateAvailibleQuizesList() {
        quizTablesListView.getItems().addAll(quizNames);

        quizTablesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentTable = quizTablesListView.getSelectionModel().getSelectedItem();

                chosenQuizLabel.setText(currentTable);
                informationLabel.setText("");

                myObject.setQuizName(currentTable);
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateAvailibleQuizesList();
    }
}
