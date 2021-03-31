package com.jemmic.addressbook.file;


import com.jemmic.addressbook.model.AddressBook;
import com.jemmic.addressbook.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Encodes the {@code AddressBook} object into a file.
 */
public class AddressBookEncoder {

    /**
     * Encodes all the {@code Person} in the {@code toSave} into a list of readable string .
     */
    public static List<String> encodeAddressBook(AddressBook toSave) {
        final List<String> encodedPersons = new ArrayList<>();
        toSave.getPersonList().forEach(person -> encodedPersons.add(encodePersonToString(person)));
        return encodedPersons;
    }

    /**
     * Encodes the {@code person} into a string representation.
     */
    private static String encodePersonToString(Person person) {

        return "n/" + person.getName() +
                "s/" + person.getSurname() +
                "p/" + person.getPhone() +
                "e/" + person.getEmail() +
                "a/" + person.getAge() +
                "h/" + person.getHairColor() +
                "cn/" + person.getCategoryName() +
                "r/" + person.getRelationship() +
                "fy/" + person.getFriendshipYear();
    }
}
