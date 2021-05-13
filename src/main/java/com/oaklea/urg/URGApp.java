package com.oaklea.urg;

import com.oaklea.urg.controller.Controller;
import com.oaklea.urg.view.scenes.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * An application for playing "Untitled Rhythm Game"
 * 
 * @author Aidan Oakley
 * @version 1.0.0
 */
public class URGApp extends Application {

    Stage mainStage;
    MainMenu mainMenu;
    Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application
     * 
     * @author Aidan Oakley
     * @version 1.0.0
     * @param primaryStage the stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainStage = primaryStage;
        this.mainStage.setTitle("URG");
        this.controller = new Controller(this.mainStage);
    }

}
