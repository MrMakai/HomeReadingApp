package sample.datamodel;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.time.DayOfWeek;
import java.time.LocalDate;


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

     private void addWeekColumns() {
        for (int i = 0; i < this.studyYear.getLengthOfYear(); i++) {
            final int index = i;
            LocalDate tempDate = this.getStudyYear().getListOfWeeks().get(i);
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

            TableColumn<Student, String> newColumn = new TableColumn<>(
                    "   Week " + (i + 1) + "\n" + tempDate.toString());
            newColumn.setResizable(true);
            newColumn.setPrefWidth(100);
            newColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() ->
                    cellData.getValue().getBooksToRead().get(index)));
            newColumn.setCellFactory(column -> {
                return new TableCell<Student, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) { //If the cell is empty
                            setText(null);

                        } else {
                            setText(item);
                            if (tempWeek.contains(LocalDate.now())) {
                                newColumn.setStyle("-fx-background-color:rgba(113, 216, 99, 0.6)");
                            } else if (tempWeek.get(0).isBefore(LocalDate.now())) {
                                newColumn.setStyle("-fx-background-color:rgba(166, 168, 164, 0.5)");
                            } else {
                                newColumn.setStyle("-fx-background-color:rgba(117, 188, 214, 0.4)");
                            }
                        }
                    }

                };
            });
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
