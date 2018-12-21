package StoreScanner.utils;

public class Name {
    private String firstName;
    private String lastName;
    private String identifier;
    public Boolean isEmpty;

    public Name() { isEmpty = true; }

    public Name(String firstName, String lastName) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.identifier = (firstName + "_" + lastName).toLowerCase();
        isEmpty = false;
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

    public void printInfo() {
        System.out.println("\n");
        System.out.println(this.encryptedIdentifier());
        System.out.println(this.getIdentifier());
        System.out.println("First: " + this.getFirstName() + "\nLast: " + this.getLastName() + "\n\n");
    }
}
