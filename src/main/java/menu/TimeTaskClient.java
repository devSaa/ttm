package menu;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import message.MessageType;
import network.ClientSocket;
import view.LoginForm;
import view.MainMenu;


public class TimeTaskClient extends Application {
    private static ClientSocket clientSocket = new ClientSocket();

    public static ClientSocket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Time Task Manager");
        stage.setResizable(true);

        LoginForm loginForm = new LoginForm();

        stage.setScene(loginForm.getScene());
        stage.getIcons().add(
                new Image(TimeTaskClient.class.getResourceAsStream("/icons/TimeTaskIcon.png")));

        MainMenu.setPrimaryStage(stage);
        MainMenu.getPrimaryStage().show();
    }

    @Override
    public void stop() throws Exception {
        if (MainMenu.getUser() != null) {
            TimeTaskClient.getClientSocket().sendMessage(MainMenu.getUser(), MessageType.UPDATE_MESSAGE);
        }
    }
}
