package cl.daplay.jsurbtc;

import java.io.Reader;
import java.util.Scanner;

public final class Utils {

    private Utils() {}

    public static boolean isEmpty(String cs) {
        return cs == null || cs.trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String convertStreamToString(java.io.InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String convertStreamToString(Reader is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
