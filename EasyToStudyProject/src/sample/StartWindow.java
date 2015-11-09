package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Владислав on 09.11.2015.
 */
public class StartWindow {

    private static Stage stage;

    StartWindow(Stage primaryStage)
    {
        stage = primaryStage;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Button button = (Button)root.lookup("#button");
        //button.setText("zima");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getStage(){
        return stage;
    }
}
