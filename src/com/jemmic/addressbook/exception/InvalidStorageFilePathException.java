package com.jemmic.addressbook.exception;


/**
 * Alert that the given file path does not valid.
 */
public class InvalidStorageFilePathException extends IllegalValueException {
    public InvalidStorageFilePathException(String message) {
        super(message);
    }
}