package com.jemmic.addressbook.commands;

import com.jemmic.addressbook.common.Messages;
import com.jemmic.addressbook.model.AddressBook;
import com.jemmic.addressbook.model.Person;

import java.util.List;

import static com.jemmic.addressbook.view.TxtUserInterface.DISPLAYED_INDEX_OFFSET;


/**
 * present an executable command.
 */
public class Command {
    protected AddressBook addressBook;
    protected List<Person> relevantPersons;
    private int targetIndex = -1;

    /**
     * @param targetIndex last visible index of the person
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command() {
    }

    /**
     * create a message that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonList(List<Person> personsDisplayed) {
        return String.format(Messages.MSG_PERSONS_LISTED, personsDisplayed.size());
    }

    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute() {
        throw new UnsupportedOperationException("implemented by child classes");
    }


    /**
     * provide the data for execute the command.
     */
    public void setData(AddressBook addressBook, List<Person> relevantPersons) {
        this.addressBook = addressBook;
        this.relevantPersons = relevantPersons;
    }

    /**
     * Extract the target person in the last list.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds
     */
    protected Person getTargetPerson() throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }


}
