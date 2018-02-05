package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {
    private static Data instance = new Data();
    private ObservableList<ClassGroup> allClasses;

    public static Data getInstance() {
        return instance;
    }

    private Data() {
        this.allClasses = FXCollections.observableArrayList();
    }

    public ObservableList<ClassGroup> getAllSchools() {
        return allClasses;
    }
}
