package StoreScanner.utils;

import javafx.beans.NamedArg;
import org.bridj.ann.Name;

public class ID {
    private String firstName;
    private String lastName;
    private String identifier;
    private String email;
    private double balance;

    public Boolean isEmpty;

    public ID() {
        isEmpty = true;
    }

    public ID(@NamedArg("First Name") String firstName, @NamedArg("Last Name") String lastName) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.identifier = (firstName + "_" + lastName).toLowerCase();


        this.balance = 234;
        this.email = "20dzhong@athenian.org";

        isEmpty = false;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return Convert.toAscii(identifier);
    }

    public String getEmail() {
        return email;
    }

    public String getBalance() {
        return ("$ " + balance);
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
