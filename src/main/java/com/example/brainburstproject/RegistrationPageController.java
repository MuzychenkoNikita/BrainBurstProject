package com.example.brainburstproject;

import com.example.brainburstproject.animations.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationPageController {

    @FXML
    private TextField emailCodeField;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink loginAccountLink;

    @FXML
    private TextField nicknameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordFieldCheck;

    @FXML
    private Button signUpButton;

    @FXML
    void onLoginAccountLinkClick(ActionEvent event) throws IOException {
        loginAccountLink.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class.getResource("welcomePage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Brain Burst");
        stage.setScene(scene);
        stage.show();
        stage.showAndWait();
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) throws IOException {
        if((nicknameField.getText().trim().equals("")) || (passwordField.getText().trim().equals("")) || (passwordFieldCheck.getText().trim().equals(""))) {
            Shake userLoginAnim = new Shake(nicknameField);
            userLoginAnim.playAnim();
            Shake userPasswordAnim = new Shake(passwordField);
            userPasswordAnim.playAnim();
            Shake userPasswordCheckAnim = new Shake(passwordFieldCheck);
            userPasswordCheckAnim.playAnim();
            errorLabel.setText("Fill all the fields!");
        } else if (!(passwordField.getText().trim().equals(passwordFieldCheck.getText().trim()))) {
            Shake userPasswordAnim = new Shake(passwordField);
            userPasswordAnim.playAnim();
            Shake userPasswordCheckAnim = new Shake(passwordFieldCheck);
            userPasswordCheckAnim.playAnim();
            errorLabel.setText("Passwords are not the same!");
        } else if ((passwordField.getText().trim().length() < 8)) {
            Shake userPasswordAnim = new Shake(passwordField);
            userPasswordAnim.playAnim();
            Shake userPasswordCheckAnim = new Shake(passwordFieldCheck);
            userPasswordCheckAnim.playAnim();
            errorLabel.setText("Password must be at least 8 characters!");
        } else
            findUser(nicknameField.getText());
    }

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String nickname = nicknameField.getText();
        String password = passwordField.getText();

        User user = new User(nickname, password);

        dbHandler.signUpUser(user);
        System.out.println("User Added");
    }

    private void findUser(String nicknameText) throws IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        if (dbHandler.doesUserExist(nicknameText)) {
            Shake userLoginAnim = new Shake(nicknameField);
            userLoginAnim.playAnim();
            errorLabel.setText("User with such a nickname already exists!");
        } else {
            signUpNewUser();
            signUpButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageApplication.class.getResource("welcomePage-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("Brain Burst");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            stage.showAndWait();
        }
    }
}