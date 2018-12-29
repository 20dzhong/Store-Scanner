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

    /**
     * generates new QR code based on an ID object, will be used in the future to implement an Adding GUI
     **/
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

    /**
     * decode a file, mostly used for testing
     **/
    public static ID decodeQR(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        return getName(bufferedImage);
    }

    /**
     * decode an image, from the video stream to determine whether there is a QR code in the picture or not
     * if there is, return the dummy ID object with everything filled out, if in any case it failed, whether the
     * message format is not correct, or there is no QR code, an empty ID will be created with the corresponding error
     * thrown
     *
     * @param qrCodeimage A buffered image from the video stream
     * @return empty ID object if no QR code, filled ID object if QR code is correct and exist
     */
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
            String[] holder = (Utility.toChar(result)).split("_");
            return new ID(holder[0], holder[1], String.format("20%s%s@athenian.org", holder[0].charAt(0), holder[1]),
                    1000.0);
            // return new ID(holder[0], holder[1]);
        } catch (NotFoundException e) {
            return new ID();
        } catch (AssertionError e) {
            Variable.errorLog = e.getMessage();
            System.out.println(e.getMessage());
            return new ID();
        }
    }
}

