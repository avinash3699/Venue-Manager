package helper;

import core.manager.VenueManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

// This class is used to get the inputs from the user
// It validates the input based on the function called
public class InputHelper {

    // function to get string input with a hint text
    // it simply displays the input hint text and get the input, of type string and returns it
    // Additionally, it checks whether the user entered string is valid and a not empty one
    // it prompts the user to enter a valid string, until a one been entered
    public static String getStringInput(String text){
        String input;
        while (true){
            System.out.print(text);
            input = new Scanner(System.in).nextLine();

            // condition checks
            // 1. is empty string?
            if(input.equals("")){
                System.out.println("\n---------");
                System.out.println("Empty input. Please try again\n");
            }
            else{
                break;
            }
        }
        return input;
    }

    // function to get a valid character input for confirmation
    // Valid characters are "Y,y,N,n"
    // it prompts the user to enter a valid character, until a one been entered
    public static char getYesOrNoCharacterInput(String text){
        String input;
        while(true) {
            input = getStringInput(text);

            // condition checks
            // 1. is input length 1?
            // 2. is input character Y/y/N/n?
            if ( (input.length() != 1) || (!"YNyn".contains(input)) ) {
                System.out.println("\n---------");
                System.out.println("Please enter a valid character.(Y/N) \n");
            }
            else
                break;
        }

        return input.charAt(0);

    }

    // function to get a valid venue code
    // the number of venues available is passed to the function
    // returns a valid venue code
    public static int getVenueCodeInput(String hintText) {
        int venueCode;
        System.out.println("\n---------");

        while(true) {
            venueCode = getIntegerInput(hintText);

            if(new VenueManager().isValidVenueCode(venueCode)){
                break;
            }
            else{
                System.out.println("Invalid Venue Code. Please try again\n");
            }
            // condition checks
            // 1. is entered integer positive?
            // 2. is entered integer less than or equal to the available number of venue
        }
        return venueCode;
    }

    // function to get a valid integer input
    public static int getIntegerInput(String hintText) {

        int choice;

        while (true) {
            try {
                choice = Integer.parseInt(getStringInput(hintText));
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n---------");
                System.out.println("OOPs! Invalid Input, please enter a valid number\n");
            }
        }

        return choice;
    }

    public static LocalDate[] getFromToDates(){
        while(true) {
            LocalDate from = getDateInput("From Date (DD-MM-YYYY): "),
                    to = getDateInput("To Date (DD-MM-YYYY): ");

            // condition check
            // whether the 'end date(to)' is behind the 'start date(from)'?
            // reason: the 'to' date should not be behind the 'from' date
            if(to.isBefore(from))
                System.out.println("You have entered a 'To date' less than 'From to'. Please try again");

            // condition check
            // whether the 'end date(to)' is more than 10 days after the 'start date(to)'
            // reason: cannot reserve a venue for more than 10 days
            else if(to.isAfter(from.plusDays(10)))
                System.out.println("Your from and to date cannot be not be more than 10 days");

            else
                return new LocalDate[]{
                    from,
                    to
                };
        }
    }

    public static LocalDate getDateInput(String hintText){
        String date;
        LocalDate parsedDate;

        // uuuu is used in place of yyyy (from Java 8)
        // https://howtodoinjava.com/java/date-time/resolverstyle-strict-date-parsing/
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-uuuu");

        while(true) {
            System.out.println("\n---------");
            date = getStringInput(hintText);
            try{
                // ResolverStyle.STRICT is used to throw exception for 29th and 30th of February, considering leap year
                // https://gist.github.com/MenoData/da0aa200b8df31a1d308ad61587a94e6
                parsedDate = LocalDate.parse(date, pattern.withResolverStyle(ResolverStyle.STRICT));

                // checking whether the entered date is a past date
                boolean isPastDate = DateHelper.isPastDate(parsedDate);
                if(isPastDate) {
                    System.out.println("OOPs! You have entered a past date. Please enter a valid one");
                    continue;
                }

                // checking whether the entered date is current date
                boolean isCurrentDate = DateHelper.isCurrentDate(parsedDate);
                if(isCurrentDate){
                    System.out.println("OOPs! You cannot enter today's date. Please enter again");
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

}
