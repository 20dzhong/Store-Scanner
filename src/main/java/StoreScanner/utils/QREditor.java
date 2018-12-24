package StoreScanner.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QREditor {

    private static void generateQRCodeImage(String text, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, Constant.qrWidth, Constant.qrHeight);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generate(ID id) {

        String PATH = "./src/main/resources/TestImages/" + id.getIdentifier() + ".png";
        if (id.isEmpty) {
            System.out.println("ID is empty, could not create QR Code");
            return;
        }
        try {
            generateQRCodeImage(id.encryptedIdentifier(), PATH);
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        } catch (WriterException e) {
            System.out.println("could not generate QR Code, WriterException :: " + e.getMessage());
        }
    }

    public static ID decodeQR(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        return getName(bufferedImage);
    }

    public static ID decodeQR(BufferedImage qrCodeimage) {
        return getName(qrCodeimage);
    }

    private static ID getName(BufferedImage qrCodeimage) {
        LuminanceSource source = new BufferedImageLuminanceSource(qrCodeimage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            String result = new MultiFormatReader().decode(bitmap).getText();
            if (!(result.substring(0, 1).equals(":") && result.substring(result.length() - 1).equals(":")))
                throw new AssertionError("The QR Code: " + result + " is not acceptable!");
            String[] holder = (Convert.toChar(result)).split("_");
            return new ID(holder[0], holder[1]);
        } catch (NotFoundException e) {
            return new ID();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
            return new ID();
        }
    }
}

