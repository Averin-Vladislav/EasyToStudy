package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        StartWindow startPage = new StartWindow(primaryStage);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
