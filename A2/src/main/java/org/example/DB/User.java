package org.example.DB;

/*
To create an object of user with all the parameters
 */
public class User {
    private String ID;
    private String password;
    private String securityQuestion;
    private String securityAnswer;

    /**
     *
     * @param Id Initializes Id passed while creating the object of the Id
     * @param password Initializes password passed while creating the object of the user
     * @param securityQuestion Initializes securityQuestion passed while creating the object of the user
     * @param securityAnswer Initializes securityAnswer passed while creating the object of the user
     */
    public User(String Id, String password,String securityQuestion,String securityAnswer){
        this.ID = Id;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    /**
     *
     * @param ID sets the userInput value of Id
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     *
     * @param password sets the userInput value of Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param securityQuestion sets the userInput value of securityQuestion
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     *
     * @param securityAnswer sets the userInput value of securityAnswer
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     *
     * @return string ID
     */
    public String getID() {
        return ID;
    }

    /**
     *
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return String securityQuestion
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     *
     * @return string securityAnswer
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

}
