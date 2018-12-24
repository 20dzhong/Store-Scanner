package StoreScanner.GUI;

import StoreScanner.utils.Constant;
import StoreScanner.utils.Utility;
import StoreScanner.utils.ID;
import StoreScanner.utils.Variable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicReference;

public class FXUI extends Application {

    private int x = 0;
    private int y = 4;

    private double opacity = 0.8;

    // vars for the left panel
    public static Text encryptedIDLabel;
    public static Text encryptedID;

    public static Text decryptedIDLabel;
    public static Text decryptedID;

    public static Text firstNameLabel;
    public static TextField firstNameField;

    public static Text lastNameLabel;
    public static TextField lastNameField;

    public static Text emailLabel;
    public static TextField emailField;

    public static Text balanceLabel;
    public static TextField balanceField;

    public static Text deductionLabel;
    public static TextField deductionField;

    public static Text finalBalanceLabel;
    public static Label finalBalanceField;

    public static Label videoStatus;

    @Override
    public void start(Stage stage) {

        Pane leftPane = createLeftPane();
        Pane rightPane = createRightPane();

        /*
         * Final Main Pane
         * Combine both pane
         */
        GridPane mainPane = new GridPane();
        mainPane.setGridLinesVisible(true);
        mainPane.add(leftPane, 0, 0);
        mainPane.add(rightPane, 1, 0);

        Thread updateThread = new Thread(() -> {
            Runnable updater = Utility::periodicUpdater;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                }
                Platform.runLater(updater);
            }
        });

        updateThread.setDaemon(true);
        updateThread.start();

        /*
         * Display main scene
         */
        Scene scene = new Scene(mainPane);
        stage.setTitle("Student Store Scanner");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * TODO How do you do this
     * private Button createButton(@NamedArg("Name") String name, @NamedArg("Action") ) {
     * Button button = new Button(name);
     * EventHandler<ActionEvent> event = e -> ;
     * return button;
     * }
     **/

    private Pane createLeftPane() {
        /*
         * Grid Pane #1, West, Display Information
         * Left side pane
         */

        GridPane leftPane = initializeGridPane();

        // encrypted id grid
        encryptedIDLabel = new Text("Encrypted ID");
        encryptedID = new Text(Variable.id.encryptedIdentifier());
        encryptedID.setOpacity(opacity - 0.3);
        encryptedID.setDisable(true);

        // non encrypted id
        decryptedIDLabel = new Text("Decrypted ID");
        decryptedID = new Text(Variable.id.getIdentifier());
        decryptedID.setOpacity(opacity - 0.3);
        decryptedID.setDisable(true);

        // first name grid
        firstNameLabel = new Text("First Name");
        firstNameField = new TextField(Variable.id.getFirstName());
        firstNameField.setOpacity(opacity);
        firstNameField.setDisable(true);

        // last name grid
        lastNameLabel = new Text("Last Name");
        lastNameField = new TextField(Variable.id.getLastName());
        lastNameField.setOpacity(opacity);
        lastNameField.setDisable(true);

        // email
        emailLabel = new Text("Email");
        emailField = new TextField(Variable.id.getEmail());
        emailField.setOpacity(opacity);
        emailField.setDisable(true);

        // account balance
        balanceLabel = new Text("Account Balance");
        balanceField = new TextField("$ " + Variable.id.getBalance());
        balanceField.setStyle("-fx-text-inner-color: green;");
        balanceField.setOpacity(1.0);
        balanceField.setDisable(true);

        // final balance
        finalBalanceLabel = new Text("Final Balance");
        finalBalanceField = new Label("$ " + Variable.id.getBalance());

        // total deduction
        deductionLabel = new Text("Deduction");
        deductionField = new TextField("0.0");
        deductionField.setDisable(true);

        // set local variables for balance and calculation
        AtomicReference<Double> balance = new AtomicReference<>(Variable.id.getBalance());
        AtomicReference<Double> res = new AtomicReference<>((double) 0);
        AtomicReference<Double> charge = new AtomicReference<>((double) 0);

        // preview calculation
        deductionField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    Utility.validate(balance.get(), deductionField.getText());
                    charge.set(Double.valueOf(deductionField.getText()));
                    res.set(balance.get() - charge.get());
                    Utility.setMessage(finalBalanceField, 0, 0, 0, "$ " + res.get());
                } catch (Utility.NegativeException | Utility.ExceedBalanceException | AssertionError e) {
                    Utility.setMessage(finalBalanceField, 1, 0, 0, e.getMessage());
                }
            }
        });

        // Submit and Exit Button
        Button submit = new Button("Submit");
        EventHandler<ActionEvent> submitAction = e -> {
            try {
                Variable.id = new ID("Donovan", "Zhong");
                Utility.validate(balance.get(), deductionField.getText());
                charge.set(Double.valueOf(deductionField.getText()));
                res.set(balance.get() - charge.get());
                Utility.setMessage(finalBalanceField, 0, 0, 0, "$ " + res.get());
            } catch (Utility.ExceedBalanceException | Utility.NegativeException | AssertionError e1) {
                Utility.setMessage(finalBalanceField, 1, 0, 0, e1.getMessage());
            }
        };
        submit.setOnAction(submitAction);

        Button exit = new Button("Exit");
        EventHandler<ActionEvent> exitEvent = e -> System.exit(0);
        exit.setOnAction(exitEvent);

        // adding left element 1
        leftPane.add(encryptedIDLabel, x, y - 3);
        leftPane.add(decryptedIDLabel, x, y - 2);
        leftPane.add(firstNameLabel, x, y + 2);
        leftPane.add(lastNameLabel, x, y + 3);
        leftPane.add(emailLabel, x, y + 4);
        leftPane.add(balanceLabel, x, y + 8);
        leftPane.add(deductionLabel, x, y + 9);
        leftPane.add(finalBalanceLabel, x, y + 11);

        // adding corresponding elements
        leftPane.add(encryptedID, x + 1, y - 3);
        leftPane.add(decryptedID, x + 1, y - 2);
        leftPane.add(firstNameField, x + 1, y + 2);
        leftPane.add(lastNameField, x + 1, y + 3);
        leftPane.add(emailField, x + 1, y + 4);
        leftPane.add(balanceField, x + 1, y + 8);
        leftPane.add(deductionField, x + 1, y + 9);
        leftPane.add(finalBalanceField, x + 1, y + 11);

        // Arranging all the nodes in the grid
        leftPane.add(submit, x, 20);
        leftPane.add(exit, x + 1, 20);
        return leftPane;
    }

    private Pane createRightPane() {
        /*
         * Grid Pane #2, East, Display Video Stream
         * Right side pane on main pane
         */
        GridPane rightPane = initializeGridPane();

        // adding video container by converting swing node
        final SwingNode videoNode = new SwingNode();
        createSwingContent(videoNode, Video.getStreamAsPanel());
        StackPane streamHolder = new StackPane();
        streamHolder.getChildren().add(videoNode);

        // creating buttons for operation
        Button reScan = new Button("Scan");
        EventHandler<ActionEvent> reScanProcess = e -> {
            if (!Variable.scanRunning) {
                new Video.ProcessStream();
                videoStatus.setText("Scan starting...");
                videoStatus.setTextFill(Color.color(0, 0, 0));
            } else {
                videoStatus.setText("Scan already running! Cannot initialize another scan before this one finishes");
                videoStatus.setTextFill(Color.color(1, 0, 0));
            }
        };
        reScan.setOnAction(reScanProcess);

        Button pauseButton = new Button("Pause");
        EventHandler<ActionEvent> pauseProcess = e -> Video.pause();
        pauseButton.setOnAction(pauseProcess);

        Button resumeButton = new Button("Resume");
        EventHandler<ActionEvent> resumeProcess = e -> Video.run();
        resumeButton.setOnAction(resumeProcess);

        videoStatus = new Label("No scan running at the moment");

        // add buttons
        GridPane southEast = new GridPane();
        southEast.setVgap(Constant.vGap);
        southEast.add(reScan, x, y - 3);
        southEast.add(pauseButton, x, y + 4);
        southEast.add(resumeButton, x, y + 5);

        // adding main stream element
        rightPane.add(streamHolder, x, y - 3);
        rightPane.add(videoStatus, x, y - 2);
        rightPane.add(southEast, x + 1, y - 3);
        return rightPane;
    }

    // converts swing component into FX node
    private void createSwingContent(final SwingNode swingNode, JComponent comp) {
        SwingUtilities.invokeLater(() -> swingNode.setContent(comp));
    }

    private GridPane initializeGridPane() {
        GridPane pane = new GridPane();
        pane.setMinSize(Constant.minWidth, Constant.minHeight);
        pane.setPadding(new Insets(Constant.padding));
        pane.setVgap(Constant.vGap);
        pane.setHgap(Constant.hGap);
        pane.setGridLinesVisible(false);
        pane.setAlignment(Pos.TOP_LEFT);

        return pane;
    }

    public static void show() {
        launch();
    }
}
