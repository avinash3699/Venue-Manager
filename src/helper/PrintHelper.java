package helper;

// This class prints the output to the console in different colors
public class PrintHelper {

    // function to print the output in red color
    public static void printRed(String text){
        System.out.println(
                ANSIColors.RED +
                        text +
                        ANSIColors.RESET
        );
    }

    // function to print the output in green color
    public static void printGreen(String text){
        System.out.println(
            ANSIColors.GREEN +
            text +
            ANSIColors.RESET
        );
    }

    // function to print the output in blue color
    public static void printBlue(String text){
        System.out.println(
                ANSIColors.BLUE +
                        text +
                        ANSIColors.RESET
        );
    }

    // function to print the output in yellow color
    public static void printYellow(String text){
        System.out.println(
            ANSIColors.YELLOW +
            text +
            ANSIColors.RESET
        );
    }

    // function to print the output in yellow color with a underline to it
    public static void printYellowUnderlined(String text){
        System.out.println(
            ANSIColors.YELLOW_UNDERLINED +
            text +
            ANSIColors.RESET
        );
    }

}