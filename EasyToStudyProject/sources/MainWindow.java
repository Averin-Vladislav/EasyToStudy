package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private Button noteTipButton;
    private Button passedTipButton;
    private Button deadlineTipButton;
    private Button absenceTipButton;
    private Button infoSetTipButton;

    private Label noteTipLabel;
    private Label passedTipLabel;
    private Label deadlineTipLabel;
    private Label absenceTipLabel;
    private Label infoSetTipLabel;

    private MainWindow() {

        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        scene = new Scene(root);
        scene.getStylesheets().add("sample/main.css");
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

        noteTipButton = new Button();
        noteTipButton.getStyleClass().add("noteDayButton");
        noteTipButton.setLayoutX(20);
        noteTipButton.setLayoutY(10);
        noteTipButton.setPrefWidth(15);
        noteTipLabel = new Label("- note");
        noteTipLabel.setLayoutX(40);
        noteTipLabel.setLayoutY(10);

        passedTipButton = new Button();
        passedTipButton.getStyleClass().add("passedDayButton");
        passedTipButton.setLayoutX(20);
        passedTipButton.setLayoutY(40);
        passedTipButton.setPrefWidth(15);
        passedTipLabel = new Label("- passed lab");
        passedTipLabel.setLayoutX(40);
        passedTipLabel.setLayoutY(40);

        deadlineTipButton = new Button();
        deadlineTipButton.getStyleClass().add("deadlineDayButton");
        deadlineTipButton.setLayoutX(20);
        deadlineTipButton.setLayoutY(70);
        deadlineTipButton.setPrefWidth(15);
        deadlineTipLabel = new Label("- deadline");
        deadlineTipLabel.setLayoutX(40);
        deadlineTipLabel.setLayoutY(70);

        absenceTipButton = new Button();
        absenceTipButton.getStyleClass().add("absenceDayButton");
        absenceTipButton.setLayoutX(20);
        absenceTipButton.setLayoutY(100);
        absenceTipButton.setPrefWidth(15);
        absenceTipLabel = new Label("- absence");
        absenceTipLabel.setLayoutX(40);
        absenceTipLabel.setLayoutY(100);

        infoSetTipButton = new Button();
        infoSetTipButton.getStyleClass().add("infoSetButton");
        infoSetTipButton.setLayoutX(20);
        infoSetTipButton.setLayoutY(130);
        infoSetTipButton.setPrefWidth(15);
        infoSetTipLabel = new Label("- set of events");
        infoSetTipLabel.setLayoutX(40);
        infoSetTipLabel.setLayoutY(130);

        Timeline.getInstance().getTimeLine().setLayoutX(50);
        Timeline.getInstance().getTimeLine().setLayoutY(160);
        anchorPane.getChildren().add(Timeline.getInstance().getTimeLine());
        anchorPane.getChildren().addAll(noteTipButton, passedTipButton, deadlineTipButton, absenceTipButton, infoSetTipButton,
                                        noteTipLabel, passedTipLabel, deadlineTipLabel, absenceTipLabel, infoSetTipLabel);
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
