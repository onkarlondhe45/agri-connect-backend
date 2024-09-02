package com.agriconnect.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("A user with the provided email address (" + email + ") already exists.\nPlease use a different email address or log in to your existing account.");
    }
}
