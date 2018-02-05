package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class StudyYear {
    private LocalDate startOfYear;
    private int lengthOfYear;
    private ObservableList<LocalDate> listOfWeeks;


    public StudyYear(int year, int month, int day, int lengthOfYear) {
        this.startOfYear = LocalDate.of(year, month, day);
        this.lengthOfYear = lengthOfYear;
        this.listOfWeeks = FXCollections.observableArrayList();
        LocalDate accumulativeWeeks = startOfYear;
        listOfWeeks.add(startOfYear);
        for(int i = 1; i < this.lengthOfYear; i++) {
            accumulativeWeeks = accumulativeWeeks.plusDays(7);
            listOfWeeks.add(accumulativeWeeks);
        }
    }

    public LocalDate getStartOfYear() {
        return startOfYear;
    }

    public int getLengthOfYear() {
        return lengthOfYear;
    }

    public ObservableList<LocalDate> getListOfWeeks() {
        return listOfWeeks;
    }

    public void displayWeeks() {
        for(LocalDate localDate: listOfWeeks) {
            System.out.println(localDate.toString());
        }
    }

}
