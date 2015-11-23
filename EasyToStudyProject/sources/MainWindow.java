package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Владислав on 09.11.2015.
 */
public class MainWindow {
    private static MainWindow instance;
    private static Scene scene;
    private static TableView<StatisticsInfo> recentLabsTable;
    private ObservableList<StatisticsInfo> statisticsRecentLabsList;
    private Parent root;
    private TableColumn firstRecentColumn;
    private TableColumn secondRecentColumn;
    private TableColumn thirdRecentColumn;

    private MainWindow() {
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        scene = new Scene(root);
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchorPane");

        recentLabsTable = (TableView<StatisticsInfo>)root.lookup("#recentLabsTable");
        statisticsRecentLabsList = FXCollections.observableArrayList();
        initRecentLabsTable();
        firstRecentColumn = new TableColumn("Subject");
        firstRecentColumn.setCellValueFactory(new PropertyValueFactory<StatisticsInfo, String>("firstColumn"));
        secondRecentColumn = new TableColumn("#");
        secondRecentColumn.setCellValueFactory(new PropertyValueFactory<StatisticsInfo, String>("secondColumn"));
        thirdRecentColumn = new TableColumn("Status");
        thirdRecentColumn.setCellValueFactory((new PropertyValueFactory<StatisticsInfo, String>("thirdColumn")));
        recentLabsTable.setItems(statisticsRecentLabsList);
        firstRecentColumn.setPrefWidth(90);
        secondRecentColumn.setPrefWidth(32);
        thirdRecentColumn.setPrefWidth(90);
        recentLabsTable.getColumns().addAll(firstRecentColumn, secondRecentColumn, thirdRecentColumn);

        Timeline.getInstance().getTimeLine().setLayoutX(55);
        Timeline.getInstance().getTimeLine().setLayoutY(100);
        anchorPane.getChildren().add(Timeline.getInstance().getTimeLine());
    }

    public static MainWindow getInstance() {
        if (instance == null)
            instance = new MainWindow();
        return instance;
    }

    protected void initRecentLabsTable() {
        statisticsRecentLabsList.clear();
        statisticsRecentLabsList.addAll(DataLists.getInstance().getRecentLabsList());
    }

    protected void synchronize() {
        initRecentLabsTable();
        recentLabsTable.setItems(statisticsRecentLabsList);
        recentLabsTable.getColumns().clear();
        recentLabsTable.getColumns().addAll(firstRecentColumn, secondRecentColumn, thirdRecentColumn);
    }

    protected void show() {
        Main.getStage().setScene(scene);
    }
}
