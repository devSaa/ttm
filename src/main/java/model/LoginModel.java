package model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LoginModel {
    private GridPane grid;
    private Button registerButton;
    private Button loginButton;
    private TextField userField;
    private PasswordField passwordField;
    private Text hint;

    public LoginModel() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(25);

        Text sceneTitle = new Text("Time Task Manager");
        sceneTitle.setId("task-title");
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);
        userField = new TextField();
        grid.add(userField, 1, 1);

        Label password = new Label("Password:");
        grid.add(password, 0, 2);
        passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        registerButton = new Button("Register");
        loginButton = new Button("Log in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(registerButton);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 3);

        hint = new Text();
        hint.setId("warning");
        HBox hbMessage = new HBox(10);
        hbMessage.setAlignment(Pos.BOTTOM_RIGHT);
        hbMessage.getChildren().add(hint);
        grid.add(hbMessage, 0, 4, 2, 1);
    }

    public GridPane getGrid() {
        return grid;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public TextField getUserField() {
        return userField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Text getHint() {
        return hint;
    }

    public void setHint(Text hint) {
        this.hint = hint;
    }
}
