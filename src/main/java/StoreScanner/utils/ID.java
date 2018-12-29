package StoreScanner.utils;

import javafx.beans.NamedArg;

public class ID {
    private String firstName;
    private String lastName;
    private String identifier;
    private String email;
    private double balance;

    public Boolean isEmpty;

    /**
     * default empty ID if no arguments
     **/
    public ID() {
        isEmpty = true;
        firstName = "null";
        lastName = "null";
        identifier = "";
        email = "null";
        balance = 0;
    }

    /**
     * Creates a ID using the first and last name from the QR Code, then using the identifier, connect to the database
     * and set the Email as well as the balance
     *
     * @param firstName First name from QR Code
     * @param lastName  Last name from QR Code
     */
    public ID(@NamedArg("First Name") String firstName, @NamedArg("Last Name") String lastName) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.identifier = (firstName + "_" + lastName).toLowerCase();

        this.balance = fetchBalance(encryptedIdentifier());

        this.email = fetchEmail(encryptedIdentifier());

        isEmpty = false;
    }

    /**
     * constructor with all param filled, used for testing purposes
     **/
    public ID(@NamedArg("First Name") String firstName, @NamedArg("Last Name") String lastName, @NamedArg("Email")
            String email, @NamedArg("Balance") double balance) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.identifier = (firstName + "_" + lastName).toLowerCase();

        this.balance = 1000.0;

        this.email = "20" + this.firstName.substring(0, 1) + this.lastName + "@athenian.org";

        isEmpty = false;
    }

    /**
     * obtain the email info directly from the database if it's not empty, private use only
     **/
    private String fetchEmail(String identifier) {
        // TODO implement function to obtain Email from database
        return null;
    }

    /**
     * obtain the balance directly from the database, private use
     **/
    private double fetchBalance(String identifier) {
        // TODO implement function to obtain balance from database
        return Integer.parseInt(null);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String encryptedIdentifier() {
        return Utility.toAscii(identifier);
    }

    public String getEmail() {
        return email;
    }

    public Double getBalance() {
        return balance;
    }

    public void printInfo() {
        if (!this.isEmpty) {
            System.out.println("\n");
            System.out.println(this.encryptedIdentifier());
            System.out.println(this.getIdentifier());
            System.out.println("First: " + this.getFirstName() + "\nLast: " + this.getLastName() + "\nEmail: " +
                    this.getEmail() + "\n\n");
        } else {
            System.out.println("Current name is empty");
        }
    }
}
