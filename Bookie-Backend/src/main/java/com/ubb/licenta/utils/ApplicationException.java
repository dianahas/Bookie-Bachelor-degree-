package com.ubb.licenta.utils;

public class ApplicationException extends Exception {
    public ApplicationException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ApplicationException(String message) {
        super(message);
    }
}
