package sample.mainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.datamodel.ClassGroup;
import sample.datamodel.Data;
import sample.datamodel.Student;


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
    private StudentController studentControllerObject = new StudentController();
    private ClassGroupController classGroupControllerObject = new ClassGroupController();
    private ReadingListGenerator readingListGeneratorObject = new ReadingListGenerator();

    private ClassGroup newClass = new ClassGroup("Small group", 2018, 2, 6, 6);
    private ClassGroup newClass1 = new ClassGroup("Large group", 2019, 7, 7, 3);

    public void initialize() {
        data.getAllSchools().add(newClass);
        data.getAllSchools().add(newClass1);

        classList.getSelectionModel().selectedItemProperty().addListener((changed, oldVal, newVal) ->
        {
            ClassGroup newClassGroup = classList.getSelectionModel().getSelectedItem();
            studentList.setItems(newClassGroup.getStudentList());
            booksToReadList.getColumns().setAll(newClassGroup.getBooksToRead().getColumns());
        });

        classList.setItems(data.getAllSchools());
        classList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        classList.getSelectionModel().selectFirst();
        ObservableList<String> bookLibrary = createBookLibrary();
        Student student = new Student("John", "Doe", 2013, 1, 14);
        Student student1 = new Student("Jane", "Doe", 2013, 2, 23);
        Student student2 = new Student("James", "Doe", 2013, 3, 5);
        Student student3 = new Student("Jennifer", "Doe", 2013, 3, 5);
        Student student4 = new Student("Jeremy", "Doe", 2013, 3, 5);
        Student student5 = new Student("Jenny", "Doe", 2013, 3, 5);


        newClass.getStudentList().add(student);
        newClass.getStudentList().add(student1);
        newClass.getStudentList().add(student2);
        newClass.getStudentList().add(student3);
        newClass.getStudentList().add(student4);
        newClass.getStudentList().add(student5);
        readingListGeneratorObject.readingListGenerator(newClass, bookLibrary);
        studentList.setItems(newClass.getStudentList());
    }

    @FXML
    public void showNewStudentDialog() {
        ClassGroup selectedClass = classList.getSelectionModel().getSelectedItem();
        studentControllerObject.showNewStudentDialog(mainBorderPane, selectedClass);
    }

    @FXML
    private void showEditStudentDialog() {
        Student selectedStudent = studentList.getSelectionModel().getSelectedItem();
        studentControllerObject.showEditStudentDialog(mainBorderPane, selectedStudent);
        studentList.refresh();
    }

    @FXML
    private void removeStudentFromClass() {
        ClassGroup selectedClass = classList.getSelectionModel().getSelectedItem();
        Student selectedStudent = studentList.getSelectionModel().getSelectedItem();
        studentControllerObject.removeStudentFromClass(selectedClass, selectedStudent);
    }

    @FXML
    private void showCreateNewClassDialog() {
        ClassGroup newClassGroup = classGroupControllerObject.showCreateNewClassDialog(mainBorderPane);
        if (newClassGroup != null) {
            data.getAllSchools().add(newClassGroup);
            classList.getSelectionModel().select(newClassGroup);
        }
    }

    @FXML
    private void showEditClassDialog() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        ClassGroup newClassGroup = classGroupControllerObject.showEditClassDialog(mainBorderPane, selectedClassGroup);
        if (newClassGroup != null) {
            data.getAllSchools().remove(selectedClassGroup);
            data.getAllSchools().add(newClassGroup);
            classList.getSelectionModel().select(newClassGroup);
            classList.refresh();
        }
    }


    @FXML
    public void deleteClassDialog() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        ClassGroup deletedClass = classGroupControllerObject.deleteClassDialog(selectedClassGroup);
        if (deletedClass != null) {
            data.getAllSchools().remove(deletedClass);
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
        readingListGeneratorObject.readingListGenerator(selectedClassGroup, createBookLibrary());
//      readingListGeneratorObject.readingListGeneratorVersionTwo(selectedClassGroup, createBookLibrary());
        studentList.refresh();
    }

    @FXML
    public void distributeBooksForRemainingWeeks() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        boolean repeat = readingListGeneratorObject.readingListGeneratorForRemainingWeeks(selectedClassGroup, createBookLibrary());
        while (repeat) {
            repeat = readingListGeneratorObject.readingListGeneratorForRemainingWeeks(selectedClassGroup, createBookLibrary());
            System.out.println("******************************************************************");
            System.out.println("******************************************************************");
            System.out.println("******************************************************************");
            System.out.println("******************************************************************");
            System.out.println("********************Error, sharing again**************************");
            System.out.println("******************************************************************");
            System.out.println("******************************************************************");
            System.out.println("******************************************************************");
            System.out.println("******************************************************************");

        }
        studentList.refresh();
    }

    @FXML
    public void readingListGeneratorForSelectedStudent() {
        ClassGroup selectedClassGroup = classList.getSelectionModel().getSelectedItem();
        Student selectedStudent = studentList.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            Alert alert = AlertAndFXMLLoaderClass.alertTypeInformation("No student selected",
                    null, "Please select the student.");
            alert.showAndWait();
            return;
        }
        readingListGeneratorObject.readingListGeneratorForSelectedStudents(selectedClassGroup
                , selectedStudent, createBookLibrary());
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

//    @FXML
//    public void printInfoToConsole() {
//        Student selectedStudent = studentList.getSelectionModel().getSelectedItem();
//        System.out.println(selectedStudent.getFullName() + " : " + selectedStudent.getDateOfBirth());
// }
}
