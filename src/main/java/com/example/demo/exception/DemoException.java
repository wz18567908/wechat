package com.example.demo.exception;

public class DemoException extends Exception {
    private static final long serialVersionUID = -4929280785105915515L;

    public DemoException(String msg) {
        super(msg);
    }

    public DemoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DemoException(Throwable cause) {
        super(cause);
    }

}
