package com.jemmic.addressbook.view;


import com.jemmic.addressbook.commands.CommandResult;
import com.jemmic.addressbook.model.Person;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.jemmic.addressbook.common.Messages.*;


/**
 * Application Text UI .
 */
public class TxtUserInterface {

    /**
     * Offset required to convert between 1-indexing and 0-indexing.
     */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    /**
     * prefix added to the beginning of lines printed by AddressBook
     */
    private static final String LINE_PREFIX = " - - - ";

    private static final String LS = System.lineSeparator();

    private static final String DIVIDER = "* * * * * * * * * * * * * * * * * ";

    /**
     * Format of indexed list item
     */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";

    /**
     * Format of a comment input line.
     */
    private static final String COMMENT_LINE_FORMAT_REGEX = "#.*";

    private final Scanner in;
    private final PrintStream out;

    public TxtUserInterface() {
        this(System.in, System.out);
    }

    public TxtUserInterface(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Returns true if the user input line should be ignored.
     * Input should be ignored if it is parsed as a comment, is only whitespace, or is empty.
     *
     * @param rawInputLine full raw user input line.
     * @return true if the entire user input line should be ignored.
     */
    private boolean shouldIgnore(String rawInputLine) {
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    /**
     * Returns true if the user input line is a comment line.
     *
     * @param rawInputLine full raw user input line.
     * @return true if input line is a comment.
     */
    private boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches(COMMENT_LINE_FORMAT_REGEX);
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     * Ignores empty, pure whitespace, and comment lines.
     * Echos the command back to the user.
     *
     * @return command (full line) entered by the user
     */
    public String getUserCommand() {
        out.print(LINE_PREFIX + "Enter command: ");
        String fullInputLine = in.nextLine();

        // silently consume all ignored lines
        while (shouldIgnore(fullInputLine)) {
            fullInputLine = in.nextLine();
        }

        showToUser("[Command entered:" + fullInputLine + "]");
        return fullInputLine;
    }

    /**
     * Generates and prints the welcome message upon the start of the application.
     *
     * @param version         current version of the application.
     * @param storageFilePath path to the storage file being used.
     */
    public void showWelcomeMessage(String version, String storageFilePath) {
        String storageFileInfo = String.format(MSG_USING_STORAGE_FILE, storageFilePath);
        showToUser(
                DIVIDER,
                DIVIDER,
                version,
                MSG_PROGRAM_LAUNCH,
                storageFileInfo,
                DIVIDER);
    }

    public void showGoodbyeMessage() {
        showToUser(MSG_GOODBYE, DIVIDER, DIVIDER);
    }


    public void showInitFailedMessage() {
        showToUser(MSG_FAILED, DIVIDER, DIVIDER);
    }

    /**
     * Shows message(s) to the user
     */
    public void showToUser(String... message) {
        for (String m : message) {
            out.println(LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX));
        }
    }

    /**
     * Shows the result of a command execution to the user. Includes additional formatting to demarcate different
     * command execution segments.
     */
    public void showResultToUser(CommandResult result) {
        final Optional<List<Person>> resultPersons = result.getRelatedPersons();
        resultPersons.ifPresent(this::showPersonListView);
        showToUser(result.userFeedbackMsg, DIVIDER);
    }

    /**
     * Shows a list of persons to the user, formatted as an indexed list.
     */
    private void showPersonListView(List<Person> persons) {
        final List<String> formattedPersons = new ArrayList<>();
        for (Person person : persons) {
            formattedPersons.add(person.toString());
        }
        showToUserAsIndexedList(formattedPersons);
    }

    /**
     * Shows a list of strings to the user, formatted as an indexed list.
     */
    private void showToUserAsIndexedList(List<String> list) {
        showToUser(getIndexedListForViewing(list));
    }

    /**
     * Formats a list of strings as a viewable indexed list.
     */
    private static String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as a viewable indexed list item.
     *
     * @param visibleIndex visible index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

}
