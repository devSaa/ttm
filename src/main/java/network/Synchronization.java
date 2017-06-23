package network;

import entity.User;
import message.MessageType;
import model.UserSerializable;
import view.MainMenu;

/**
 * TimerTask
 */
public class Synchronization implements Runnable {
    @Override
    public void run() {
        int count = 1;
        while (true) {
            try {
                Thread.sleep(1000 * 60); // one in minutes
                UserSerializable uSerializable = new UserSerializable();
                uSerializable.saveUserOnDisc(MainMenu.getUser());

                if ((count % 60) == 0) {
                    // one in hours
                    UserSerializable userSerializable = new UserSerializable();
                    User user = userSerializable.readUserOnDisc();
                    ClientSocket socket = new ClientSocket();
                    socket.sendMessage(user, MessageType.UPDATE_MESSAGE);
                }
                count++;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
