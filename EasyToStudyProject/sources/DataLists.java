package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Владислав on 12.11.2015.
 */
public class DataLists {
    private static DataLists instance;
    private ArrayList<Note> noteArrayList;
    private ArrayList<Subject> subjectArrayList;

    private DataLists() {
        noteArrayList = new ArrayList<>();
        subjectArrayList = new ArrayList<>();
        Scanner inNote;
        try {
            inNote = new Scanner(new File("D:\\Files\\note.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        while(inNote.hasNext()) {
            String str;
            str = inNote.nextLine();
            if(Objects.equals(str, ""))
                break;
            Integer month = Integer.parseInt(String.valueOf(str.charAt(3)) + String.valueOf(str.charAt(4)));
            Integer day = Integer.parseInt(String.valueOf(str.charAt(0)) + String.valueOf(str.charAt(1)));
            EventDate eventDate = new EventDate(month, day);
            Note note = new Note(eventDate, str.substring(6));
            noteArrayList.add(note);
        }
        inNote.close();

        Scanner inSubject;
        try {
            inSubject = new Scanner(new File("D:\\Files\\subject.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        while(inSubject.hasNext()) {
            String str;
            str = inSubject.nextLine();
            if(Objects.equals(str, ""))
                break;
            subjectArrayList.add(new Subject(Integer.parseInt(String.valueOf(str.charAt(0) + String.valueOf(str.charAt(1)))), str.substring(3)));
        }
        inSubject.close();

    }

    public static DataLists getInstance() {
        if(instance == null) {
            instance = new DataLists();
        }
        return instance;
    }

    protected ArrayList<Note> getNoteList() {
        return noteArrayList;
    }

    protected ArrayList<String> getNoteListDates() {
        ArrayList<String> noteListDates = new ArrayList<>();
        for (Note note: noteArrayList) {
            noteListDates.add(note.getEventDate().getInfo());
        }
        return noteListDates;
    }

    protected ArrayList<Subject> getSubjectList() {
        return subjectArrayList;
    }

    protected ArrayList<String> getSubjectNameList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for(Subject subject: subjectArrayList) {
            arrayList.add(subject.getName());
        }
        return arrayList;
    }

    protected ArrayList<StatisticsInfo> getStatisticsAbsenceList() {
        ArrayList<StatisticsInfo> statisticsInfoList = new ArrayList<>();
        for(Subject subject: subjectArrayList) {
            statisticsInfoList.add(new StatisticsInfo(subject.getName(), Integer.toString(subject.getAbsenceList().size())));
        }
        return statisticsInfoList;
    }

    protected ArrayList<StatisticsInfo> getStatisticsMarkList(String subjectName) {
        ArrayList<StatisticsInfo> statisticsInfoList = new ArrayList<>();
        for(Subject subject: subjectArrayList) {
            if(Objects.equals(subjectName, subject.getName())) {
                for(int i = 0; i < subject.getLabList().size(); i++) {
                    statisticsInfoList.add(new StatisticsInfo(Integer.toString(i + 1), subject.getLabList().get(i).getMark()));
                }
            }
        }
        return statisticsInfoList;
    }

    protected ArrayList<StatisticsInfo> getRecentLabsList() {
        ArrayList<StatisticsInfo> statisticsInfoList = new ArrayList<>();

        LabWork labWorkRecent1 = new LabWork(0);
        labWorkRecent1.setPassDate(new EventDate(0, 0));
        String labWorkRecentName1 = "";
        LabWork labWorkRecent2 = new LabWork(1);
        labWorkRecent2.setPassDate(new EventDate(0, 0));
        String labWorkRecentName2 = "";

        for(Subject subject: subjectArrayList) {
            for(LabWork labWork: subject.getLabList()) {
                if(labWork.getPassDate().moreEqualThan(labWorkRecent1.getPassDate())) {
                    labWorkRecent1 = labWork;
                    labWorkRecentName1 = subject.getName();
                    if(labWorkRecent1.getPassDate().moreEqualThan(labWorkRecent2.getPassDate())) {
                        LabWork labWorkBuffer;
                        String stringBuffer;

                        labWorkBuffer = labWorkRecent1;
                        labWorkRecent1 = labWorkRecent2;
                        labWorkRecent2 = labWorkBuffer;

                        stringBuffer = labWorkRecentName1;
                        labWorkRecentName1 = labWorkRecentName2;
                        labWorkRecentName2 = stringBuffer;
                    }
                }
            }
        }

        statisticsInfoList.add(new StatisticsInfo(labWorkRecentName2, Integer.toString(labWorkRecent2.getNumber()), labWorkRecent2.getMark()));
        statisticsInfoList.add(new StatisticsInfo(labWorkRecentName1, Integer.toString(labWorkRecent1.getNumber()), labWorkRecent1.getMark()));
        return statisticsInfoList;
    }

    protected ArrayList<LabWork> getLabList(String subjectName) {
        ArrayList<LabWork> labList = null;
        for (Subject subject: getSubjectList()) {
            if (Objects.equals(subject.getName(), subjectName))
                labList = subject.getLabList();
        }
        return labList;
    }

    protected ArrayList<String> getAbsenceStringList(String subjectName) {
        ArrayList<String> absenceList = new ArrayList<>();
        for (Subject subject: getSubjectList()) {
            if (Objects.equals(subject.getName(), subjectName)) {
                for (EventDate date: subject.getAbsenceList()) {
                        absenceList.add(date.getInfo());
                }
                break;
            }
        }
        return absenceList;
    }

    protected ArrayList<EventDate> getAbsenceList(String subjectName) {
        ArrayList<EventDate> absenceList = null;
        for (Subject subject: getSubjectList()) {
            if (Objects.equals(subject.getName(), subjectName)) {
                absenceList = subject.getAbsenceList();
            }
        }
        return absenceList;
    }

    protected void synchronizeWithFile() {
        try {
            PrintWriter printWriter = new PrintWriter("D:\\Files\\note.txt");
            for(Note note: noteArrayList) {
                printWriter.print(((note.getEventDate().getDay() < 10) ? '0' + note.getEventDate().getDay() : note.getEventDate().getDay()) + " "
                               + ((note.getEventDate().getMonth() < 10) ? '0' + note.getEventDate().getMonth() : note.getEventDate().getMonth()) + " "
                               +   note.getInfo());
                printWriter.println();
            }
            printWriter.close();

            for(Subject subject: getSubjectList()) {
                    printWriter = new PrintWriter("D:\\Files\\subjects\\marks\\" + subject.getName() + ".txt");
                    for(LabWork labWork: subject.getLabList()) {
                        printWriter.print(((labWork.getDeadLineDate().getDay() < 10) ? "0" + labWork.getDeadLineDate().getDay() : labWork.getDeadLineDate().getDay()) + " "
                                       + (((labWork.getDeadLineDate().getMonth() < 10) ? "0" + labWork.getDeadLineDate().getMonth() : labWork.getDeadLineDate().getMonth()) + " "
                                       + (((labWork.getPassDate().getDay() < 10) ? "0" + labWork.getPassDate().getDay() : labWork.getPassDate().getDay()) + " "
                                       + (((labWork.getPassDate().getMonth() < 10) ? "0" + labWork.getPassDate().getMonth() : labWork.getPassDate().getMonth()) + " "
                                       + labWork.getMark()))));
                        printWriter.println();
                    }
                    printWriter.close();
                    printWriter = new PrintWriter("D:\\Files\\subjects\\absences\\" + subject.getName() + ".txt");
                    for(EventDate eventDate: subject.getAbsenceList()) {
                        printWriter.print(((eventDate.getDay() < 10) ? "0" + eventDate.getDay() : eventDate.getDay()) + " "
                                + (((eventDate.getMonth() < 10) ? "0" + eventDate.getMonth() : eventDate.getMonth()) + " "));
                        printWriter.println();
                    }
                    printWriter.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
