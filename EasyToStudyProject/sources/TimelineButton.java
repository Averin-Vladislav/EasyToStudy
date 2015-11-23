package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.util.Calendar;

/**
 * Created by Владислав on 12.11.2015.
 */
public class TimelineButton {
    private EventDate eventDate;
    private Button dayButton;
    private Tooltip tooltip;
	private boolean isInfoSet;

    TimelineButton(Integer number) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + number - 3);

        eventDate = new EventDate(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        dayButton = new Button();
        dayButton.setPrefWidth(15);
        tooltip = new Tooltip();
        tooltip.setText(eventDate.getInfo());
        dayButton.setTooltip(tooltip);
    }

    public Button getDayButton() {
        return dayButton;
    }

    public void initializeTooltip(){
        tooltip.setText(eventDate.getInfo());
    }

    public void addInfoToTooltip(String info){
        tooltip.setText(tooltip.getText() + info);
    }

    public EventDate getTimelineButtonDate(){
        return eventDate;
    }

    public String getTooltipText() {
        return tooltip.getText();
    }

	public void setInfoSet(boolean value) {
		isInfoSet = value;
	}

	public boolean isInfoSet() {
		return isInfoSet;
	} 
}