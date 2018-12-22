package StoreScanner.GUI;

import StoreScanner.utils.Name;
import StoreScanner.utils.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingUI extends JFrame implements ActionListener {

    SwingUI() {
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

        add(Video.getFrameAsPanel());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "exit":
                System.exit(0);
                break;
            case "rescan":
                new StreamThread();
                break;
        }
    }

    public static void showUI() {
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
