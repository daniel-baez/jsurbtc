package cl.daplay.jsurbtc.model;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public JSurbtcException() {
        super();
        this.httpStatusCode = 0;
        this.message = "";
        this.code = "";
        this.details = new Detail[0];
    }

    public JSurbtcException(String message) {
        super(message);
        this.httpStatusCode = 0;
        this.message = message;
        this.code = "";
        this.details = new Detail[0];
    }

    public JSurbtcException(final int httpStatusCode, final String message, final String code, final Detail[] details) {
        super(message(httpStatusCode, message, code, details));
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.details = details;
    }

    public JSurbtcException(final int httpStatusCode, final Throwable cause, final String message, final String code, final Detail[] details) {
        super(message(httpStatusCode, message, code, details), cause);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.details = details;
    }

    public JSurbtcException(final int httpStatusCode, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace, final String message, final String code, final Detail[] details) {
        super(message(httpStatusCode, message, code, details), cause, enableSuppression, writableStackTrace);
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
        this.details = details;
    }

    private static String message(final int httpStatusCode, final String message, final String code, final Detail[] details) {
        String detailsString = Arrays.stream(details)
                .map(Object::toString)
                .map(str -> " - " + str)
                .collect(Collectors.joining("\n"));

        String template = "Surbtc request failed. %n HTTP Status Code: %d,%n Message: '%s',%n Code: '%s',%n Details:%n%s";

        return format(template, httpStatusCode, message, code, detailsString);
    }
}
