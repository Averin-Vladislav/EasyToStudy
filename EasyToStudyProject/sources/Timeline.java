package sample;

import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Владислав on 12.11.2015.
 */
public class Timeline {
    private static Timeline instance;
    private static ArrayList<TimelineButton> timelineButtonList;
    private static HBox timeLine;
    private static Date date;

    private Timeline() {
        timeLine = new HBox();
        date = new Date();

        timelineButtonList = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            TimelineButton timeLineButton = new TimelineButton(i);
            timelineButtonList.add(timeLineButton);
            timeLine.getChildren().add(timeLineButton.getDayButton());
        }
    }

    public static Timeline getInstance() {
        if(instance == null)
            instance = new Timeline();
        return instance;
    }

    protected static HBox getTimeLine() {
        return timeLine;
    }
}
