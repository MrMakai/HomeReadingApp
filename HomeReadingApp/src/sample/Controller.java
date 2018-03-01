package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.classes.NewClassDialogController;
import sample.datamodel.ClassGroup;
import sample.datamodel.Data;
import sample.datamodel.Student;
import sample.students.NewStudentDialogController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class Controller {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<Student> studentList;
    @FXML
    private TableColumn<Student, String> booksToReadList;
    @FXML
    private ListView<ClassGroup> classList;
    private Data data = Data.getInstance();
    private ClassGroup newClass = new ClassGroup("Small group", 2017, 7, 7, 6);
    private ClassGroup newClass1 = new ClassGroup("Large group", 2019, 7, 7, 3);

    public void initialize() {
        data.getAllSchools().add(newClass);
        data.getAllSchools().add(newClass1);
        classList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ClassGroup>() {
            @Override
            public void changed(ObservableValue<? extends ClassGroup> observable, ClassGroup oldValue, ClassGroup newValue) {
                if (newValue != null) {
                    ClassGroup newClassGroup = classList.getSelectionModel().getSelectedItem();
                    studentList.setItems(newClassGroup.getStudentList());
                    booksToReadList.getColumns().setAll(newClassGroup.getBooksToRead().getColumns());
                }
            }
        });

        classList.setItems(data.getAllSchools());
        classList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        classList.getSelectionModel().selectFirst();
        ObservableList<String> bookLibrary = createBookLibrary();
        Student student = new Student("John", "Doe", 2013, 1, 14);
        Student student1 = new Student("Jane", "Doe", 2013, 2, 23);
        Student student2 = new Student("James", "Doe", 2013, 3, 5);

        newClass.getStudentList().add(student);
        newClass.getStudentList().add(student1);
        newClass.getStudentList().add(student2);
        readingListGenerator(newClass, bookLibrary);
        studentList.setItems(newClass.getStudentList());
    }

    private void readingListGenerator(ClassGroup classGroup, List<String> books) {
        for (int n = 0; n < classGroup.getStudentList().size(); n++) {
            Student tempStudent = classGroup.getStudentList().get(n);
            for (int l = 0; l < classGroup.getStudyYear().getLengthOfYear(); l++) {
                tempStudent.getBooksToRead().add(l, " ");
            }
        }
        for (int i = 0; i < classGroup.getStudentList().size(); i++) {
            boolean repeat = false;
            Student student = classGroup.getStudentList().get(i);
            ObservableList<String> readingList = FXCollections.observableArrayList();
            List<String> tempList = new ArrayList<>(books);
            for (int j = 0; j < classGroup.getStudyYear().getLengthOfYear(); j++) {
                List<String> anotherTempBookList = new ArrayList<>(tempList);
                Random random = new Random();
                int randomNumber = random.nextInt(tempList.size());
                for (int k = 0; k < classGroup.getStudentList().size(); k++) {
                    Student student1 = classGroup.getStudentList().get(k);
                    while (anotherTempBookList.get(randomNumber).equals(student1.getBooksToRead().get(j))) {
                        if (anotherTempBookList.size() == 1) {
                            System.out.println("AnotherTempBookList is almost empty " + student.getFullName()
                                    + " on the date of " + classGroup.getStudyYear().getListOfWeeks().get(j));
                            repeat = true;
                            break;
                        } else {
                            anotherTempBookList.remove(randomNumber);
                            randomNumber = random.nextInt(anotherTempBookList.size());
                            k = -1;
                            System.out.println("1");
                        }
                    }
                }
                readingList.add(j, anotherTempBookList.get(randomNumber));
                tempList.remove(anotherTempBookList.get(randomNumber));
                if (repeat) {
                    for (int l = 0; l < classGroup.getStudyYear().getLengthOfYear(); l++) {
                        readingList.add(l, " ");
                    }
                    System.out.println("Reseting list for student " + student.getFullName());
                    j = -1;
                    tempList.clear();
//                    tempList.removeAll(books);
                    tempList.addAll(books);
                    repeat = false;
                }
            }
            student.setBooksToRead(readingList);
            System.out.println("Reading list successfully given to " + student.getFullName());
        }
        System.out.println("--- Distribution has finished ---");
    }

    @FXML
    public void showNewStudentDialog() {
        ClassGroup selectedClass = classList.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Create new student");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("students\\newStudentDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog: " + e.getMessage());
            e.printStackTrace();
            return;
        }

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
                Alert alert = alertTypeError("Missing information", null, "Please fill out all " +
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
//            !!! This for loop is required for the table view data binding to work !!!
            List<String> newStudentEmptyReadingList = new ArrayList<>();
            for (int i = 0; i < newClass.getStudyYear().getLengthOfYear(); i++) {
                newStudentEmptyReadingList.add("");
            }
            newStudent.setBooksToRead(newStudentEmptyReadingList);
            selectedClass.getStudentList().add(newStudent);
        }
    }


    @FXML
    public void showEditStudentDialog() {
        Student selectedStudent = studentList.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            Alert alert = alertTypeInformation("No student selected", null,
                    "Please select the student you wish to edit.");
            alert.showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit student");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("students\\newStudentDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        NewStudentDialogController newStudentDialogController = fxmlLoader.getController();
        newStudentDialogController.editStudent(selectedStudent);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Student editedStudent = newStudentDialogController.updateStudent(selectedStudent);
            while (editedStudent.getFirstName().equals("0") && editedStudent.getLastName().equals("0") &&
                    (editedStudent.getDateOfBirth().getYear() == 1) &&
                    (editedStudent.getDateOfBirth().getMonthValue() == 1) &&
                    (editedStudent.getDateOfBirth().getDayOfMonth() == 1)) {
                Alert alert = alertTypeError("Missing information", null, "Please fill out all " +
                        " the required information marked with *");
                alert.showAndWait();
                Optional<ButtonType> anotherResult = dialog.showAndWait();
                if (anotherResult.isPresent() && anotherResult.get() == ButtonType.OK) {
                    editedStudent = newStudentDialogController.updateStudent(editedStudent);
                } else if (anotherResult.isPresent() && anotherResult.get() == ButtonType.CANCEL) {
                    break;
                }
            }
            selectedStudent.setFirstName(editedStudent.getFirstName());
            selectedStudent.setLastName(editedStudent.getLastName());
            selectedStudent.setDateOfBirth(editedStudent.getDateOfBirth());
            studentList.refresh();
        }
    }

    @FXML
    public void removeStudentFromClass() {
        ClassGroup selectedClass = classList.getSelectionModel().getSelectedItem();
        Student selectedStudent = studentList.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            Alert alert = alertTypeInformation("No student selected", null,
                    "Please select the student you wish to remove.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing student from the class");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove " + selectedStudent.getFullName() +
                " from the class?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectedClass.getStudentList().remove(selectedStudent);
        }
    }

    @FXML
    public void showCreateNewClassDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Create new class");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("classes\\newClassDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load dialog: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewClassDialogController newClassDialogController = fxmlLoader.getController();
            ClassGroup newClassGroup = newClassDialogController.createNewClass();
            while (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                Alert alert = alertTypeError("Missing information", null, "Please fill out all " +
                        " the required information marked with *");
                alert.showAndWait();
                Optional<ButtonType> anotherResult = dialog.showAndWait();
                if (anotherResult.isPresent() && anotherResult.get() == ButtonType.OK) {
                    newClassGroup = newClassDialogController.createNewClass();
                } else if (anotherResult.isPresent() && anotherResult.get() == ButtonType.CANCEL) {
                    break;
                }
            }

            if (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                return;
            }
            data.getAllSchools().add(newClassGroup);
            classList.getSelectionModel().select(newClassGroup);
        }
    }

    @FXML
    public void showEditClassDialog() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit class");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("classes\\newClassDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        NewClassDialogController newClassDialogController = fxmlLoader.getController();
        newClassDialogController.editClass(selectedClassGroup);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.getAllSchools().remove(selectedClassGroup);
            ClassGroup newClassGroup = newClassDialogController.updateClass(selectedClassGroup);
            while (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                Alert alert = alertTypeError("Missing information", null, "Please fill out all " +
                        " the required information marked with *");
                alert.showAndWait();
                Optional<ButtonType> anotherResult = dialog.showAndWait();
                if (anotherResult.isPresent() && anotherResult.get() == ButtonType.OK) {
                    newClassGroup = newClassDialogController.updateClass(newClassGroup);
                } else if (anotherResult.isPresent() && anotherResult.get() == ButtonType.CANCEL) {
                    break;
                }
            }

            if (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                return;
            }
            data.getAllSchools().add(newClassGroup);
            classList.getSelectionModel().select(newClassGroup);
            classList.refresh();
        }
    }

    @FXML
    public void deleteClassDialog() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing class from the list");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove " + selectedClassGroup.getName() +
                " from the list?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.getAllSchools().remove(selectedClassGroup);
        }
    }

    private ObservableList<String> createBookLibrary() {
        ObservableList<String> bookLibrary = FXCollections.observableArrayList();
        bookLibrary.add("book 1");
        bookLibrary.add("book 2");
        bookLibrary.add("book 3");
        bookLibrary.add("book 4");
        bookLibrary.add("book 5");
        bookLibrary.add("book 6");
//        bookLibrary.add("book 7");
//        bookLibrary.add("book 8");
//        bookLibrary.add("book 9");
//        bookLibrary.add("book 10");
        return bookLibrary;
    }

    @FXML
    public void distributeBooks() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        readingListGenerator(selectedClassGroup, createBookLibrary());
        studentList.refresh();
    }

    @FXML
    public void printReadingList() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        for (Student student : selectedClassGroup.getStudentList()) {
            System.out.println(student.getFullName());
            for (String book : student.getBooksToRead()) {
                System.out.println(book);
            }
        }
    }

    @FXML
    public void printInfoToConsole() {
        Student selectedStudent = studentList.getSelectionModel().getSelectedItem();
        System.out.println(selectedStudent.getFullName() + " : " + selectedStudent.getDateOfBirth());

    }

    private Alert alertTypeError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    private Alert alertTypeInformation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
}
