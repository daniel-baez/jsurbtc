package cl.daplay.jsurbtc.signer;

import cl.daplay.jsurbtc.Signer;

public enum NOOPSigner implements Signer {
    INSTANCE;

	public String sign(final String body,
                       final String method,
                       final String path, 
                       long nonce) {
        return "";
    }

}
