package helper;

import java.util.regex.Pattern;

public class ValidationHelper {

    public static boolean isValidName(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.matches("[^0]\\d{9}", phoneNumber);
    }

    public static boolean isValidEmailId(String emailId){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(
                  regexPattern,
                  emailId
                );
    }

}