package com.audieni.ers.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Email or password is invalid.");
    }
}
