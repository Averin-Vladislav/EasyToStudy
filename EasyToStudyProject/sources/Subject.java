package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Владислав on 12.11.2015.
 */
public class Subject {
    private ArrayList<LabWork> labList;
    private String name;
    private ArrayList<EventDate> absenceList;

    Subject(Integer _count, String _name) {
        name = _name;
        labList = new ArrayList<>();
        absenceList = new ArrayList<>();

        for(int i = 0; i < _count; i++) {
            labList.add(new LabWork(i + 1));
        }

        Scanner inAbsences;
        try {
            inAbsences = new Scanner(new File("D:\\Files\\subjects\\absences\\" + name + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        while (inAbsences.hasNext()) {
            String str;
            str = inAbsences.nextLine();
            if(Objects.equals(str, ""))
                break;
            absenceList.add(new EventDate(Integer.parseInt(String.valueOf(str.charAt(3)) + String.valueOf(str.charAt(4))),
                                          Integer.parseInt(String.valueOf(str.charAt(0)) + String.valueOf(str.charAt(1)))));
        }
        inAbsences.close();

        Scanner inMarks;
        try {
            inMarks = new Scanner(new File("D:\\Files\\subjects\\marks\\" + name + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        for (LabWork temp : labList) {
            if (inMarks.hasNext()) {
                String str;
                str = inMarks.nextLine();
                if (str.length() < 2)
                    break;
                temp.setDeadLineDate(new EventDate(Integer.parseInt(String.valueOf(str.charAt(3)) + String.valueOf(str.charAt(4))),
                        Integer.parseInt(String.valueOf(str.charAt(0) + String.valueOf(str.charAt(1))))));
                temp.setPassDate(new EventDate(Integer.parseInt(String.valueOf(str.charAt(9)) + String.valueOf(str.charAt(10))),
                        Integer.parseInt(String.valueOf(str.charAt(6) + String.valueOf(str.charAt(7))))));
                temp.setMark(str.substring(12));
            } else {
                temp.setMark("-");
                temp.setPassDate(new EventDate(0, 0));
                temp.setDeadLineDate(new EventDate(0, 0));
            }
        }
        inMarks.close();
    }

    public ArrayList<LabWork> getLabList() {
        return labList;
    }

    public ArrayList<EventDate> getAbsenceList() {
        return absenceList;
    }

    public String getName() {
        return name;
    }
}
