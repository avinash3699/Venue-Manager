import java.util.Scanner;

public class VenueManager {

    //methods

    // method to get username and password from the user and authenticate the user
    void authenticate(){

            String username = getStringInput("Enter username: ");
            String password = getStringInput("Enter password: ");

            try{
                System.out.println("\nAuthenticating....");
                Authenticator.authenticate(
                        username,
                        password
                );
                System.out.println("Authentication Successful!!\n");
            }
            catch (AuthenticationException e) {
                // Displaying a common message would be of better security
                // https://stackoverflow.com/questions/14922130/which-error-message-is-better-when-users-entered-a-wrong-password
                System.out.println("You have entered an invalid username or password. Please try again\n");
            }
    }



    //functions

    // function to get string input with a hint text
    private static String getStringInput(String text){
        System.out.print(text);
        return new Scanner(System.in).nextLine();
    }

}
