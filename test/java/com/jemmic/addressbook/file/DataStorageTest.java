package com.jemmic.addressbook.file;

import com.jemmic.addressbook.exception.IllegalValueException;
import com.jemmic.addressbook.model.AddressBook;
import com.jemmic.addressbook.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataStorageTest {
    @TempDir
    public static Path testFolder;

    private static final String TEST_DATA_FOLDER = "test/files";

    @Test
    public void constructor_nullFilePath_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new DataStorage(null));
    }

    @Test
    public void constructor_noTxtExtension_exceptionThrown() {
        assertThrows(IllegalValueException.class, () ->
                new DataStorage(TEST_DATA_FOLDER + "/" + "InvalidfileName"));
    }

    @Test
    public void save_nullAddressBook_exceptionThrown() throws Exception {
        DataStorage storage = getTempStorage();
        assertThrows(NullPointerException.class, () -> storage.save(null));
    }


    private DataStorage getTempStorage() throws Exception {
        return new DataStorage(testFolder.resolve("temp.txt").toString());
    }

}
