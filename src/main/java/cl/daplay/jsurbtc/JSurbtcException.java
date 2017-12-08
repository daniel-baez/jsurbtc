package cl.daplay.jsurbtc;

import static java.lang.String.format;

/**
 * Represents API Exceptions
 */
public final class JSurbtcException extends Exception {

    public static class Error {

        public final String resource;
        public final String field;
        public final String code;
        public final String message;

        public Error(final String resource, final String field, final String code, final String message) {
            this.resource = resource;
            this.field = field;
            this.code = code;
            this.message = message;
        }
    }

    public final int httpStatusCode;
    public final String message;
    public final String code;
    public final Error[] errors;

    public JSurbtcException(String message) {
        super(message);
        this.httpStatusCode = 0;
        this.message = message;
        this.code = "";
        this.errors = new Error[0];
    }

    public JSurbtcException(final int httpStatusCode, final String message, final String code, final Error[] errors) {
        super(message(httpStatusCode, message, code));
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public JSurbtcException(final int httpStatusCode, final Throwable cause, final String message, final String code, final Error[] errors) {
        super(message(httpStatusCode, message, code), cause);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public JSurbtcException(final int httpStatusCode, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace, final String message, final String code, final Error[] errors) {
        super(message(httpStatusCode, message, code), cause, enableSuppression, writableStackTrace);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    private static String message(final int httpStatusCode, final String message, final String code) {
        return format("Surbtc request failed. http status code: %d,%n message: '%s',%n code: '%s'", httpStatusCode, message, code);
    }
}
