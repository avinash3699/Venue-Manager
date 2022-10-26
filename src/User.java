import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

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

    public User getDetails(){
        return this;
    }

}