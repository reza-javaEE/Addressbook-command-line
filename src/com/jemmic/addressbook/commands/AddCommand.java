package com.jemmic.addressbook.commands;

import com.jemmic.addressbook.model.Person;

/**
 * Add a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MSG_USAGE = COMMAND_WORD + ": Add a person to the address book. "
            + "Parameters: name surname p/phone e/email a/age h/hairColor cn/category r/relationship fy/friendshipYear \n"
            + "Examples : \n" +
            COMMAND_WORD + " n/reza   s/rezaei p/9213613219 e/reza@ymail.com a/36 h/black  cn/Friend       r/      fy/5 \n" +
            COMMAND_WORD + " n/mashi  s/mashi  p/9214556677 e/mash@yahoo.com a/32 h/blonde cn/Family       r/wife  fy/0 \n" +
            COMMAND_WORD + " n/soheil s/onsori p/9219988707 e/sohl@gmail.com a/39 h/green  cn/Acquaintance r/      fy/0";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";

    private final Person toAdd; // todo check Duplication


    public AddCommand(String name, String surname, String phone,
                      String email, String age, String hairColor,
                      String categoryName, String relationship, String friendshipYear) {

        this.toAdd = new Person(
                name, surname, phone, email, age, hairColor, categoryName, relationship, friendshipYear
        );
    }

    public AddCommand(Person toAdd) {
        this.toAdd = toAdd;
    }

    public Person getPerson() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {

        addressBook.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

}
