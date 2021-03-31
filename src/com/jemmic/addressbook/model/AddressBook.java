package com.jemmic.addressbook.model;

import com.jemmic.addressbook.common.Messages;
import com.jemmic.addressbook.exception.PersonNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class AddressBook {

    private List<Person> personList = new ArrayList<>();

    public AddressBook() {
    }

    public AddressBook(List<Person> personList) {
        this.personList = personList;
    }

    /**
     * Adds a person to the address book.
     */
    // todo check Duplication
    public void addPerson(Person newPerson) {
        personList.add(newPerson);
    }

    /**
     * Remove person from the address book.
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(Person deletedPerson) throws PersonNotFoundException {
        final boolean personFound = personList.remove(deletedPerson);
        if (!personFound) {
            throw new PersonNotFoundException(Messages.MSG_PERSON_NOT_FOUND);
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }

}
