package com.jemmic.addressbook.commands;

import com.jemmic.addressbook.common.Messages;
import com.jemmic.addressbook.model.AddressBook;
import com.jemmic.addressbook.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommandTest {

    private AddressBook emptyAddressBook;
    private AddressBook addressBook;

    private List<Person> emptyDisplayList;
    private List<Person> listWithEveryone;

    @BeforeEach
    public void setUp()  {
        Person rezaRezaei = new Person("reza", "rezaei", "9213612346", "reza@yahoo.com", "36",
                "black", "Friend", "", "7");
        Person mashiRezaei = new Person("mashi", "rezaei", "9213612346", "mashi@yahoo.com", "28",
                "black", "Family", "wife", "");
        Person fatiRezaei = new Person("fati", "rezaei", "9213612346", "fati@yahoo.com", "28",
                "black", "Family", "sister", "");
        Person tomJones = new Person("Tom", "jones", "9213612346", "tom@yahoo.com", "28",
                "black", "Acquaintance", "", "");

        emptyAddressBook = createAddressBook();
        addressBook = createAddressBook(rezaRezaei, mashiRezaei, fatiRezaei, tomJones);

        emptyDisplayList = createList();

        listWithEveryone = createList(rezaRezaei, mashiRezaei, fatiRezaei, tomJones);
    }

    @Test
    public void execute_emptyAddressBook_returnsPersonNotFoundMessage() {
        assertDeletionFailsDueToNoSuchPerson(1, emptyAddressBook, listWithEveryone);
    }

    @Test
    public void execute_noPersonDisplayed_returnsInvalidIndexMessage() {
        assertDeletionFailsDueToInvalidIndex(1, addressBook, emptyDisplayList);
    }

    @Test
    public void execute_targetUnknownPerson_returnsPersonNotFoundMessage() {
        Person UnknownPerson = new Person("unknown", "unknown", "99887766", "unknown@yahoo.com", "65",
                "black", "Acquaintance", "", "");
        List<Person> listWithUnknownPerson = createList(UnknownPerson);

        assertDeletionFailsDueToNoSuchPerson(1, addressBook, listWithUnknownPerson);
    }

    @Test
    public void execute_invalidIndex_returnsInvalidIndexMessage() {
        assertDeletionFailsDueToInvalidIndex(0, addressBook, listWithEveryone);
        assertDeletionFailsDueToInvalidIndex(-1, addressBook, listWithEveryone);
        assertDeletionFailsDueToInvalidIndex(listWithEveryone.size() + 1, addressBook, listWithEveryone);
    }

    /**
     * Creates a new delete command.
     *
     * @param targetVisibleIndex of the person that we want to delete
     */
    private DeleteCommand createDeleteCommand(int targetVisibleIndex, AddressBook addressBook,
                                              List<Person> displayList) {

        DeleteCommand command = new DeleteCommand(targetVisibleIndex);
        command.setData(addressBook, displayList);

        return command;
    }

    /**
     * Executes the command, and checks that the execution was what we had expected.
     */
    private void assertCommandBehaviour(DeleteCommand deleteCommand, String expectedMessage,
                                        AddressBook expectedAddressBook, AddressBook actualAddressBook) {

        CommandResult result = deleteCommand.execute();

        assertEquals(expectedMessage, result.userFeedbackMsg);
        assertEquals(expectedAddressBook.getPersonList(), actualAddressBook.getPersonList());
    }

    /**
     * Asserts that the index is not valid for the given display list.
     */
    private void assertDeletionFailsDueToInvalidIndex(int invalidVisibleIndex, AddressBook addressBook,
                                                      List<Person> displayList) {

        String expectedMessage = Messages.MSG_INVALID_PERSON_INDEX;

        DeleteCommand command = createDeleteCommand(invalidVisibleIndex, addressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, addressBook, addressBook);
    }

    /**
     * Asserts that the person at the specified index cannot be deleted, because that person
     * is not in the address book.
     */
    private void assertDeletionFailsDueToNoSuchPerson(int visibleIndex, AddressBook addressBook,
                                                      List<Person> displayList) {

        String expectedMessage = Messages.MSG_PERSON_NOT_FOUND;

        DeleteCommand command = createDeleteCommand(visibleIndex, addressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, addressBook, addressBook);
    }

    public static AddressBook createAddressBook(Person... persons) {
        AddressBook addressBook = new AddressBook();

        for (Person person : persons) {
            addressBook.addPerson(person);
        }

        return addressBook;
    }


    public static List<Person> createList(Person... persons) {
        List<Person> list = new ArrayList<Person>();

        for (Person person : persons) {
            list.add(person);
        }

        return list;
    }


    public static AddressBook clone(AddressBook addressBook) {
        return new AddressBook(addressBook.getPersonList());
    }

}
