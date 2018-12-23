package StoreScanner.utils;

public class Convert {
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
        else
            return ("ID Found!\nEncrypted ID: " + id.encryptedIdentifier() + "\nIdentifier: " + id.getIdentifier() + "\nfirst name: " + id.getFirstName()
                    + "\nlast name: " + id.getLastName());
    }
}
