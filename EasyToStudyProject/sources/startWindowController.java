package sample;

import javafx.event.ActionEvent;

public class startWindowController {
    public void onOrganize(ActionEvent actionEvent) {
        MainWindow.getInstance().show();
        DataLists.getInstance();
        Timeline.getInstance().synchronize();
        DataLists.getInstance().synchronizeWithFile();
    }
}
