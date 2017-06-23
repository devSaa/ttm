package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.util.Duration;
import menu.TimeTaskClient;
import message.Message;
import message.MessageType;
import model.LoginModel;
import model.MainModel;
import view.MainMenu;

import java.net.ConnectException;

public class LoginController {
    private final LoginModel loginModel;

    public LoginController(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public void register() {
        loginModel.getRegisterButton().setOnAction(e -> {
            if (loginModel.getUserField().getText().trim().isEmpty() ||
                    loginModel.getPasswordField().getText().trim().isEmpty()) {
                loginModel.getHint().setText("You need to enter all fields");
            } else {
                try {
                    Message responseMessage = TimeTaskClient.getClientSocket().sendMessage(
                            loginModel.getUserField().getText(), loginModel.getPasswordField().getText(),
                            MessageType.CREATE_MESSAGE, "register"
                    );
                    MainMenu.setUser(responseMessage.getUser());

                    if (responseMessage.getMessageType().equals(MessageType.FAIL_MESSAGE)) {
                        loginModel.getHint().setText("Already registered");
                    } else if (responseMessage.getMessageType().equals(MessageType.CREATE_SUCCESS_MESSAGE)) {
                        loginModel.getHint().setId("success");
                        loginModel.getHint().setText("Successfully registered");
                        MainMenu mainMenu = new MainMenu();
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.millis(1000),
                                ae -> MainMenu.getPrimaryStage().setScene(MainMenu.getScene())));
                        timeline.play();
                        timeline.setOnFinished(event -> setMainMenuPosition());
                    }
                } catch (ConnectException ce) {
                    loginModel.getHint().setText("No connection");
                }
            }
        });
    }

    public void login() {
        loginModel.getLoginButton().setOnAction(e -> {
            processLogin();
        });
    }

    public void loginOnEnter() {
        loginModel.getPasswordField().setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                processLogin();
            }
        });
    }

    public void processLogin() {
        if(loginModel.getUserField().getText().trim().isEmpty() ||
                loginModel.getPasswordField().getText().trim().isEmpty()) {
            loginModel.getHint().setText("You need to enter all fields");
        } else {
            try {
                Message responseMessage = TimeTaskClient.getClientSocket().sendMessage(
                        loginModel.getUserField().getText(), loginModel.getPasswordField().getText(),
                        MessageType.LOGIN_MESSAGE, "log in"
                );
                MainMenu.setUser(responseMessage.getUser());

                if (responseMessage.getMessageType().equals(MessageType.FAIL_MESSAGE)) {
                    loginModel.getHint().setText("Wrong username or password");
                } else if (responseMessage.getMessageType().equals(MessageType.LOGIN_SUCCESS_MESSAGE)) {
                    loginModel.getHint().setId("success");
                    loginModel.getHint().setText("Logged in successfully");
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.getMainModel().loadTreeView(MainMenu.getUser().getTaskList(),
                            MainModel.getTreeView().getRoot());
                    Timeline timeline = new Timeline(new KeyFrame(
                            Duration.millis(1000),
                            e -> MainMenu.getPrimaryStage().setScene(MainMenu.getScene())));
                    timeline.play();
                    timeline.setOnFinished(event -> setMainMenuPosition());
                }
            } catch (ConnectException ce) {
                loginModel.getHint().setText("No connection");
            }
        }
    }

    public void setMainMenuPosition() {
        MainMenu.getPrimaryStage().setResizable(true);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        MainMenu.getPrimaryStage().setX((primScreenBounds.getWidth() - MainMenu.getPrimaryStage().getWidth()) / 2);
        MainMenu.getPrimaryStage().setY((primScreenBounds.getHeight() - MainMenu.getPrimaryStage().getHeight()) / 4);
    }
}
