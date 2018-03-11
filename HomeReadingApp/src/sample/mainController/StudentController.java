package sample.mainController;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import sample.datamodel.ClassGroup;
import sample.datamodel.Student;
import sample.students.NewStudentDialogController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class StudentController {

    public void showNewStudentDialog(BorderPane borderPane, ClassGroup classGroup) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Create new student");
        FXMLLoader fxmlLoader = AlertAndFXMLLoaderClass.fxmlLoader(dialog, "sample\\students\\newStudentDialog.fxml",
                "Couldn't load the dialog: ");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewStudentDialogController newStudentDialogController = fxmlLoader.getController();
            Student newStudent = newStudentDialogController.createNewStudent();
            while (newStudent.getFirstName().equals("0") && newStudent.getLastName().equals("0") &&
                    (newStudent.getDateOfBirth().getYear() == 1) &&
                    (newStudent.getDateOfBirth().getMonthValue() == 1) &&
                    (newStudent.getDateOfBirth().getDayOfMonth() == 1)) {
                Alert alert = AlertAndFXMLLoaderClass.alertTypeError("Missing information", null, "Please fill out all " +
                        " the required information marked with *");
                alert.showAndWait();
                Optional<ButtonType> anotherResult = dialog.showAndWait();
                if (anotherResult.isPresent() && anotherResult.get() == ButtonType.OK) {
                    newStudent = newStudentDialogController.createNewStudent();
                } else if (anotherResult.isPresent() && anotherResult.get() == ButtonType.CANCEL) {
                    break;
                }
            }

            if (newStudent.getFirstName().equals("0") && newStudent.getLastName().equals("0") &&
                    (newStudent.getDateOfBirth().getYear() == 1) &&
                    (newStudent.getDateOfBirth().getMonthValue() == 1) &&
                    (newStudent.getDateOfBirth().getDayOfMonth() == 1)) {
                return;
            }
//          This for loop is required for the table view data binding to work
            List<String> newStudentEmptyReadingList = new ArrayList<>();
            for (int i = 0; i < classGroup.getStudyYear().getLengthOfYear(); i++) {
                newStudentEmptyReadingList.add("");
            }
            newStudent.setBooksToRead(newStudentEmptyReadingList);
            classGroup.getStudentList().add(newStudent);
        }
    }

    public void showEditStudentDialog(BorderPane borderPane, Student student) {
        if (student == null) {
            Alert alert = AlertAndFXMLLoaderClass.alertTypeInformation("No student selected",
                    null, "Please select the student you wish to edit.");
            alert.showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Edit student");
        FXMLLoader fxmlLoader = AlertAndFXMLLoaderClass.fxmlLoader(dialog,
                "sample\\students\\newStudentDialog.fxml",
                "Couldn't load the dialog: ");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        NewStudentDialogController newStudentDialogController = fxmlLoader.getController();
        newStudentDialogController.editStudent(student);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Student editedStudent = newStudentDialogController.updateStudent(student);
            while (editedStudent.getFirstName().equals("0") && editedStudent.getLastName().equals("0") &&
                    (editedStudent.getDateOfBirth().getYear() == 1) &&
                    (editedStudent.getDateOfBirth().getMonthValue() == 1) &&
                    (editedStudent.getDateOfBirth().getDayOfMonth() == 1)) {
                Alert alert = AlertAndFXMLLoaderClass.alertTypeError("Missing information",
                        null, "Please fill out all " +
                                " the required information marked with *");
                alert.showAndWait();
                Optional<ButtonType> anotherResult = dialog.showAndWait();
                if (anotherResult.isPresent() && anotherResult.get() == ButtonType.OK) {
                    editedStudent = newStudentDialogController.updateStudent(editedStudent);
                } else if (anotherResult.isPresent() && anotherResult.get() == ButtonType.CANCEL) {
                    break;
                }
            }
            student.setFirstName(editedStudent.getFirstName());
            student.setLastName(editedStudent.getLastName());
            student.setDateOfBirth(editedStudent.getDateOfBirth());
        }
    }

    public void removeStudentFromClass(ClassGroup classGroup, Student student) {
        if (student == null) {
            Alert alert = AlertAndFXMLLoaderClass.alertTypeInformation("No student selected", null,
                    "Please select the student you wish to remove.");
            alert.showAndWait();
            return;
        }
        Alert alert = AlertAndFXMLLoaderClass.alertTypeConfirmation("Removing student from the class",
                null, "Are you sure you want to remove " + student.getFullName() +
                        " from the class?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            classGroup.getStudentList().remove(student);
        }
    }
}
