package com.jemmic.addressbook.commands;

import com.jemmic.addressbook.common.Messages;
import com.jemmic.addressbook.exception.PersonNotFoundException;
import com.jemmic.addressbook.model.Person;


/**
 * Delete a person using index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MSG_USAGE = COMMAND_WORD
            + ": Delete the person by the index number.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MSG_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";


    public DeleteCommand(int targetIndex) {
        super(targetIndex);
    }


    @Override
    public CommandResult execute() {
        try {
            final Person target = getTargetPerson();
            addressBook.removePerson(target);
            return new CommandResult(String.format(MSG_DELETE_PERSON_SUCCESS, target));

        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return new CommandResult(Messages.MSG_INVALID_PERSON_INDEX);
        } catch (PersonNotFoundException personNotFoundException) {
            return new CommandResult(Messages.MSG_PERSON_NOT_FOUND);
        }
    }

}
