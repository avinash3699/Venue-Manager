package helper;

import java.time.LocalDate;
import java.time.ZoneId;

public class DateHelper {

    // function to check whether the entered date is past date or not
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

}
