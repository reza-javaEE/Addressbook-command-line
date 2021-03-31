package com.jemmic.addressbook.parser;

import com.jemmic.addressbook.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.jemmic.addressbook.common.Messages.MSG_INVALID_FORMAT;
import static com.jemmic.addressbook.common.Messages.MSG_INVALID_PERSON_INDEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ParserTest {

    private Parser parser;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void emptyInputParse_shouldReturnsIncorrect() {
        final String[] emptyInputs = {"", "  ", "\n  \n"};
        final String resultMessage = String.format(MSG_INVALID_FORMAT, HelpCommand.MSG_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, emptyInputs);
    }

    @Test
    public void unknownCommandWordParse_shouldReturnsHelp() {
        final String input = "unknowncommandword arguments arguments";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    @Test
    public void helpCommandParse_shouldParsedCorrectly() {
        final String input = "help";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    @Test
    public void showallCommandParse_shouldParsedCorrectly() {
        final String input = "showall";
        parseAndAssertCommandType(input, ShowAllCommand.class);
    }

    @Test
    public void exitCommandParse_shouldParsedCorrectly() {
        final String input = "exit";
        parseAndAssertCommandType(input, ExitCommand.class);
    }

    @Test
    public void deleteCommandNoArgsParse_shouldReturnErrorMessage() {
        final String[] inputs = {"delete", "delete "};
        final String resultMessage = String.format(MSG_INVALID_FORMAT, DeleteCommand.MSG_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommandArgsIsNotSingleNumberParse_shouldReturnErrorMessage() {
        final String[] inputs = {"delete notAnumber ", "delete 8*wh12", "delete 1 2 3 4 5"};
        parseAndAssertIncorrectWithMessage(MSG_INVALID_PERSON_INDEX, inputs);
    }

    @Test
    public void deleteCommandNumericArgParse_shouldIndexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "delete " + testIndex;
        final DeleteCommand result = parseAndAssertCommandType(input, DeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void addCommandInvalidPersonDataInArgsParse_shouldReturnInvalidFormatMessge() {
        final String invalidName = "";
        final String validName = "n/mahshid";
        final String validSurName = "s/onsori";
        final String invalidSurName = "";
        final String invalidPhoneArg = "p/";
        final String validPhoneArg = "p/" + 9987456;
        final String invalidEmailArg = "e/";
        final String validEmailArg = "e/" + "test@ymail.com";

        final String addCommandFormatString = "add n/%s s/%s p/%s e/%s";

        // test each incorrect person data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addCommandFormatString, invalidName, validSurName, validPhoneArg, validEmailArg),
                // invalid surname
                String.format(addCommandFormatString, validName, invalidSurName, invalidPhoneArg, validEmailArg),
                // invalid phone
                String.format(addCommandFormatString, validName, validSurName, invalidPhoneArg, validEmailArg),
                // invalid email
                String.format(addCommandFormatString, validName, validSurName, validPhoneArg, invalidEmailArg)
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, InvalidCommand.class);
        }
    }

    /**
     * Asserts that parsing the given inputs will return InvalidCommand with the given feedback message.
     */
    private void parseAndAssertIncorrectWithMessage(String feedbackMessage, String... inputs) {
        for (String input : inputs) {
            final InvalidCommand result = parseAndAssertCommandType(input, InvalidCommand.class);
            assertEquals(result.feedbackToUser, feedbackMessage);
        }
    }

    /**
     * Parses input and asserts the class/type of the returned command object.
     *
     * @param input                to be parsed
     * @param expectedCommandClass expected class of returned command
     * @return the parsed command object
     */
    private <T extends Command> T parseAndAssertCommandType(String input, Class<T> expectedCommandClass) {
        final Command result = parser.parseCommand(input);
        assertTrue(result.getClass().isAssignableFrom(expectedCommandClass));
        return (T) result;
    }
}
