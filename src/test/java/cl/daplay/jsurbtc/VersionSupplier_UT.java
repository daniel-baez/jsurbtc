package cl.daplay.jsurbtc;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VersionSupplier_UT {

    @Test
    public void test() {
        final String version = VersionSupplier.INSTANCE.get();

        assertFalse(version == null || version.isEmpty());
        assertTrue(version.matches("^\\d+\\.\\d+\\.\\d+.*"));
    }

}
