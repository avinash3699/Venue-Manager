// Utility class to store all the choices list to be displayed to the user

// separated from the Main class to improve the code readability
public class Choices {

    static String loginExitChoice =
            "Welcome to Venue Manager\n" +
            "1. Login\n" +
            "2. Exit\n";

    static String userChoices =
            "Venue Manager Console\n\n" +
            "0. Display different Venue Details\n" +
            "1. Check Venue Availability\n" +
            "2. Reserve Venue\n" +
            "3. Cancel Reserved Venue\n" +
            "4. Change Reserved Venue\n" +
            "5. Show Reserved Venues\n" +
            "6. LOGOUT\n";

    static String adminChoices =
            "Admin Operations\n" +
            "7. Add Users\n" +
            "8. View All Users\n" +
            "9. Update Venues\n";

    static String displayVenueChoices =
            "Display details of\n" +
            "1. All venues\n" +
            "2. Conference Room\n" +
            "3. Hands-On Training Centre\n" +
            "4. Auditorium\n" +
            "5. Specific Venue (requires Venue Code)\n" +
            "6. GO TO VENUE MANAGER CONSOLE\n";

    static String checkAvailabilityChoices =
            "Check availability of\n" +
            "1. All venues\n" +
            "2. Conference Room\n" +
            "3. Hands-On Training Centre\n" +
            "4. Auditorium\n" +
            "5. Specific Venue (requires Venue Code)\n" +
            "6. CHECK ANOTHER DATES\n" +
            "7. GO TO VENUE MANAGER CONSOLE\n";

    static String reserveVenueChoices =
            "Reserve\n" +
            "1. Any Conference Room\n" +
            "2. Any Auditorium\n" +
            "3. Any Hands-on training centre\n" +
            "4. Using Venue Code\n" +
            "5. RESERVE ANOTHER DATES\n" +
            "6. GO TO VENUE MANAGER CONSOLE\n";

    static String cancelVenueChoices =
            "Cancel\n" +
            "1. Entire reservation\n" +
            "2. From-to dates\n" +
            "3. a Day\n" +
            "4. CANCEL WITH ANOTHER ACCESS ID\n" +
            "5. GO TO VENUE MANAGER CONSOLE\n";
}