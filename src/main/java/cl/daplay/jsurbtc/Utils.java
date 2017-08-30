package cl.daplay.jsurbtc;

public final class Utils {

    private Utils() {}

    public static boolean isEmpty(String cs) {
        return cs == null || cs.trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
}
