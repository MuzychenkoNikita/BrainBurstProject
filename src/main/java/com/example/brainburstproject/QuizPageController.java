package com.example.brainburstproject;

import com.example.brainburstproject.animations.RightAnswer;
import com.example.brainburstproject.animations.Shake;
import com.example.brainburstproject.animations.ShakeAnswer;
import com.example.brainburstproject.animations.WrongAnswer;
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
import java.util.ArrayList;
import java.util.ResourceBundle;


import static com.example.brainburstproject.MenuPageController.myObject;

public class QuizPageController implements Initializable {

    @FXML
    private Button goBackButton;

    @FXML
    private Button option1Button;

    @FXML
    private Button option2Button;

    @FXML
    private Button option3Button;

    @FXML
    private Button option4Button;

    @FXML
    private Label questionLabel;

    @FXML
    private ProgressBar quizProgressBar;

    @FXML
    void onGoBackButtonClick(ActionEvent event) throws IOException {
        goBackButton.getScene().getWindow().hide();

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
    void onOption1ButtonClick(ActionEvent event) {
        checkUserAnswer(option1Button.getText());
        if (!userRight) {
            WrongAnswer userButtonAnim = new WrongAnswer(option1Button);
            ShakeAnswer userButtonAnim2 = new ShakeAnswer(option1Button);
            userButtonAnim.playAnim();
            userButtonAnim2.playAnim();
        } else {
            RightAnswer useButtonAnim = new RightAnswer(option1Button);
            useButtonAnim.playAnim();
        }
        questionCount = questionCount + 6;
        quizEnd();
        updateQuestion();
    }

    @FXML
    void onOption2ButtonClick(ActionEvent event) {
        checkUserAnswer(option2Button.getText());
        if (!userRight) {
            WrongAnswer userButtonAnim = new WrongAnswer(option2Button);
            ShakeAnswer userButtonAnim2 = new ShakeAnswer(option2Button);
            userButtonAnim.playAnim();
            userButtonAnim2.playAnim();
        } else {
            RightAnswer useButtonAnim = new RightAnswer(option2Button);
            useButtonAnim.playAnim();
        }
        questionCount = questionCount + 6;
        quizEnd();
        updateQuestion();
    }

    @FXML
    void onOption3ButtonClick(ActionEvent event) {
        checkUserAnswer(option3Button.getText());
        if (!userRight) {
            WrongAnswer userButtonAnim = new WrongAnswer(option3Button);
            ShakeAnswer userButtonAnim2 = new ShakeAnswer(option3Button);
            userButtonAnim.playAnim();
            userButtonAnim2.playAnim();
        } else {
            RightAnswer useButtonAnim = new RightAnswer(option3Button);
            useButtonAnim.playAnim();
        }
        questionCount = questionCount + 6;
        quizEnd();
        updateQuestion();
    }

    @FXML
    void onOption4ButtonClick(ActionEvent event) {
        checkUserAnswer(option4Button.getText());
        if (!userRight) {
            WrongAnswer userButtonAnim = new WrongAnswer(option4Button);
            ShakeAnswer userButtonAnim2 = new ShakeAnswer(option4Button);
            userButtonAnim.playAnim();
            userButtonAnim2.playAnim();
        } else {
            RightAnswer useButtonAnim = new RightAnswer(option4Button);
            useButtonAnim.playAnim();
        }
        questionCount = questionCount + 6;
        quizEnd();
        updateQuestion();
    }

    public void updateQuestion() {
        questionLabel.setText(quizData.get(questionCount));
        option1Button.setText(quizData.get(questionCount + 1));
        option2Button.setText(quizData.get(questionCount + 2));
        option3Button.setText(quizData.get(questionCount + 3));
        option4Button.setText(quizData.get(questionCount + 4));
        userProgress();
    }

    public void checkUserAnswer(String answer) {
        if (quizData.get(questionCount + 5).equals(answer)) {
            userScore++;
            userRight = true;
        } else {
            userRight = false;
        }
    }

    public void userProgress() {
        quizProgressBar.setProgress((questionCount * 1f) / (quizSize * 1f));
    }

    public void quizEnd() {
        if (questionCount == quizSize) {
            myObject.setResult(userScore);
            myObject.setQuizLength(quizSize / 6);
            goBackButton.getScene().getWindow().hide();
            handler.saveUserScore();

            FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class
                    .getResource("resultPage-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 900, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle("Brain Burst");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.showAndWait();
        }
    }


    DatabaseHandler handler = new DatabaseHandler();
    ArrayList<String> quizData = new ArrayList<>(handler.getQuestionData());
    int quizSize = quizData.size();
    boolean userRight;
    int userScore = 0;
    int questionCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateQuestion();
    }
}
