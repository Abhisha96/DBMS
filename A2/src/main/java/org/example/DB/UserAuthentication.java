package org.example.DB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.example.DB.Main.USER_FILE_PATH;

public class UserAuthentication {
    private Map<String, User> users = new HashMap<>();
    public UserAuthentication() {
    }

    /**
     *
     * @param user passed the object of user and added it to Map users with key as Id and value as User object
     */
    public void addUser(User user) {
        users.put(user.getID(), user);
    }

    /**
     * save the passed user object values to file
     */
    public void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH,true))) {
            for (User user : users.values()) {
                writer.write(user.getID() + "," + user.getPassword() + "," + user.getSecurityQuestion() + ","
                        + user.getSecurityAnswer());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save user information to file.");
            e.printStackTrace();
        }
    }

    /**
     * checks the id and password match. If it matches it asks for security q and security answer
     * @param ID userinput id
     * @param password userinput password
     * @return
     */
    public boolean validateCredentials(String ID, String password) {
        User user = users.get(ID);
        String pass = user.getPassword();
        String comparepass = password;
        System.out.println(pass);
        System.out.println(comparepass);
        Boolean value = user.getPassword().equals(password);
        System.out.println(value);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * taking id as the key, security answer is validated
     * @param ID userinput id
     * @param answer userinput password
     * @return
     */
    public boolean validateSecurityAnswer(String ID, String answer) {
        User user = users.get(ID);
        return user != null && user.getSecurityAnswer().equals(answer);
    }

    /**
     *
     * @param ID id - as a key passed to retrieve securityanswer
     * @return returns the security answer that is in Id as key
     */
    public String getSecurityQuestion(String ID){
        User user = users.get(ID);
        return user.getSecurityQuestion();
    }
}
