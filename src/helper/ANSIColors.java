package helper;

public enum ANSIColors {
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),

    YELLOW_UNDERLINED("\033[4;33m"),

    RESET("\033[0m");

    private final String value;

    ANSIColors(String s) {
        this.value = s;
    }

    @Override
    public String toString(){
        return value;
    }
}

// Reference Links:
// https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797
// https://www.w3schools.blog/ansi-colors-java