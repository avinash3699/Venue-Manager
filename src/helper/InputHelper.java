package helper;

import database.Database;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class InputHelper {

    // function to get string input with a hint text
    public static String getStringInput(String text){
        System.out.print(text);
        return new Scanner(System.in).nextLine();
    }

    public static int getAccessIdInput() {
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

    public static int getVenueCodeInput(String hintText) {
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

    public static int getIntegerInput(String hintText) {

        int choice;

        while (true) {
            try {
                choice = Integer.parseInt(getStringInput(hintText));
                break;
            } catch (NumberFormatException e) {
//                e.printStackTrace();
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

    public static LocalDate getDateInput(String hintText){
        String date;
        LocalDate parsedDate;
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        while(true) {
            System.out.println("\n---------");
            date = getStringInput(hintText);
            try{
                // ResolverStyle.STRICT is used to throw exception for 29th and 30th of February, considering leap year
                // https://gist.github.com/MenoData/da0aa200b8df31a1d308ad61587a94e6
                parsedDate = LocalDate.parse(date, pattern.withResolverStyle(ResolverStyle.STRICT));

                boolean isPastDate = DateHelper.isPastDate(parsedDate);
                if(isPastDate) {
                    System.out.println("OOPs! You have entered a past date. Please enter a valid one");
                    continue;
                }

                boolean isCurrentDate = DateHelper.isCurrentDate(parsedDate);
                if(isCurrentDate){
                    System.out.println("OOPs! You cannot enter current date. Please enter again");
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
