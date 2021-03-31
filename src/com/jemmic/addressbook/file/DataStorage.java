package com.jemmic.addressbook.file;

import com.jemmic.addressbook.exception.IllegalValueException;
import com.jemmic.addressbook.exception.InvalidStorageFilePathException;
import com.jemmic.addressbook.exception.StorageOperationException;
import com.jemmic.addressbook.model.AddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Presents the file used to store address book data.
 */
public class DataStorage {

    /**
     * Default file path used if the user doesn't provide the file name.
     */
    public static final String DEFAULT_FILEPATH = "addressbook.txt";


    public final Path path;

    /**
     * @throws InvalidStorageFilePathException if the default path is invalid
     */
    public DataStorage() throws InvalidStorageFilePathException {
        this(DEFAULT_FILEPATH);
    }

    /**
     * @throws InvalidStorageFilePathException if the given file path is invalid
     */
    public DataStorage(String filePath) throws InvalidStorageFilePathException {
        path = Paths.get(filePath);
        if (!isValidPath(path)) {
            throw new InvalidStorageFilePathException("Storage file should end with '.txt'");
        }
    }

    /**
     * Returns true if the given path is acceptable as a text file.
     */
    private static boolean isValidPath(Path filePath) {
        return filePath.toString().endsWith(".txt");
    }

    /**
     * Saves the {@code addressBook} data to the storage file.
     *
     * @throws StorageOperationException if there were errors converting or storing data to file.
     */
    public void save(AddressBook addressBook) throws StorageOperationException {
        try {
            List<String> encodedAddressBook = AddressBookEncoder.encodeAddressBook(addressBook);
            Files.write(path, encodedAddressBook);
        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + path);
        }
    }

    /**
     * Loads the {@code AddressBook} data from this file, and then returns it.
     * Returns an empty {@code AddressBook} if the file does not exist, or is not a regular file.
     *
     * @throws StorageOperationException if there were errors reading or converting data from file.
     */
    public AddressBook load() throws StorageOperationException {

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return new AddressBook();
        }
        try {
            return AddressBookDecoder.decodeAddressBook(Files.readAllLines(path));
        } catch (IOException ioException) {
            throw new StorageOperationException("Error reading from file: " + path);
        } catch (IllegalValueException illegalValueException) {
            throw new StorageOperationException("File contains illegal data values");
        }
    }

    public String getPath() {
        return path.toString();
    }

}
