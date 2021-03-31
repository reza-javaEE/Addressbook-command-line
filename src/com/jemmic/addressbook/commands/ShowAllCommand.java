package com.jemmic.addressbook.commands;

import com.jemmic.addressbook.model.Person;

import java.util.List;

/**
 * Show all persons in the address book .
 */
public class ShowAllCommand extends Command {

    public static final String COMMAND_WORD = "showall";

    public static final String MSG_USAGE = COMMAND_WORD
            + ": Display all persons in the address book .\n"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        List<Person> allPersons = addressBook.getPersonList();
        return new CommandResult(getMessageForPersonList(allPersons), allPersons);
    }
}
