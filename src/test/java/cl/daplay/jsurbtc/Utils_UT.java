package cl.daplay.jsurbtc;

import org.junit.Assert;
import org.junit.Test;

public class Utils_UT {

    @Test
    public void test_isEmpty() {
        Assert.assertTrue(Utils.isEmpty(""));
        Assert.assertTrue(Utils.isEmpty(" "));
        Assert.assertTrue(Utils.isEmpty("    "));
        Assert.assertTrue(Utils.isEmpty("\t\t\t"));
        Assert.assertTrue(Utils.isEmpty("\n\t\t"));
    }

}
