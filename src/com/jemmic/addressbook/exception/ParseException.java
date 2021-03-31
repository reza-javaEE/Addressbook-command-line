package com.jemmic.addressbook.exception;

/**
 * Alert that the user input could not be parsed.
 */
public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}

