package StoreScanner.GUI;

import StoreScanner.utils.QREditor;
import StoreScanner.utils.Variable;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Video {

    private static Webcam webcam;
    private static WebcamPanel panel;
    private static boolean running = true;

    static {
        List<Webcam> webcamList = Webcam.getWebcams();
        System.out.println(webcamList.toString());
        if (webcamList.size() > 1) webcam = webcamList.get(1);
        else webcam = Webcam.getDefault();

        webcam.setViewSize(WebcamResolution.VGA.getSize());
    }

    public static WebcamPanel getFrameAsPanel() {
        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        panel.start();
        return panel;
    }

    public static void setWebcam(int cameraNum) {
        List<Webcam> webcamList = Webcam.getWebcams();
        if (webcamList.size() > 1 && cameraNum < webcamList.size()) webcam = webcamList.get(cameraNum);
    }


    public static BufferedImage getFrameAsImage() {
        return webcam.getImage();
    }

    public static void pause() {
        panel.pause();
        running = false;
    }

    public static void run() {
        panel.resume();
        running = true;
    }

    public static boolean isRunning() {
        return running;
    }


    public static class ProcessStream implements Runnable {
        public Thread thread;

        public ProcessStream() {
            this.thread = new Thread(this);
            this.thread.start();

        }

        @Override
        public void run() {
            try {
                System.out.println("Scan starting...");
                Variable.scanRunning = true;
                if (!Video.isRunning()) Video.run();
                while (true) {
                    Thread.sleep(500);
                    Variable.id = QREditor.decodeQR(Video.getFrameAsImage());
                    try {
                        if (Variable.id.isEmpty) throw new AssertionError("No QR code found! Hence, no identifier.");
                        break;
                    } catch (AssertionError ignored) { }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Scanning thread interrupted!");
            } finally {
                Variable.scanRunning = false;

                // TODO WHEN Process thread notify the update thread, update the text there after checking if it's filled
                // Variable.status.setText("Target found, exiting scanning thread.");
                Variable.status.setTextFill(Color.color(0,0.75,0));
                System.out.println("\nTarget found, exiting scanning thread");
            }
            Video.pause();
        }

    }
}
