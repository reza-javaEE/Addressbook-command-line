package com.jemmic.addressbook.exception;

/**
 * Alert that the person was not found.
 */
public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(String message) {
        super(message);
    }
}