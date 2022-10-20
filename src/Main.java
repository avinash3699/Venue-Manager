import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Main {

    static Scanner sc;

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        int choice;
        LocalDate from, to;

        VenueManager venueManager = new VenueManager();

        // logging in the user
        //venueManager.authenticate();
//        System.out.println(Database.getInstance().venues);

        while(true) {
            System.out.println("\n---------");

            System.out.println(Choices.userChoices);

            choice = getIntegerInput("Enter choice: ");
            switch (choice) {

                case 0:
                    while (true) {
                        System.out.println("\n---------");
                        System.out.println(Choices.displayVenueChoices);

                        choice = getIntegerInput("Enter choice: ");

                        System.out.println("\n---------");
                        switch (choice) {
                            case 1:
                                venueManager.displayVenueDetails();
                                break;
                            case 2:
                                venueManager.displayVenueDetails("Conference");
                                break;
                            case 3:
                                venueManager.displayVenueDetails("Hands-On training");
                                break;
                            case 4:
                                venueManager.displayVenueDetails("Auditorium");
                                break;
                            default:
                                System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                                continue;
                        }
                        break;
                    }
                    break;

                case 1:

//                    from = getDateInput("From Date (DD-MM-YYYY): ");
//                    to = getDateInput("To Date (DD-MM-YYYY): ");

                    LocalDate[] dates = getFromToDates();
                    from = dates[0]; to = dates[1];

                    while(true) {
                        System.out.println("\n---------");
                        System.out.printf("From: %s\nTo: %s\n\n", from, to);
                        System.out.println(Choices.checkAvailabilityChoices);

                        choice = getIntegerInput("Enter choice: ");
                        switch (choice) {
                            case 1:
                                System.out.println(venueManager.checkAvailability(from, to));
                                break;
                            case 2:
                                System.out.println(venueManager.checkAvailability("Conference", from, to));
                                break;
                            case 3:
                                System.out.println(venueManager.checkAvailability("Hands-On training", from, to));
                                break;
                            case 4:
                                System.out.println(venueManager.checkAvailability("Auditorium", from, to));
                                break;
                            case 5:
                                int venueCode = getVenueCodeInput("Enter Venue Code: ");
                                System.out.println(venueManager.checkAvailability(venueCode, from, to));
                                break;
                            default:
                                System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                                continue;
                        }
                        break;
                    }
                    break;
                case 2:

//                    while(true) {
//                        from = getDateInput("From Date (DD-MM-YYYY): ");
//                        to = getDateInput("To Date (DD-MM-YYYY): ");
//
//                        if(to.isBefore(from))
//                            System.out.println("You have enter a To date less than From to. Please try again");
//                        else
//                            break;
//                    }
                    dates = getFromToDates();
                    from = dates[0]; to = dates[1];

                    while(true) {
                        System.out.println("\n---------");
                        System.out.printf("From: %s\nTo: %s\n\n", from, to);

                        System.out.println(Choices.reserveHallChoices);

                        choice = getIntegerInput("Enter choice: ");
                        switch (choice) {
                            case 1:
                                venueManager.reserveVenue("Conference", from, to);
                                break;
                            case 2:
                                venueManager.reserveVenue("Hands-On training", from, to);
                                break;
                            case 3:
                                venueManager.reserveVenue("Auditorium", from, to);
                                break;
                            case 4:
                                int venueCode = getVenueCodeInput("Enter Venue Code: ");
                                venueManager.reserveVenue(venueCode, from, to);
                                break;
                            default:
                                System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                                continue;
                        }
                        break;
                    }
                    break;
                case 3:

                    int accessId = getAccessIdInput(),
                        venueCode = getVenueCodeInput("Enter Venue Code: ");

                    while(true) {
                        System.out.println("\n---------");
                        System.out.println(Choices.cancelHallChoices);

                        choice = getIntegerInput("Enter choice: ");
                        switch (choice) {
                            case 1:
                                venueManager.cancelVenue(venueCode, accessId);
                                break;
                            case 2:
//                                from = getDateInput("From Date (DD-MM-YYYY): ");
//                                to = getDateInput("To Date (DD-MM-YYYY): ");
                                dates = getFromToDates();
                                from = dates[0]; to = dates[1];

                                venueManager.cancelVenue(venueCode, accessId, from, to);
                                break;
                            case 3:
//                                String date = VenueManager.getStringInput("Enter the date to be cancelled(DD-MM-YYYY): ");
//                                LocalDate dateToBeCancelled = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                LocalDate dateToBeCancelled = getDateInput("Enter the date to be cancelled(DD-MM-YYYY): ");
                                venueManager.cancelVenue(venueCode, accessId, dateToBeCancelled);
                                break;
                            default:
                                System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                                continue;
                        }
                        break;
                    }
                    break;
                case 4:
                    accessId = getAccessIdInput();
                    int oldVenueCode = getVenueCodeInput("Enter Venue Code: "),
                        newVenueCode = getVenueCodeInput("Enter New Venue Code: ");
                    venueManager.changeVenue(oldVenueCode, accessId, newVenueCode);
                    break;
                default:
                    System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
            }
        }

//        Venue confRoom = new ConferenceRoom();
//        System.out.println(confRoom.getVenueDetails());
    }

    private static int getAccessIdInput() {
        int accessId;
        System.out.println("\n---------");

        while(true) {
            accessId = getIntegerInput("Enter Access Id: ");
            if((Database.getInstance().getAccessIds()).contains(accessId))
                break;
            else
            {
                System.out.println("\n---------");
                System.out.println("OOPs! Invalid Access Id. Please try again!\n");
            }
        }
        return accessId;
    }

    private static int getVenueCodeInput(String hintText) {
        int venueCode;
        System.out.println("\n---------");

        while(true) {
            venueCode = getIntegerInput(hintText);
            if(venueCode <= Database.getInstance().getVenuesCount())
                break;
            else{
                System.out.println("\n---------");
                System.out.println("OOPs! Invalid Venue Code. Please try again!\n");
            }
        }
        return venueCode;
    }

    private static int getIntegerInput(String hintText) {

        int choice;

        while (true) {
            try {
                choice = Integer.parseInt(VenueManager.getStringInput(hintText));
                break;
            } catch (NumberFormatException e) {
//                e.printStackTrace();
                System.out.println("\n---------");
                System.out.println("OOPs! Invalid Input, please enter a valid number\n");
            }
        }

        return choice;
    }

    private static LocalDate getDateInput(String hintText){
        String date;
        LocalDate parsedDate;
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        while(true) {
            System.out.println("\n---------");
            date = VenueManager.getStringInput(hintText);
            try{
                // ResolverStyle.STRICT is used to throw exception for 29th and 30th of February, considering leap year
                // https://gist.github.com/MenoData/da0aa200b8df31a1d308ad61587a94e6
                parsedDate = LocalDate.parse(date, pattern.withResolverStyle(ResolverStyle.STRICT));
                boolean isPastDate = isDatePast(parsedDate);
                if(isPastDate) {
                    System.out.println("OOPs! You have entered a past date. Please enter a valid one");
                    continue;
                }
                break;
            }
            catch (DateTimeParseException e){
                System.out.println("OOPs! Invalid Date, please enter a valid date");
            }
        }
        return parsedDate;
    }

    private static LocalDate[] getFromToDates(){
        while(true) {
            LocalDate from = getDateInput("From Date (DD-MM-YYYY): "),
                      to = getDateInput("To Date (DD-MM-YYYY): ");

            if(to.isBefore(from))
                System.out.println("You have enter a 'To date' less than 'From to'. Please try again");
            else if(to.isAfter(from.plusDays(10)))
                System.out.println("Your from and to date cannot be not be more than 10 days");
            else
                return new LocalDate[]{
                        from,
                        to
                };
        }
    }

    private static boolean isDatePast(LocalDate inputDate) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        return inputDate.isBefore(localDate);
    }

//    static private LocalDate[] getInputAndParsedDate(){
//
//        LocalDate[] parsedDates;
//
//        while (true) {
//            System.out.println("\n---------");
//            String fromDate = VenueManager.getStringInput("From Date (DD-MM-YYYY): "),
//                    toDate = VenueManager.getStringInput("To Date (DD-MM-YYYY): ");
//
//            try {
//                parsedDates = new LocalDate[]{
//                        LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
//                        LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
//                };
//                break;
//            }
//            catch (DateTimeParseException e) {
////                e.printStackTrace();
//                System.out.println("OOPs! Invalid Date, please enter valid from and to dates\n");
//            }
//        }
//
//        return parsedDates;
//    }

}