package launcher;

import javafx.application.Application;
import menu.TimeTaskClient;
import network.Synchronization;

public class Launcher {
    public static void main(String[] args) {
        Synchronization synchronization = new Synchronization();
        Thread newThready = new Thread(synchronization);
        newThready.setDaemon(true);
        newThready.start();

        Application.launch(TimeTaskClient.class, args);
    }
}
