package sample.datamodel;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

public class ClassGroup {
    private String name;
    private ObservableList<Student> studentList;
    private StudyYear studyYear;
    private TableColumn<Student, String> booksToRead;

    public ClassGroup(String name, int year, int month, int day, int lengthOfYear) {
        this.name = name;
        this.studentList = FXCollections.observableArrayList();
        this.studyYear = new StudyYear(year, month, day, lengthOfYear);
        this.booksToRead = new TableColumn<>();
        addWeekColumns();
    }

    public String getName() {
        return name;
    }

    public ObservableList<Student> getStudentList() {
        return studentList;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addWeekColumns() {
        for (int i = 0; i < this.studyYear.getLengthOfYear(); i++) {
            final int index = i;
            TableColumn<Student, String> newColumn = new TableColumn<>(
                    "   Week " + (i + 1) + "\n" + this.getStudyYear().getListOfWeeks().get(i).toString());
            newColumn.setResizable(true);
            newColumn.setPrefWidth(100);
            newColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() ->
                    cellData.getValue().getBooksToRead().get(index)));
            this.booksToRead.getColumns().add(newColumn);
        }
    }

    public TableColumn<Student, String> getBooksToRead() {
        return booksToRead;
    }

    public void setBooksToRead(TableColumn<Student, String> booksToRead) {
        this.booksToRead = booksToRead;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentList(ObservableList<Student> studentList) {
        this.studentList = studentList;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

}
