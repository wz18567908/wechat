package com.example.demo.exception;

public class AuthenticationException extends Exception {
    private static final long serialVersionUID = -6653141509849725362L;

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

}
