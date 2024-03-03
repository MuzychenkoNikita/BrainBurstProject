package com.example.brainburstproject.animations;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class WrongAnswer {

    private Button button;

    public WrongAnswer(Button button) {
        this.button = button;
    }

    public void playAnim() {
        String originalColor = button.getStyle();

        Timeline timeline = new Timeline();

        KeyValue keyValue1 = new KeyValue(button.styleProperty(), "-fx-background-color: #ff6171;");
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.0), keyValue1);

        KeyValue keyValue2 = new KeyValue(button.styleProperty(), originalColor);
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.2), keyValue2);

        timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);

        timeline.setOnFinished((ActionEvent event) -> {
            button.setStyle("-fx-background-color: #d5d5f7;");
        });

        timeline.play();
    }
}
