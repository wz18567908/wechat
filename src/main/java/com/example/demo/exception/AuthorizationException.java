package com.example.demo.exception;

public class AuthorizationException extends Exception {
    private static final long serialVersionUID = -4929280785105915515L;

    public AuthorizationException(String msg) {
        super(msg);
    }

    public AuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }

}
