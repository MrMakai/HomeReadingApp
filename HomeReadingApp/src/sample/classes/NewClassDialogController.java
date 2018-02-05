package sample.classes;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import sample.datamodel.ClassGroup;
import sample.datamodel.Student;
import sample.datamodel.StudyYear;

import java.time.LocalDate;


public class NewClassDialogController {
    @FXML
    private TextField newClassNameTextField;
    @FXML
    private DatePicker newClassStudyYearStartPicker;
    @FXML
    private Spinner<Integer> newClassStudyYearLength;

    public ClassGroup createNewClass() {
        String className = newClassNameTextField.getText().trim();
        LocalDate studyYearStartDate;
        if (newClassStudyYearStartPicker.getValue() == null) {
            studyYearStartDate = LocalDate.of(1, 1, 1);
        } else {
            studyYearStartDate = newClassStudyYearStartPicker.getValue();
        }
        int yearOfStart = studyYearStartDate.getYear();
        int monthOfStart = studyYearStartDate.getMonthValue();
        int dayOfStart = studyYearStartDate.getDayOfMonth();
        int studyYearLength = newClassStudyYearLength.getValue();
        if (className.isEmpty() || ((yearOfStart == 1) && (monthOfStart == 1) && (dayOfStart == 1))) {
            return new ClassGroup("0", 1, 1, 1, 1);
        }
        return new ClassGroup(className, yearOfStart, monthOfStart, dayOfStart, studyYearLength);
    }


    public void editClass(ClassGroup classGroup) {
        newClassNameTextField.setText(classGroup.getName());
        newClassStudyYearStartPicker.setValue(classGroup.getStudyYear().getStartOfYear());
        newClassStudyYearLength.getValueFactory().setValue(classGroup.getStudyYear().getLengthOfYear());

    }

    public ClassGroup updateClass(ClassGroup classGroup) {
        int weekDifference = newClassStudyYearLength.getValue() - classGroup.getStudyYear().getLengthOfYear();
        String classname = newClassNameTextField.getText().trim();
        LocalDate studyYearStartDate = newClassStudyYearStartPicker.getValue();
        int yearOfStart = studyYearStartDate.getYear();
        int monthOfStart = studyYearStartDate.getMonthValue();
        int dayOfStart = studyYearStartDate.getDayOfMonth();
        int studyYearLength = newClassStudyYearLength.getValue();
        ObservableList<Student> existingList = classGroup.getStudentList();
        classGroup.setStudyYear(new StudyYear(yearOfStart, monthOfStart, dayOfStart, studyYearLength));
        ClassGroup newClassGroup = new ClassGroup(classname, classGroup.getStudyYear().getStartOfYear().getYear(),
                classGroup.getStudyYear().getStartOfYear().getMonth().getValue(),
                classGroup.getStudyYear().getStartOfYear().getDayOfMonth(), classGroup.getStudyYear().getLengthOfYear());
        newClassGroup.setStudentList(existingList);
        if (weekDifference > 0) {
            for (int i = 0; i < weekDifference; i++) {
                for (Student student : newClassGroup.getStudentList()) {
                    student.getBooksToRead().add("");
                }
            }
//        } else if (weekDifference < 0) {
//            for (int i = 0; i < (weekDifference * -1); i++) {
//                int last = classGroup.getStudyYear().getLengthOfYear() - i + 1;
//                for (Student student : newClassGroup.getStudentList()) {
//                    student.getBooksToRead().remove(last);
//                }
//            }
        }

        if (newClassGroup.getName().isEmpty()) {
            ClassGroup anotherClassGroup = new ClassGroup("0", 1, 1, 1, 1);
            anotherClassGroup.setStudentList(existingList);
            return anotherClassGroup;
        }
        return newClassGroup;
    }
}
