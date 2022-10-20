public class User {

    final String username, emailId;
    ContactPerson contactPerson;

    public User(String username, String emailId, String contactPersonName, String contactPersonEmailId, String contactPersonPhoneNumber) {
        this.username = username;
        this.emailId = emailId;
        contactPerson = new ContactPerson(contactPersonName, contactPersonEmailId, contactPersonPhoneNumber);
    }

}
