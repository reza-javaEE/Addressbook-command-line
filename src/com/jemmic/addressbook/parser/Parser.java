package com.jemmic.addressbook.parser;

import com.jemmic.addressbook.commands.*;
import com.jemmic.addressbook.exception.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jemmic.addressbook.common.Messages.*;

/**
 * Parse user inputs.
 */
public class Parser {

    public static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public static final Pattern PERSON_DATA_ARGS_FORMAT =
            Pattern.compile("n/(?<name>[^/]+)"
                    + "s/(?<surname>[^/]+)"
                    + "p/(?<phone>[^/]+)"
                    + "e/(?<email>[^/]+)"
                    + "a/(?<age>[^/]+)"
                    + "h/(?<hairColor>[^/]+)"
                    + "cn/(?<categoryName>[^/]+)"
                    + "r/(?<relationship>[^/]+)"
                    + "fy/(?<friendshipYear>[^/]+)"
            );


    /**
     * initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parse user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new InvalidCommand(String.format(MSG_INVALID_FORMAT, HelpCommand.MSG_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String args = matcher.group("arguments");

        switch (commandWord) { //todo EditCommand.COMMAND_WORD

            case AddCommand.COMMAND_WORD:
                return prepareAdd(args);

            case DeleteCommand.COMMAND_WORD:
                return prepareDelete(args);

            case ShowAllCommand.COMMAND_WORD:
                return new ShowAllCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
            default:
                return new HelpCommand();
        }
    }

    /**
     * Parse arguments for add person command.
     * and check the mandatory fields.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new InvalidCommand(String.format(MSG_INVALID_FORMAT, AddCommand.MSG_USAGE));
        }

        if (matcher.group("name").isBlank() ||
                matcher.group("surname").isBlank() ||
                matcher.group("phone").isBlank() ||
                matcher.group("email").isBlank()) {

            return new InvalidCommand(String.format(MSG_MANDATORY_FIELD_NOT_FILL, AddCommand.MSG_USAGE));
        }

        return new AddCommand(
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

    /**
     * Parse arguments for delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new DeleteCommand(targetIndex);
        } catch (ParseException pe) {
            return new InvalidCommand(String.format(MSG_INVALID_FORMAT, DeleteCommand.MSG_USAGE));
        } catch (NumberFormatException nfe) {
            return new InvalidCommand(MSG_INVALID_PERSON_INDEX);
        }
    }

    /**
     * Parses the given arguments string as a single index number.
     *
     * @param args arguments string to parse as index number
     * @return the parsed index number
     * @throws ParseException        if no region of the args string could be found for the index
     * @throws NumberFormatException the args string region is not a valid number
     */
    private int parseArgsAsDisplayedIndex(String args) throws ParseException, NumberFormatException {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find index number to parse");
        }
        return Integer.parseInt(matcher.group("targetIndex"));
    }

}
