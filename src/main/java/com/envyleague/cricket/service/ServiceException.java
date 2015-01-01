package com.envyleague.cricket.service;

public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
