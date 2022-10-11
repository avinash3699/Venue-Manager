import java.util.Scanner;

public class Main {

    static Scanner sc;

    public static void main(String[] args) {

        sc = new Scanner(System.in);

        VenueManager venueManager = new VenueManager();

        loginExitLoop:
        while(true) {
            System.out.println(Choices.loginExitChoice);

            switch (sc.nextInt()) {
                case 1:


                    venueManager.authenticate();
                    break loginExitLoop;
                case 2:
                    return;
            }
        }

        System.out.println(Choices.userChoices);

    }





}