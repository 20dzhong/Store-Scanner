package StoreScanner.GUI;

import StoreScanner.utils.Name;
import StoreScanner.utils.Variable;
import StoreScanner.utils.QREditor;
import java.io.IOException;


public class StreamThread implements Runnable {
    public Thread thread;

    StreamThread() {
        this.thread = new Thread(this);
        this.thread.start();

    }

    @Override
    public void run() {
        try {
            System.out.println("Scan starting...");
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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errors occurred in decoding the QR code");
        } finally {
            System.out.println("\nTarget found, exiting scanning thread");
        }
        Video.pause();
    }

}
