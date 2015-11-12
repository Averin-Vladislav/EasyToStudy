package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Владислав on 12.11.2015.
 */
public class TimelineButton {
    static private EventDate eventDate;
    static private ArrayList<String> eventsList;
    static private Button dayButton;
    static private Tooltip tooltip;

    TimelineButton(int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + number);

        eventDate = new EventDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dayButton = new Button();
        dayButton.setPrefWidth(15);
        tooltip = new Tooltip();
        tooltip.setText(eventDate.getInfo());
        dayButton.setTooltip(tooltip);
    }

    public static Button getDayButton() {
        return dayButton;
    }
}