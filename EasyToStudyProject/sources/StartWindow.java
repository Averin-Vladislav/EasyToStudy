package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Created by ????????? on 09.11.2015.
 */
public class StartWindow {
    StartWindow()
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }
}
