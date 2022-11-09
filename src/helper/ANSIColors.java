package helper;

public enum ANSIColors {
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    WHITE("\033[0;37m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),

    YELLOW_UNDERLINED("\033[4;33m"),

    YELLOW_BOLD("\033[1;33m"),

    YELLOW_BACKGROUND("\033[43m"),

    YELLOW_BRIGHT("\033[0;93m"),

    RESET("\033[0m");

    private String value;

    ANSIColors(String s) {
        this.value = s;
    }

    @Override
    public String toString(){
        return value;
    }
}
