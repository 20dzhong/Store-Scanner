package Test;

import StoreScanner.GUI.Video;
import StoreScanner.utils.Constant;
import StoreScanner.utils.Variable;
import javafx.application.Application;
import javafx.beans.NamedArg;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import javax.swing.*;
//import java.awt.*;
// import java.awt.Color;
import javafx.scene.paint.*;
import java.util.EventObject;

public class FXTest extends Application {

    private int x = 0;
    private int y = 4;

    private double opacity = 0.8;

    @Override
    public void start(Stage stage) {


        /*
         * Grid Pane #1, West, Display Information
         * Left side pane
         */
        GridPane leftPane = initializeGridPane();

        // encrypted id grid
        Text eID = new Text("Encrypted ID");
        Text eIDText = new Text(Variable.id.encryptedIdentifier());
        eIDText.setOpacity(opacity - 0.3);
        eIDText.setDisable(true);


        // non encrypted id
        Text nEID = new Text("Decrypted ID");
        Text nEIDText = new Text(Variable.id.getIdentifier());
        nEIDText.setOpacity(opacity - 0.3);
        nEIDText.setDisable(true);

        // first name grid
        Text fName = new Text("First Name");
        TextField fNText = new TextField(Variable.id.getFirstName());
        fNText.setOpacity(opacity);
        fNText.setDisable(true);

        // last name grid
        Text lName = new Text("Last Name");
        TextField lNText = new TextField(Variable.id.getLastName());
        lNText.setOpacity(opacity);
        lNText.setDisable(true);

        // account balance
        Text bName = new Text("Account Balance");
        TextField bField = new TextField(Variable.id.getBalance());
        bField.setStyle("-fx-text-inner-color: green;");

        bField.setOpacity(1.0);
        bField.setDisable(true);

        //Creating Buttons
        Button submit = new Button("Submit");
        EventHandler<ActionEvent> submitAction = e -> System.out.println("REEEEEEEE");
        submit.setOnAction(submitAction);

        Button exit = new Button("Exit");
        EventHandler<ActionEvent> exitEvent = e -> System.exit(0);
        exit.setOnAction(exitEvent);

        // adding left element 1
        leftPane.add(eID, x, y - 3);
        leftPane.add(nEID, x, y - 2);
        leftPane.add(fName, x, y + 2);
        leftPane.add(lName, x, y + 3);
        leftPane.add(bName, x, y + 6);

        // adding corresponding elements
        leftPane.add(eIDText, x + 1, y - 3);
        leftPane.add(nEIDText, x + 1, y - 2);
        leftPane.add(fNText, x + 1, y + 2);
        leftPane.add(lNText, x + 1, y + 3);
        leftPane.add(bField, x + 1, y + 6);


        // Arranging all the nodes in the grid
        leftPane.add(submit, x, 20);
        leftPane.add(exit, x + 1, 20);


        /*
         * Grid Pane #2, East, Display Video Stream
         * Right side pane on main pane
         */
        GridPane rightPane = initializeGridPane();

        // adding video container by converting swing node
        final SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
        StackPane streamHolder = new StackPane();
        streamHolder.getChildren().add(swingNode);

        // creating buttons for operation
        Button reScan = new Button("Rescan");
        EventHandler<ActionEvent> reScanProcess = e -> {
            if (!Variable.scanRunning) {
                new Video.ProcessStream();
                Variable.status.setText("Scan started");
                Variable.status.setTextFill(Color.color(0,0,0));
            }
            else {
                Variable.status.setText("Scan already running! Cannot initialize another scan before this one finishes");
                Variable.status.setTextFill(Color.color(1,0,0));
            }

        };
        reScan.setOnAction(reScanProcess);

        Button pauseButton = new Button("Pause");
        EventHandler<ActionEvent> pauseProcess = e -> Video.pause();
        pauseButton.setOnAction(pauseProcess);

        Button resumeButton = new Button("Resume");
        EventHandler<ActionEvent> resumeProcess = e -> Video.run();
        resumeButton.setOnAction(resumeProcess);


        // add buttons
        GridPane southEast = new GridPane();
        southEast.setVgap(Constant.vGap);
        southEast.add(reScan, x, y - 3);
        southEast.add(pauseButton, x, y + 4);
        southEast.add(resumeButton, x, y + 5);

        // adding main stream element
        rightPane.add(streamHolder, x, y - 3);
        rightPane.add(Variable.status, x, y - 2);
        rightPane.add(southEast, x + 1, y - 3);


        /*
         * Final Main Pane
         * Combine both pane
         */
        GridPane mainPane = new GridPane();
        // mainPane.setGridLinesVisible(true);
        mainPane.add(leftPane, 0, 0);
        mainPane.add(rightPane, 1, 0);


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

    // converts swing component into FX node
    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            swingNode.setContent(Video.getFrameAsPanel());
            Video.getFrameAsPanel();
        });
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


    public static void main(String args[]) {
        launch(args);
    }
} 
