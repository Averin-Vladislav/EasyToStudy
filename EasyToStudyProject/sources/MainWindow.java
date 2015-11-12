package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Владислав on 09.11.2015.
 */
public class MainWindow {
    private static MainWindow instance;
    private static Scene scene;
    private Parent root;
    private AnchorPane anchorPane;

    private MainWindow() {
        root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(root);
        anchorPane = (AnchorPane)root.lookup("#anchorPane");

        Timeline.getInstance().getTimeLine().setLayoutX(55);
        Timeline.getInstance().getTimeLine().setLayoutY(100);

        anchorPane.getChildren().add(Timeline.getInstance().getTimeLine());
    }

    public static MainWindow getInstance() {
        if (instance == null)
            instance = new MainWindow();
        return instance;
    }

    protected static void show() {
        Main.getStage().setScene(scene);
    }
}
