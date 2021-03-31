package com.jemmic.addressbook.commands;


import com.jemmic.addressbook.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * present the result execution of a command .
 */
public class CommandResult {

    /**
     * The feedback message to the user.
     */
    public final String userFeedbackMsg;

    /**
     * The list of persons that was produced by the command
     */
    private final List<Person> relatedPersons;

    public CommandResult(String userFeedbackMsg) {
        this.userFeedbackMsg = userFeedbackMsg;
        relatedPersons = null;
    }

    public CommandResult(String userFeedbackMsg, List<Person> relatedPersons) {
        this.userFeedbackMsg = userFeedbackMsg;
        this.relatedPersons = relatedPersons;
    }

    /**
     * Returns a list of persons related to the command result.
     */
    public Optional<List<Person>> getRelatedPersons() {
        return Optional.ofNullable(relatedPersons);
    }

}
