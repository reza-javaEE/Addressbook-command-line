package com.jemmic.addressbook.exception;

/**
 * Alert that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends Exception {

    public IllegalValueException(String message) {
        super(message);
    }
}
