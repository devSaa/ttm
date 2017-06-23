package view;

import controller.LoginController;
import javafx.scene.Scene;
import menu.TimeTaskClient;
import model.LoginModel;

public class LoginForm {
    private Scene loginScene;
    private LoginModel loginModel;
    private LoginController loginController;

    public LoginForm() {
        loginModel = new LoginModel();
        loginController = new LoginController(loginModel);

        loginController.register();
        loginController.login();
        loginController.loginOnEnter();

        loginScene = new Scene(loginModel.getGrid(), 375, 275);
        loginScene.getStylesheets().add(
                TimeTaskClient.class.getResource("/css/Form.css").toExternalForm());
    }

    public Scene getScene() {
        return loginScene;
    }
}
