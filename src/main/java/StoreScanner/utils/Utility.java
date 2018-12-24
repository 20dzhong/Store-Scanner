package StoreScanner.utils;

import StoreScanner.GUI.FXUI;
import javafx.beans.NamedArg;

import javafx.scene.paint.Color;
import javafx.scene.control.Label;


public class Utility {
    public static String toAscii(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append(":");
        for (int i = 0; i < text.length(); i++) builder.append((int) text.charAt(i)).append(":");
        return builder.toString();
    }

    public static String toChar(String text) {
        String[] splited = text.substring(1, text.length() - 1).split(":");
        StringBuilder builder = new StringBuilder();
        for (String index : splited) builder.append((char) (int) Integer.valueOf(index));
        return builder.toString();
    }

    public static String toLabel(ID id) {
        if (id.isEmpty) return ("No QR code found in the image.");
        else return ("ID Found!\nEncrypted ID: " + id.encryptedIdentifier() + "\nIdentifier: " + id.getIdentifier() + "\nfirst name: " + id.getFirstName()
                    + "\nlast name: " + id.getLastName());
    }

    public static void validate(@NamedArg("Balance") double balance, @NamedArg("Charges in String") String charge) throws NegativeException, ExceedBalanceException, AssertionError {
        double dCharge;
        try{ dCharge = Double.valueOf(charge); } catch (NumberFormatException e) { throw new AssertionError("Please enter a valid number!"); }
        if (dCharge <= 0.0) throw new NegativeException("The charge cannot be 0 or lower!");
        if (balance < dCharge) throw new ExceedBalanceException("The charge cannot exceed the balance!");
    }

    public static void setMessage(Label label, @NamedArg("red") double red, @NamedArg("green") double green, @NamedArg("blue") double blue, String e) {
        label.setTextFill(Color.color(red, green, blue));
        label.setText(e);
    }

    public static class NegativeException extends Throwable {
        public NegativeException(String message) {
            super(message);
        }
    }

    public static class ExceedBalanceException extends Throwable {
        public ExceedBalanceException(String message) {
            super(message);
        }
    }

    public static void periodicUpdater() {
        if (!Variable.id.isEmpty) {
            Utility.setMessage(FXUI.videoStatus, 0, 0.60, 0, ("Scan finished!\nShowing info for: " +
                    Variable.id.getFirstName() + " " + Variable.id.getLastName()));
            FXUI.encryptedID.setText(Variable.id.encryptedIdentifier());
            FXUI.decryptedID.setText(Variable.id.getIdentifier());
            FXUI.firstNameField.setText(Variable.id.getFirstName());
            FXUI.lastNameField.setText(Variable.id.getLastName());
            FXUI.emailField.setText(Variable.id.getEmail());
            FXUI.balanceField.setText("$ " + Variable.id.getBalance());
            FXUI.deductionField.setDisable(false);
        }

        if (!Variable.errorLog.equals("")) {
            Utility.setMessage(FXUI.videoStatus, 1, 0, 0, Variable.errorLog);
            Variable.errorLog = "";
        }
    }
}
