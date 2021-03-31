package com.jemmic.addressbook.exception;

/**
 * indicate some error while trying to read/write data between the application
 * and the file.
 */
public class StorageOperationException extends Exception {
    public StorageOperationException(String message) {
        super(message);
    }
}

