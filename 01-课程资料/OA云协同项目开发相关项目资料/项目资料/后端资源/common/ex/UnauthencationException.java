package org.xdq.demo.common.ex;

public class UnauthencationException extends RuntimeException{

    public UnauthencationException() {
    }

    public UnauthencationException(String message) {
        super(message);
    }

    public UnauthencationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthencationException(Throwable cause) {
        super(cause);
    }

    public UnauthencationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
