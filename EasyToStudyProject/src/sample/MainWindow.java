package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Created by Владислав on 09.11.2015.
 */
public class MainWindow {
    MainWindow() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Button button = (Button)root.lookup("#button");
        //button.setText("zima");



        Scene scene = new Scene(root);
        StartWindow.getStage().setScene(scene);
        StartWindow.getStage().show();
    }
}
