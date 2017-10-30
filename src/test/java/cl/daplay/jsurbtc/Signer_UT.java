package cl.daplay.jsurbtc;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class Signer_UT {

    @Test
    public void asdf() throws NoSuchAlgorithmException, InvalidKeyException {
        final String key = "feaea7e6ffef2e57a53a473624517cee";
        final String secret = "N6Cj0DGvNQJcJzv+x6gq+7GloOls+zylRQTtUsB7";
        final String path = "/api/v2/markets";
        final long nonce = 1509319603729L;

        final String actual = new Signer(secret).sign("", "GET", path, nonce);
        final String expected = "a05a167313fe24cc4ace446b028f0d668842bedf3e40a1387dee9537fa0d244c394b24d0e0ee86681c196fe80d754ab2";

        assertEquals(expected, actual);
    }


}
