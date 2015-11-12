package sample;

/**
 * Created by Владислав on 12.11.2015.
 */
public class EventDate {
    private static int month;
    private static int day;

    EventDate(int _month, int _day) {
        month = _month + 1;
        day = _day;
    }

    public static int getMonth() {
        return month;
    }

    public static int getDay() {
        return day;
    }

    public static void setMonth(int _month) {
        month = _month;
    }

    public static void setDay(int _day) {
        day = _day;
    }

    public static String getInfo() {
        String info = "";

        switch (month)
        {
            case 1: info += "January";
                break;
            case 2: info += "February";
                break;
            case 3: info += "March";
                break;
            case 4: info += "April";
                break;
            case 5: info += "May";
                break;
            case 6: info += "June";
                break;
            case 7: info += "July";
                break;
            case 8: info += "August";
                break;
            case 9: info += "September";
                break;
            case 10: info += "October";
                break;
            case 11: info += "November";
                break;
            case 12: info += "December";
                break;
        }
        info += " " + day;

        return info;
    }

    public boolean equal(EventDate eventDate) {
        return (this.month == eventDate.month) && (this.day == eventDate.day) ? true : false;
    }
}
