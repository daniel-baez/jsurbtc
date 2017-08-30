package cl.daplay.jsurbtc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Utils_UT {

    @Test
    public void test_isEmpty() {
        assertTrue(Utils.isEmpty(""));
        assertTrue(Utils.isEmpty(" "));
        assertTrue(Utils.isEmpty("    "));
        assertTrue(Utils.isEmpty("\t\t\t"));
        assertTrue(Utils.isEmpty("\n\t\t"));
    }

}
