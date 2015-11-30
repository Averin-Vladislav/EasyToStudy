package sample;

import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Владислав on 12.11.2015.
 */
public class Timeline {
    private static Timeline instance;
    private ArrayList<TimelineButton> timelineButtonList;
    private HBox timeLine;
    private EventDate startDate;
    private EventDate endDate;

    private Timeline() {


        timeLine = new HBox();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 3);
        startDate = new EventDate(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 30);
        endDate = new EventDate(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

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

    protected HBox getTimeLine() {
        return timeLine;
    }

    protected void synchronize(){
        for(TimelineButton temp: timelineButtonList) {
            temp.initializeTooltip();
			temp.setInfoSet(false);
            while(temp.getDayButton().getStyleClass().size() > 1)
                temp.getDayButton().getStyleClass().remove(temp.getDayButton().getStyleClass().size() - 1);
        }

        ArrayList<Note> noteList = DataLists.getInstance().getNoteList();
        for(Note note: noteList) {
            TimelineButton timelineButton = getButtonInInterval(note.getEventDate());
            if (timelineButton != null) {
                timelineButton.addInfoToTooltip("\nNote:\n\t" + note.getInfo());
				if(timelineButton.isInfoSet()) {
					timelineButton.getDayButton().getStyleClass().add("infoSetButton");
				}
				else {
                    timelineButton.getDayButton().getStyleClass().add("noteDayButton");
                    timelineButton.setInfoSet(true);
                }
            }
        }

        ArrayList<Subject> subjectList = DataLists.getInstance().getSubjectList();
        for(Subject subject: subjectList) {
            for(EventDate eventDate: subject.getAbsenceList()) {
                TimelineButton timelineButton = getButtonInInterval(eventDate);
                if (timelineButton != null) {
                    timelineButton.addInfoToTooltip("\nAbsence:\n\t" + subject.getName());
					if(timelineButton.isInfoSet()) {
						timelineButton.getDayButton().getStyleClass().add("infoSetButton");
					}
					else {
                        timelineButton.getDayButton().getStyleClass().add("absenceDayButton");
                        timelineButton.setInfoSet(true);
                    }
                }
            }
        }

        for(Subject subject: subjectList) {
            for(LabWork labWork: subject.getLabList()) {
                TimelineButton timelineButton = getButtonInInterval(labWork.getDeadLineDate());
                if (timelineButton != null) {
                    timelineButton.addInfoToTooltip("\nDeadline:\n\t" + subject.getName());
					if(timelineButton.isInfoSet()) {
						timelineButton.getDayButton().getStyleClass().add("infoSetButton");
					}
					else {
						timelineButton.getDayButton().getStyleClass().add("deadlineDayButton");
                        timelineButton.setInfoSet(true);
                    }
                }
            }
        }

        for(Subject subject: subjectList) {
            for(LabWork labWork: subject.getLabList()) {
                TimelineButton timelineButton = getButtonInInterval(labWork.getPassDate());
                if (timelineButton != null) {
                    timelineButton.addInfoToTooltip("\nPassed:\n\t" + subject.getName());
					if(timelineButton.isInfoSet()) {
						timelineButton.getDayButton().getStyleClass().add("infoSetButton");
					}
					else {
                        timelineButton.getDayButton().getStyleClass().add("passedDayButton");
                        timelineButton.setInfoSet(true);
                    }
                }
            }
        }
    }

    private TimelineButton getButtonInInterval(EventDate eventDate){
        if (eventDate.moreEqualThan(startDate) && eventDate.lessThan(endDate)) {
            for (TimelineButton button : timelineButtonList) {
                if (button.getTimelineButtonDate().equal(eventDate))
                    return button;
            }
        }
        return null;
    }
}
