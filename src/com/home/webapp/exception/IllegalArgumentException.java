package com.home.webapp.exception;

public class IllegalArgumentException extends StorageException {
    public IllegalArgumentException() {
        super("Error: Method argument is not valid","IllegalArgument");
    }
}
