package org.example.DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    static final String USER_FILE_PATH = "C://Users//AVuser//csci5408_s23_b00937694_abhisha_thaker//A2//userInfo.txt";

    public static void main(String[] args) {
        UserAuthentication userAuth = new UserAuthentication();
        getUserFromConsole(userAuth);
    }

    /**
     * This method checks if the Id already exists or not in the file. If the Id exists, then it authenticates against
     * the saved details in the file and allows the user to create db, create table, insert values,
     * delete row, and update table
     * If the Id doesn't exist, then it stores the details in the file.
     * @param userAuth An object of userAuthentication is passed
     */
    private static void getUserFromConsole(UserAuthentication userAuth) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID: ");
        String ID = scanner.nextLine();

        if (isIDExists(ID)) {
            loadUsersFromFile(userAuth);
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.println(hashPassword(password));

            if (userAuth.validateCredentials(ID, hashPassword(password))) {
                System.out.println("Credentials validated.");
                System.out.print("Answer the security question: " + userAuth.getSecurityQuestion(ID) + ": ");
                String answer = scanner.nextLine();

                if (userAuth.validateSecurityAnswer(ID, answer)) {
                    System.out.println("Authentication successful. Welcome, " + ID + "!");
                    System.out.println("Enter the query");
                    String query = scanner.nextLine();

                    System.out.println("Choose the option of the query type that you want to write" +
                            "out of the following options" + "" +
                            "1. CREATE DATABASE" +
                            "2. CREATE TABLE" +
                            "3. INSERT INTO TABLE" +
                            "4. SELECT FROM TABLE" +
                            "5. UPDATE THE ROW" +
                            "6. DELETE THE ROW");
                    int choiceQuery = scanner.nextInt();
                    String dbName = new String();
                    Query q = new Query();
                        switch (choiceQuery) {
                            case 1:
                            {
                                dbName = q.createDB(query);
                                System.out.println(dbName);
                                break;
                            }
                            case 2:{
                                System.out.println("Enter the database name");
                                String choiceDb = scanner.next();
                                q.createTables(query,choiceDb);
                                break;
                            }
                            case 3:
                            {
                                System.out.println("Enter the database name");
                                String choiceDb = scanner.next();
                                q.insertValues(query,choiceDb);
                                break;
                            }
                            case 4:{
                                System.out.println("Enter the database name");
                                String choiceDb = scanner.next();
                                q.selectValues(query,choiceDb);
                                break;
                            }
                            case 5:{
                                System.out.println("Enter the database name");
                                String choiceDb = scanner.next();
                                q.updateValues(query,choiceDb);
                                break;
                            }
                            case 6:{
                                System.out.println("Enter the database name");
                                String choiceDb = scanner.next();
                                q.deleteValues(query,choiceDb);
                                break;
                            }
                            default:{
                                System.out.println("please specify a proper value between 1 to 6");
                                break;
                            }
                        }
                } else {
                    System.out.println("Incorrect security answer. Authentication failed.");
                }
            } else {
                System.out.println("Invalid credentials. Authentication failed.");
            }
        }
        else
            {
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                System.out.print("Enter security question: ");
                String securityQuestion = scanner.nextLine();

                System.out.print("Enter security answer: ");
                String securityAnswer = scanner.nextLine();
                User user = new User(ID, hashPassword(password), securityQuestion, securityAnswer);
                userAuth.addUser(user);
                userAuth.saveUsersToFile();
            }
    }

    /**
     *
     * @param password This method takes the useriNput password and hashes it and stores the hashed password
     *                 in the file
     * @return
     */
    static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param ID This method takes userInput Id as the parameter value and checks if the Id exists in the file
     *           or not
     * @return If the ID exists, then return value  - true. If Id doesn't exist, return value false.
     */
    public static boolean isIDExists(String ID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String fileID = userInfo[0];
                if (fileID.equals(ID)) {
                    return true; // ID exists in the file
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read user information from file.");
            e.printStackTrace();
        }
        return false; // ID does not exist in the file
    }

    /**
     * It retrives the saved user info already saved in the file.
     * @param userAuth passes an object of userAuthentication
     */
    private static void loadUsersFromFile(UserAuthentication userAuth) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String ID = userInfo[0];
                String password = userInfo[1];
                String securityQuestion = userInfo[2];
                String securityAnswer = userInfo[3];
                User user = new User(ID, password, securityQuestion, securityAnswer);
                userAuth.addUser(user);
            }
        } catch (IOException e) {
            System.out.println("Failed to load user information from file.");
            e.printStackTrace();
        }
    }
}