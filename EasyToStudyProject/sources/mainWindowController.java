package sample;

import javafx.event.ActionEvent;

/**
 * Created by Владислав on 12.11.2015.
 */
public class mainWindowController {
    public void onAdd(ActionEvent actionEvent) {
        ManageInfo manageInfo = new ManageInfo(false);
    }

    public void onEdit(ActionEvent actionEvent) {
        ManageInfo manageInfo = new ManageInfo(true);
    }

    public void onExit(ActionEvent actionEvent) {
        Main.getStage().close();
    }

    public void onStatistics(ActionEvent actionEvent) {
        StatisticsWindow.getInstance().show();
        StatisticsWindow.getInstance().initAbsenceTable();
    }
}
