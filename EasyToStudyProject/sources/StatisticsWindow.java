package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Created by Владислав on 12.11.2015.
 */
public class StatisticsWindow {
    private static StatisticsWindow instance;
    private static Scene scene;
    private static Parent root;

    private StatisticsWindow() {
        root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("StatisticsWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(root);
    }

    public static StatisticsWindow getInstance() {
        if (instance == null)
            instance = new StatisticsWindow();
        return instance;
    }

    protected static void show() {
        Main.getStage().setScene(scene);
    }
}
