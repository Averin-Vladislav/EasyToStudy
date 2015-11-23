package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;



    public void start(Stage primaryStage) {
        stage  = primaryStage;
        new StartWindow();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    public static Stage getStage() {
        return stage;
    }
}
