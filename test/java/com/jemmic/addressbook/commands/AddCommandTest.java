package com.jemmic.addressbook.commands;

import com.jemmic.addressbook.model.AddressBook;
import com.jemmic.addressbook.model.Person;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AddCommandTest {
    private static final List<Person> EMPTY_PERSON_LIST = Collections.emptyList();


    @Test
    public void addCommand_testValidData() {
        Person person = generateTestPerson();
        AddCommand command = new AddCommand(person.getName(), person.getSurname(), person.getPhone(), person.getEmail(), person.getAge(), person.getHairColor()
                , person.getCategoryName(), person.getRelationship(), person.getFriendshipYear());
        Person p = command.getPerson();

        assertEquals(person.getName(), p.getName());
        assertEquals(person.getSurname(), p.getSurname());
        assertEquals(person.getPhone(), p.getPhone());
        assertEquals(person.getEmail(), p.getEmail());
        assertEquals(person.getAge(), p.getAge());
        assertEquals(person.getHairColor(), p.getHairColor());
        assertEquals(person.getCategoryName(), p.getCategoryName());
        assertEquals(person.getRelationship(), p.getRelationship());
        assertEquals(person.getFriendshipYear(), p.getFriendshipYear());

    }

    @Test
    public void addCommand_testEmptyAddressBook() {
        Person p = generateTestPerson();
        AddCommand command = new AddCommand(p);
        AddressBook book = new AddressBook();
        command.setData(book, EMPTY_PERSON_LIST);
        CommandResult result = command.execute();
        List<Person> people = book.getPersonList();

        assertTrue(people.contains(p));
        assertEquals(1, people.size());
        assertFalse(result.getRelatedPersons().isPresent());
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, p), result.userFeedbackMsg);
    }

    public static Person generateTestPerson() {
        return new Person("Tom", "jones", "9213612346", "Ali@yahoo.com", "28",
                "black", "Friend", "", "7");
    }
}
