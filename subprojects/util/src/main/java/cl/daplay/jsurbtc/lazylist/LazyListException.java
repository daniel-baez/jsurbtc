package cl.daplay.jsurbtc.lazylist;

import java.io.PrintStream;
import java.io.PrintWriter;

public final class LazyListException extends IndexOutOfBoundsException {

    private final Exception e;

    public LazyListException(final Exception e) {
        this.e = e;
    }

    @Override
    public String getMessage() {
        return e.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return e.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return e.getCause();
    }

    @Override
    public synchronized Throwable initCause(final Throwable cause) {
        return e.initCause(cause);
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public void printStackTrace() {
        e.printStackTrace();
    }

    @Override
    public void printStackTrace(final PrintStream s) {
        e.printStackTrace(s);
    }

    @Override
    public void printStackTrace(final PrintWriter s) {
        e.printStackTrace(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return e.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return e.getStackTrace();
    }

    @Override
    public void setStackTrace(final StackTraceElement[] stackTrace) {
        e.setStackTrace(stackTrace);
    }
}
