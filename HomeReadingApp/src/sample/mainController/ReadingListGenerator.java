package sample.mainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.datamodel.ClassGroup;
import sample.datamodel.CurrentWeek;
import sample.datamodel.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ReadingListGenerator {

    public void readingListGenerator(ClassGroup classGroup, List<String> books) {
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
                    tempList.addAll(books);
                    repeat = false;
                }
            }
            student.setBooksToRead(readingList);
            System.out.println("Reading list successfully given to " + student.getFullName());
        }
        System.out.println("--- Distribution has finished ---");
    }

    public boolean readingListGeneratorForRemainingWeeks(ClassGroup classGroup, List<String> books) {
        int count = 0;
        CurrentWeek currentWeek = new CurrentWeek();
        ObservableList<LocalDate> current = currentWeek.currentWeek(classGroup);
        current.retainAll(classGroup.getStudyYear().getListOfWeeks());
        for (LocalDate week : current) {
            System.out.println(week.toString());
        }
        int startingIndex = classGroup.getStudyYear().getListOfWeeks().indexOf(current.get(0));

        for (int n = 0; n < classGroup.getStudentList().size(); n++) {
            Student tempStudent = classGroup.getStudentList().get(n);
            for (int l = startingIndex; l < classGroup.getStudyYear().getLengthOfYear(); l++) {
                tempStudent.getBooksToRead().add(l, " ");
            }
        }
        for (int i = 0; i < classGroup.getStudentList().size(); i++) {
            boolean repeat = false;
            Student student = classGroup.getStudentList().get(i);
            ObservableList<String> readingList = FXCollections.observableArrayList();
            List<String> tempBookListForEachStudent = new ArrayList<>(books);
            for (int p = 0; p < startingIndex; p++) {
                readingList.add(student.getBooksToRead().get(p));
                tempBookListForEachStudent.remove(student.getBooksToRead().get(p));
            }
            for (int j = startingIndex; j < classGroup.getStudyYear().getLengthOfYear(); j++) {
                List<String> anotherTempBookList = new ArrayList<>(books);
                anotherTempBookList.retainAll(tempBookListForEachStudent);
                Random random = new Random();
                int randomNumber = random.nextInt(tempBookListForEachStudent.size());
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
                System.out.println("Adding " + anotherTempBookList.get(randomNumber) + " on the date of " +
                        classGroup.getStudyYear().getListOfWeeks().get(j) + " for student " + student.getFullName());
                readingList.add(j, anotherTempBookList.get(randomNumber));
                tempBookListForEachStudent.remove(anotherTempBookList.get(randomNumber));
                if (repeat) {
                    for (int l = startingIndex; l < classGroup.getStudyYear().getLengthOfYear(); l++) {
                        readingList.add(l, " ");
                    }
                    System.out.println("Reseting list for student " + student.getFullName());
                    j = startingIndex - 1;
                    tempBookListForEachStudent.clear();
                    tempBookListForEachStudent.addAll(books);
                    for (int p = 0; p < startingIndex; p++) {
                        tempBookListForEachStudent.remove(student.getBooksToRead().get(p));
                    }
                    count++;
                    if (count == 20) {
                        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                        return true;
                    }
                }
            }
            student.setBooksToRead(readingList);
            System.out.println("Reading list successfully given to " + student.getFullName());
        }
        System.out.println("--- Distribution has finished ---");
        return false;
    }


//    public void readingListGeneratorVersionTwo(ClassGroup classGroup, List<String> books) {
//        for (int n = 0; n < classGroup.getStudentList().size(); n++) {
//            Student tempStudent = classGroup.getStudentList().get(n);
//            for (int l = 0; l < classGroup.getStudyYear().getLengthOfYear(); l++) {
//                tempStudent.getBooksToRead().add(l, " ");
//            }
//        }
//        for (int i = 0; i < classGroup.getStudentList().size(); i++) {
//            Student student = classGroup.getStudentList().get(i);
//            readingListGeneratorForSelectedStudents(classGroup, student, books);
//        }
//    }


    public void readingListGeneratorForSelectedStudents(ClassGroup classGroup, Student student, List<String> books) {
        ObservableList<String> readingList = FXCollections.observableArrayList();
        List<String> tempBookList = new ArrayList<>(books);
        boolean repeat = false;
        for (int i = 0; i < classGroup.getStudyYear().getLengthOfYear(); i++) {
            student.getBooksToRead().set(i, " ");
        }
        for (int i = 0; i < classGroup.getStudyYear().getLengthOfYear(); i++) {
            List<String> unavailableBooks = new ArrayList<>();
            for (int j = 0; j < classGroup.getStudentList().size(); j++) {
                Student students = classGroup.getStudentList().get(j);
                unavailableBooks.add(students.getBooksToRead().get(i));
            }
            List<String> availableBooks = new ArrayList<>(books);
            availableBooks.removeAll(unavailableBooks);
            Random random = new Random();
            int randomNumber = random.nextInt(availableBooks.size());
            while (!tempBookList.contains(availableBooks.get(randomNumber))) {
                if (availableBooks.size() == 1) {
                    System.out.println("Student " + student.getFullName() + " on date " +
                            classGroup.getStudyYear().getListOfWeeks().get(i) + " had a problem.");
                    repeat = true;
                    break;
                } else {
                    availableBooks.remove(randomNumber);
                    randomNumber = random.nextInt(availableBooks.size());
                }
            }
            readingList.add(i, availableBooks.get(randomNumber));
            tempBookList.remove(availableBooks.get(randomNumber));
            if (repeat) {
                i = -1;
                tempBookList.clear();
                tempBookList.addAll(books);
                repeat = false;
            }
        }
        student.setBooksToRead(readingList);
    }
}
