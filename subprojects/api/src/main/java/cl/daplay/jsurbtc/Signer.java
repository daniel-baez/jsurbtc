package cl.daplay.jsurbtc;


public interface Signer {

	String sign(final String body, final String method, final String path, long nonce) throws Exception;

}
