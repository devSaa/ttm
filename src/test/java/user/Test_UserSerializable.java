package user;

import entity.Task;
import entity.User;
import message.Message;
import message.MessageType;
import model.UserSerializable;
import network.ClientSocket;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Test_UserSerializable {
    @Before
    public void setUp() {
        User user = new User("Andrey", "1234");
        UserSerializable userSerializable = new UserSerializable();
        userSerializable.saveUserOnDisc(user);
    }

    @Test
    public void test_readUserOnDisc() {
        UserSerializable userSerializable = new UserSerializable();
        User user = userSerializable.readUserOnDisc();
        User userExpected = new User("Andrey", "1234");
        assertEquals(userExpected, user);
    }

    @Test
    public void test_HightLoad() {
        for (int i = 0; i < 50; i++) {
            User user = new User("Andrey" + i, "1234" + i);
            UserSerializable userSerializable = new UserSerializable();
            userSerializable.saveUserOnDisc(user);

            userSerializable = new UserSerializable();
            User userReal = userSerializable.readUserOnDisc();
            User userExpected = new User("Andrey" + i, "1234" + i);
            assertEquals(userExpected, userReal);
        }
    }

    @Test
    public void test_UpdateUser() {
        User user = new User("Anton123", "12345");
        user.setId(20);
        List<Task> list = new ArrayList<Task>();
        list.add(new Task());
        user.setTaskList(list);
        user.setTaskListVersion(5L);
        ClientSocket socket = new ClientSocket();
        Message responseMessage = socket.sendMessage(user, MessageType.UPDATE_MESSAGE);
        Message messageExpected;
    }
}
