package helper;

public class PrintHelper {

    public static void printGreen(String text){
        System.out.println(
            ANSIColors.GREEN +
            text +
            ANSIColors.RESET
        );
    }

    public static void printRed(String text){
        System.out.println(
            ANSIColors.RED +
            text +
            ANSIColors.RESET
        );
    }

    public static void printYellow(String text){
        System.out.println(
            ANSIColors.YELLOW +
            text +
            ANSIColors.RESET
        );
    }

    public static void printBlue(String text){
        System.out.println(
            ANSIColors.BLUE +
            text +
            ANSIColors.RESET
        );
    }

    public static void printYellowUnderlined(String text){
        System.out.println(
            ANSIColors.YELLOW_UNDERLINED +
            text +
            ANSIColors.RESET
        );
    }

}