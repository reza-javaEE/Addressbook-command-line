package com.jemmic.addressbook.file;

import com.jemmic.addressbook.exception.IllegalValueException;
import com.jemmic.addressbook.exception.StorageOperationException;
import com.jemmic.addressbook.model.AddressBook;
import com.jemmic.addressbook.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.jemmic.addressbook.parser.Parser.PERSON_DATA_ARGS_FORMAT;


/**
 * Decode the file into an {@code AddressBook} object.
 */
public class AddressBookDecoder {

    /**
     * Decodes {@code encodedAddressBook} into an {@code AddressBook} object.
     *
     * @throws IllegalValueException     if any of the fields is invalid.
     * @throws StorageOperationException if the {@code encodedAddressBook} is in an invalid format.
     */
    public static AddressBook decodeAddressBook(List<String> encodedAddressBook)
            throws IllegalValueException, StorageOperationException {
        final List<Person> decodedPersons = new ArrayList<>();
        for (String encodedPerson : encodedAddressBook) {
            decodedPersons.add(decodePersonFromString(encodedPerson));
        }
        return new AddressBook(decodedPersons);
    }

    /**
     * Decodes {@code encodedPerson} into a {@code Person}.
     *
     * @throws IllegalValueException     if any field in the {@code encodedPerson} is invalid.
     * @throws StorageOperationException if {@code encodedPerson} is in an invalid format.
     */
    private static Person decodePersonFromString(String encodedPerson)
            throws IllegalValueException, StorageOperationException {
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(encodedPerson);
        if (!matcher.matches()) {
            throw new StorageOperationException("Encoded person in invalid format. Unable to decode.");
        }

        return new Person(
                matcher.group("name"),
                matcher.group("surname"),
                matcher.group("phone"),
                matcher.group("email"),
                matcher.group("age"),
                matcher.group("hairColor"),
                matcher.group("categoryName"),
                matcher.group("relationship"),
                matcher.group("friendshipYear")

        );
    }

}
