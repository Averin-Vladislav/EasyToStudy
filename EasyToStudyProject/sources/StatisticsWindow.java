package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

/**
 * Created by Владислав on 12.11.2015.
 */
public class StatisticsWindow {
    private Scene scene;
    private static StatisticsWindow instance;
    private TableView<StatisticsInfo> absenceTable;
    private TableView<StatisticsInfo> markTable;
    private ObservableList<StatisticsInfo> statisticsAbsenceList;
    private ObservableList<StatisticsInfo> statisticsMarksList;

    private StatisticsWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("StatisticsWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        absenceTable = (TableView<StatisticsInfo>)root.lookup("#absencesTable");
        markTable = (TableView<StatisticsInfo>)root.lookup("#marksTable");
        statisticsAbsenceList = FXCollections.observableArrayList();
        statisticsMarksList = FXCollections.observableArrayList();
        initAbsenceTable();


        TableColumn firstAbsenceColumn = new TableColumn("Subject");
        firstAbsenceColumn.setCellValueFactory(new PropertyValueFactory<StatisticsInfo, String>("firstColumn"));
        TableColumn secondAbsenceColumn = new TableColumn("Count");
        secondAbsenceColumn.setCellValueFactory(new PropertyValueFactory<StatisticsInfo, String>("secondColumn"));
        absenceTable.setItems(statisticsAbsenceList);
        firstAbsenceColumn.setPrefWidth(150);
        secondAbsenceColumn.setPrefWidth(48);
        absenceTable.getColumns().addAll(firstAbsenceColumn, secondAbsenceColumn);

        TableColumn firstMarkColumn = new TableColumn("#");
        firstMarkColumn.setCellValueFactory(new PropertyValueFactory<StatisticsInfo, String>("firstColumn"));
        TableColumn secondMarkColumn = new TableColumn("Mark");
        secondMarkColumn.setCellValueFactory(new PropertyValueFactory<StatisticsInfo, String>("secondColumn"));
        markTable.setItems(statisticsMarksList);
        firstMarkColumn.setPrefWidth(33);
        secondMarkColumn.setPrefWidth(100);
        markTable.getColumns().addAll(firstMarkColumn, secondMarkColumn);

        ComboBox<String> comboBox;
        comboBox = (ComboBox)root.lookup("#subjectComboBox");
        ObservableList<String> observableList = FXCollections.observableArrayList(DataLists.getInstance().getSubjectNameList());
        comboBox.setItems(observableList);
        comboBox.setPromptText("Choose subject...");
        comboBox.setOnHiding(event -> { if(comboBox.getValue() != null) initMarkTable(comboBox.getValue());});

        scene = new Scene(root);
    }

    public static StatisticsWindow getInstance() {
        if (instance == null)
            instance = new StatisticsWindow();
        return instance;
    }

    protected void show() {
        Main.getStage().setScene(scene);
    }

    protected void initAbsenceTable() {
        statisticsAbsenceList.clear();
        statisticsAbsenceList.addAll(DataLists.getInstance().getStatisticsAbsenceList());
    }

    protected void initMarkTable(String subjectName) {
        statisticsMarksList.clear();
        statisticsMarksList.addAll(DataLists.getInstance().getStatisticsMarkList(subjectName));
    }
}
