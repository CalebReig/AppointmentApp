package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.utils.Popup;
import main.db.Auth;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * This class controls events for the Login page.
 */
public class LoginController extends BaseController{

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label userLocationLabel;
    @FXML private Label titleLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label locationLabel;
    @FXML private Button loginButton;
    @FXML private Button closeButton;

    /**
     * This method sets the initial settings for the Login page.
     * All items on the Login page will be displayed in French
     * if French is the user's system language.
     */
    public void initialize() {
        titleLabel.setText(getWord("login"));
        usernameLabel.setText(getWord("username"));
        passwordLabel.setText(getWord("password"));
        locationLabel.setText(getWord("location"));
        loginButton.setText(getWord("login"));
        closeButton.setText(getWord("close"));
        userLocationLabel.setText(ZoneId.systemDefault().getDisplayName(TextStyle.NARROW,
                                    Locale.forLanguageTag(Main.LANGUAGE)));
    }

    /**
     * This method submits the user's credentials to the database.
     */
    @FXML private void login() throws IOException {
        if (Auth.verifyLogin(usernameField.getText(), passwordField.getText())) {
            Main.setRoot("homepage", 670, 875);
            Main.window.setX(200);
            Main.window.setY(20);
        }
        else {
            if (Main.LANGUAGE.equals("fr")) {Popup.displayError(4);}
            else {Popup.displayError(0);}
            usernameField.setText(null);
            passwordField.setText(null);
        }
    }
}
