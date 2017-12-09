package cl.daplay.jsurbtc;

import static java.lang.String.format;

/**
 * Represents API Exceptions
 */
public final class JSurbtcException extends Exception {

    public static class Detail {

        public final String resource;
        public final String field;
        public final String code;
        public final String message;

        public Detail(final String resource, final String field, final String code, final String message) {
            this.resource = resource;
            this.field = field;
            this.code = code;
            this.message = message;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Detail{");
            sb.append("resource='").append(resource).append('\'');
            sb.append(", field='").append(field).append('\'');
            sb.append(", code='").append(code).append('\'');
            sb.append(", message='").append(message).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public final int httpStatusCode;
    public final String message;
    public final String code;
    public final Detail[] details;

    public JSurbtcException(String message) {
        super(message);
        this.httpStatusCode = 0;
        this.message = message;
        this.code = "";
        this.details = new Detail[0];
    }

    public JSurbtcException(final int httpStatusCode, final String message, final String code, final Detail[] details) {
        super(message(httpStatusCode, message, code));
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.details = details;
    }

    public JSurbtcException(final int httpStatusCode, final Throwable cause, final String message, final String code, final Detail[] details) {
        super(message(httpStatusCode, message, code), cause);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.details = details;
    }

    public JSurbtcException(final int httpStatusCode, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace, final String message, final String code, final Detail[] details) {
        super(message(httpStatusCode, message, code), cause, enableSuppression, writableStackTrace);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.details = details;
    }

    private static String message(final int httpStatusCode, final String message, final String code) {
        return format("Surbtc request failed. http status code: %d,%n message: '%s',%n code: '%s'", httpStatusCode, message, code);
    }
}
