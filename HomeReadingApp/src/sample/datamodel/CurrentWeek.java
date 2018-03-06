package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CurrentWeek {

    public ObservableList<LocalDate> currentWeek(ClassGroup classGroup) {
        LocalDate today = LocalDate.now();
        ObservableList<LocalDate> currentWeek = FXCollections.observableArrayList();
        for (int i = 0; i < classGroup.getStudyYear().getLengthOfYear(); i++) {
//            final int index = i;
            LocalDate tempDate = classGroup.getStudyYear().getListOfWeeks().get(i);
            ObservableList<LocalDate> tempWeek = FXCollections.observableArrayList();
            if (tempDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                for (int j = 0; j < 7; j++) {
                    tempWeek.add(tempDate.plusDays(j));
                }
            } else if (tempDate.getDayOfWeek().equals(DayOfWeek.TUESDAY)) {
                for (int j = -1; j < 6; j++) {
                    tempWeek.add(tempDate.plusDays(j));
                }
            } else if (tempDate.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {
                for (int j = -2; j < 5; j++) {
                    tempWeek.add(tempDate.plusDays(j));
                }
            } else if (tempDate.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
                for (int j = -3; j < 4; j++) {
                    tempWeek.add(tempDate.plusDays(j));
                }
            } else if (tempDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                for (int j = -4; j < 3; j++) {
                    tempWeek.add(tempDate.plusDays(j));
                }
            } else if (tempDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                for (int j = -5; j < 2; j++) {
                    tempWeek.add(tempDate.plusDays(j));
                }
            } else if (tempDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                for (int j = -6; j < 1; j++) {
                    tempWeek.add(tempDate.plusDays(j));
                }
            }
            if (tempWeek.contains(today)) {
                currentWeek.addAll(tempWeek);
            }
        }
        return currentWeek;
    }
}



