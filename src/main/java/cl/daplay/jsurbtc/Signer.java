package cl.daplay.jsurbtc;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface Signer {

	public String sign(final String body,
                       final String method,
                       final String path,
                       long nonce) throws Exception;

}
