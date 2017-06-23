package model;

import entity.User;

import java.io.*;

public class UserSerializable {

    /**
     * Save user in file
     *
     * @param user
     */
    public void saveUserOnDisc(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src\\main\\java\\cache\\User.ser"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read user of file
     *
     * @return User
     */
    public User readUserOnDisc() {
        User user;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src\\main\\java\\cache\\User.ser"))) {
            user = (User) ois.readObject();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
