package helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    // function to check whether the entered date is a past date or not
    // returns 'true', if past date. else, 'false'
    public static boolean isPastDate(LocalDate inputDate) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        return inputDate.isBefore(localDate);
    }

    // function to check whether the entered date is current date or not
    // returns 'true', if current date. else, 'false'
    public static boolean isCurrentDate(LocalDate inputDate) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        return inputDate.isEqual(localDate);
    }

    // function that formats a date into "dd-MM-yyyy" format
    // called during the printing of dates
    public static String getFormattedDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

}
