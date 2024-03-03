package com.example.brainburstproject;

import com.example.brainburstproject.animations.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.brainburstproject.MenuPageController.myObject;

public class WelcomePageController {

    @FXML
    private Hyperlink createAccountLink;

    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Button logGoogleButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nicknameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    void onCreateAccountLinkClick(ActionEvent event) throws IOException {
        createAccountLink.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class
                .getResource("registrationPage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Brain Burst");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.showAndWait();
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException {
        String nicknameText = nicknameField.getText().trim();
        String passwordText = passwordField.getText().trim();
        myObject.setNickname(nicknameField.getText().trim());

        if(!nicknameText.equals("") || !passwordText.equals(""))
            loginUser(nicknameText, passwordText);
        else
            errorLabel.setText("Fill all the fields!");
    }

    private void loginUser(String nicknameText, String passwordText) throws IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setNickname(nicknameText);
        user.setPassword(passwordText);
        dbHandler.getUser(user);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        try {
            while (result.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(counter >= 1) {
            loginButton.getScene().getWindow().hide();

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
        else {
            Shake userLoginAnim = new Shake(nicknameField);
            Shake userPassAnim = new Shake(passwordField);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();
            errorLabel.setText("Incorrect user data!");
        }
    }

}
