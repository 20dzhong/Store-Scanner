package StoreScanner.utils;


/**
 * This is an absolutely horrendous showcase of my laziness and brute force
 * Since there is no global variable in Java, instead of trying to figure out how to make the program work without
 * global variables, I made this.
 */
public final class Variable {
    private Variable() {
    }

    public static boolean scanRunning = false;
    public static boolean repainted = false;
    public static ID id = new ID();

    public static String errorLog = "";
}
