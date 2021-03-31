package com.jemmic.addressbook;

import com.jemmic.addressbook.commands.Command;
import com.jemmic.addressbook.commands.CommandResult;
import com.jemmic.addressbook.commands.ExitCommand;
import com.jemmic.addressbook.exception.InvalidStorageFilePathException;
import com.jemmic.addressbook.exception.StorageOperationException;
import com.jemmic.addressbook.file.DataStorage;
import com.jemmic.addressbook.model.AddressBook;
import com.jemmic.addressbook.model.Person;
import com.jemmic.addressbook.parser.Parser;
import com.jemmic.addressbook.view.TxtUserInterface;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class Main {

    private AddressBook addressBook;
    private TxtUserInterface txtUI;
    private DataStorage file;

    public static final String WELCOME = "... Welcome to command line AddressBook ...";
    
    private List<Person> updatedList = Collections.emptyList();

    public static void main(String... args) {
        new Main().runProgram(args);
    }

    public void runProgram(String[] args) {
        start(args);
        runCommandLoop();
        exit();
    }

    /**
     * Set up objects -- load up data .
     *
     * @param arguments arguments supplied by the user at program launch
     */
    private void start(String[] arguments) {
        try {
            this.txtUI = new TxtUserInterface();
            this.file = initStorageFile(arguments);
            this.addressBook = file.load();
            txtUI.showWelcomeMessage(WELCOME, file.getPath());

        } catch (InvalidStorageFilePathException | StorageOperationException e) {
            txtUI.showInitFailedMessage();
            throw new RuntimeException(e);
        }
    }


    private void exit() {
        txtUI.showGoodbyeMessage();
        System.exit(0);
    }

    /**
     * Read the command and execute it, until the exit command.
     */
    private void runCommandLoop() {
        Command command;
        do {
            String userCommand = txtUI.getUserCommand();
            command = new Parser().parseCommand(userCommand);
            CommandResult result = executeCommand(command);
            recordResult(result);
            txtUI.showResultToUser(result);

        } while (!ExitCommand.isExit(command));
    }

    /**
     * Update the {@link #updatedList} if the result contains a list of Persons.
     */
    private void recordResult(CommandResult result) {
        final Optional<List<Person>> personList = result.getRelatedPersons();
        personList.ifPresent(people -> updatedList = people);
    }

    /**
     * Execute command and return result.
     *
     * @param command user command
     * @return result of the command
     */
    private CommandResult executeCommand(Command command) {
        try {
            command.setData(addressBook, updatedList);
            CommandResult result = command.execute();
            file.save(addressBook);
            return result;
        } catch (Exception e) {
            txtUI.showToUser(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the StorageFile object.
     *
     * @param launchArgs arguments supplied by the user at program launch.
     * @throws InvalidStorageFilePathException if the target file path is incorrect.
     */
    private DataStorage initStorageFile(String[] launchArgs) throws InvalidStorageFilePathException {
        boolean isStorageFileSpecifiedByUser = launchArgs.length > 0;
        return isStorageFileSpecifiedByUser ? new DataStorage(launchArgs[0]) : new DataStorage();
    }


}

