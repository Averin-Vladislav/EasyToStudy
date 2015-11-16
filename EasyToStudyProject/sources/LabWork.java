package sample;

/**
 * Created by Владислав on 12.11.2015.
 */
public class LabWork {
    private EventDate deadLineDate;
    private EventDate passDate;
    private String mark;
    private Integer number;

    LabWork(Integer _number) {
        deadLineDate = new EventDate(0, 0);
        passDate = new EventDate(0, 0);
        mark = "-";
        number = _number;
    }

    public void setMark(String _mark) {
        mark =_mark;
    }

    public void setDeadLineDate(EventDate _deadLineDate) {
        deadLineDate = _deadLineDate;
    }

    public void setPassDate(EventDate _passDate) {
        passDate = _passDate;
    }

    public Integer getNumber() {
        return number;
    }

    public EventDate getDeadLineDate() {
        return deadLineDate;
    }

    public EventDate getPassDate() {
        return passDate;
    }

    public String getMark() {
        return mark;
    }
}
