import java.util.LinkedHashMap;

public class User {

    private final String username;
    private String phoneNumber, emailId;

    public User(String username, String phoneNumber, String emailId) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public String getUsername() {
        return username;
    }

    public User getDetails(){
        return this;
    }

    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected String getEmailId() {
        return emailId;
    }
}