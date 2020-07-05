package com.esbt.streaming.app.exception;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {

    private HttpStatus httpStatus;
    private String userMessage;
    private String code;
    private String[] prms;

    public GlobalException(String userMessage) {
        super(userMessage);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.userMessage = userMessage;
    }

    public GlobalException(HttpStatus httpStatus, String userMessage) {
        super(userMessage);
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
    }

    public GlobalException(HttpStatus httpStatus, String userMessage, String code, String... prms) {
        super(userMessage);
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
        this.code = code;
        this.prms = prms;
    }

    public GlobalException(HttpStatus httpStatus, String userMessage, Throwable e) {
        super(userMessage, e);
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getCode() {
        return code;
    }

    public String[] getPrms() {
        return prms;
    }
}