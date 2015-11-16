package sample;

/**
 * Created by Владислав on 12.11.2015.
 */
public class Note {
    private EventDate eventDate;
    private String info;

    Note(EventDate _eventDate, String _info) {
        eventDate = _eventDate;
        info = _info;
    }

    public void setEventDate(EventDate _eventDate) {
        eventDate = _eventDate;
    }

    public void setInfo(String _info) {
        info = _info;
    }

    public EventDate getEventDate() {
        return eventDate;
    }

    public String getInfo() {
        return info;
    }
}