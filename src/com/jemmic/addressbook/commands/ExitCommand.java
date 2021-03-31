package com.jemmic.addressbook.commands;

/**
 * Exit from AddressBook.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MSG_USAGE = COMMAND_WORD + ": Exit from application.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MSG_EXIT_ACKNOWEDGEMENT = "... Exiting Address Book  ...";

    @Override
    public CommandResult execute() {
        return new CommandResult(MSG_EXIT_ACKNOWEDGEMENT);
    }

    public static boolean isExit(Command command) {
        return command instanceof ExitCommand; // instanceof returns false if it is null
    }
}
