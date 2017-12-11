package cl.daplay.jsurbtc.signers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import cl.daplay.jsurbtc.Signer;

public enum DoNothingSigner implements Signer {
    INSTANCE;

	public String sign(final String body,
                       final String method,
                       final String path, 
                       long nonce) throws InvalidKeyException, NoSuchAlgorithmException {
        return "";
    }

}
