package com.lesson3.HomeWork.model;

public class NullFieldsException extends Exception {

    public NullFieldsException() {
        super();
    }

    public NullFieldsException(String message) {
        super(message);
    }

    public NullFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullFieldsException(Throwable cause) {
        super(cause);
    }

    protected NullFieldsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
