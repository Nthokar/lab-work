package org.example.app;
public class CloseMenuException extends RuntimeException{
    public CloseMenuException(String errorMessage) {
        super(errorMessage);
    }
    public CloseMenuException() {
        super((String) null);
    }
}