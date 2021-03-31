package com.jemmic.addressbook.commands;


/**
 * Show help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MSG_USAGE = COMMAND_WORD + ": Show instructions.\n"
            + "Example: " + COMMAND_WORD;

    //todo editCommand
    @Override
    public CommandResult execute() {
        return new CommandResult(
                AddCommand.MSG_USAGE
                        + "\n" + DeleteCommand.MSG_USAGE
                        + "\n" + ShowAllCommand.MSG_USAGE
                        + "\n" + HelpCommand.MSG_USAGE
                        + "\n" + ExitCommand.MSG_USAGE
        );
    }
}
