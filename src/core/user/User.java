package core.user;

import core.manager.VenueManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class User {

    private final String username;
    private String phoneNumber, emailId;
    protected VenueManager venueManager;

    public User(String username, String phoneNumber, String emailId) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        venueManager = new VenueManager();
    }

    public String getUsername() {
        return username;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return venueManager.updateUserDatabase(this);
    }

    public boolean setEmailId(String emailId) {
        this.emailId = emailId;
        return venueManager.updateUserDatabase(this);
    }

    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected String getEmailId() {
        return emailId;
    }

    public Map<String, String> getPersonalDetails(){
        return new LinkedHashMap(){
            {
                put("Username", username);
                put("Email Id", emailId);
                put("Phone Number", phoneNumber);
            }
        };
    }

}