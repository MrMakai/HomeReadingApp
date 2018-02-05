package sample.students;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.datamodel.Student;
import java.time.LocalDate;

public class NewStudentDialogController {

    @FXML
    private TextField newStudentFirstNameTextField;
    @FXML
    private TextField newStudentLastNameTextField;
    @FXML
    private DatePicker newStudentDateOfBirthPicker;

    public Student createNewStudent() {
        String firstName = newStudentFirstNameTextField.getText().trim();
        String lastName = newStudentLastNameTextField.getText().trim();
        LocalDate birthDate;
        if (newStudentDateOfBirthPicker.getValue() == null) {
            birthDate = LocalDate.of(1,1,1);
        } else {
            birthDate  = newStudentDateOfBirthPicker.getValue();
        }
        int yearOfBirth = birthDate.getYear();
        int monthOfBirth = birthDate.getMonthValue();
        int dayOfBirth = birthDate.getDayOfMonth();
        if(firstName.isEmpty() || lastName.isEmpty() || ((yearOfBirth == 1) && (monthOfBirth == 1) && (dayOfBirth == 1))) {
            return new Student("0", "0", 1,1,1);
        }
        return new Student(firstName, lastName, yearOfBirth, monthOfBirth, dayOfBirth);
    }

    public void editStudent(Student student) {
        newStudentFirstNameTextField.setText(student.getFirstName());
        newStudentLastNameTextField.setText(student.getLastName());
        newStudentDateOfBirthPicker.setValue(student.getDateOfBirth());
    }

    public Student updateStudent(Student student) {
        String firstName = newStudentFirstNameTextField.getText().trim();
        String lastName = newStudentLastNameTextField.getText().trim();
        LocalDate birthDate  = newStudentDateOfBirthPicker.getValue();
        int yearOfBirth = birthDate.getYear();
        int monthOfBirth = birthDate.getMonthValue();
        int dayOfBirth = birthDate.getDayOfMonth();
        if(firstName.isEmpty() || lastName.isEmpty()) {
            return new Student("0", "0", 1,1,1);
        }
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setDateOfBirth(birthDate);
        return new Student(firstName, lastName, yearOfBirth, monthOfBirth, dayOfBirth);
    }
}
