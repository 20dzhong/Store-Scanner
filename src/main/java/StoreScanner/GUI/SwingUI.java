package StoreScanner.GUI;

import StoreScanner.utils.QREditor;
import StoreScanner.utils.Convert;
import StoreScanner.utils.Name;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SwingUI extends JFrame implements ActionListener {
    protected static Name id;

    SwingUI() throws InterruptedException {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);


        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuFile.add(menuItemExit);

        setJMenuBar(menuBar);

        setLayout(new FlowLayout());

        JButton rescan = new JButton("Rescan");
        rescan.addActionListener(this);
        rescan.setActionCommand("rescan");
        add(rescan);

        JButton exit = new JButton("Exit");
        exit.addActionListener(this);
        exit.setActionCommand("exit");
        add(exit);

        JButton test = new JButton("Test");
        test.addActionListener(this);
        test.setActionCommand("test");
        add(test);

        add(Video.getFrameAsPanel());
        // startScan();


    }

    public void startScan() throws InterruptedException {
        while (true) {
            wait();
            try {
                System.out.println("Scan starting...");
                if (!Video.isRunning()) Video.run();
                while (true) {
                    Thread.sleep(500);
                    id = QREditor.decodeQR(Video.getFrameAsImage());
                    try {
                        if (id.isEmpty) throw new AssertionError("No QR code found! Hence, no identifier.");
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
            id.printInfo();
        }
    }

    public void testing() {
        System.out.println("Hello world");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "exit":
                System.exit(0);
                break;
            case "rescan":
                notify();
                break;
            case "test":
                testing();
                break;
        }
    }

    public static void showUI() throws InterruptedException {
        JFrame window = new SwingUI();

        Image icon = new ImageIcon("./src/main/java/Images/gothub.png").getImage();
        window.setIconImage(icon);

        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.pack();

    }
}

class reee {
    public static void main(String[] args) throws InterruptedException {
        SwingUI.showUI();
    }
}
