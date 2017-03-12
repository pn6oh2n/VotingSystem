package com.example.exception;

public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String[] details;

    public String getUrl() {
        return url;
    }

    public String getCause() {
        return cause;
    }

    public String[] getDetails() {
        return details;
    }

    public ErrorInfo(CharSequence url, Throwable ex) {
        this(url, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }

    public ErrorInfo(CharSequence url, String cause, String... details) {
        this.url = url.toString();
        this.cause = cause;
        this.details = details;
    }
}
