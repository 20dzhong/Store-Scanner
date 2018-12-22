package StoreScanner.GUI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

class Video {

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

    static JPanel getFrameAsPanel() {
        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        return panel;
    }

    static void setWebcam(int cameraNum) {
        List<Webcam> webcamList = Webcam.getWebcams();
        if (webcamList.size() > 1 && cameraNum < webcamList.size()) webcam = webcamList.get(cameraNum);
    }

    static BufferedImage getFrameAsImage() {
        webcam.open();
        return webcam.getImage();
    }

    static void pause() {
        panel.pause();
        running = false;
    }

    static void run() {
        panel.resume();
        running = true;
    }

    public static boolean isRunning() {
        return running;
    }
}
