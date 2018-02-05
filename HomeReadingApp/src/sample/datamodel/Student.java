package sample.datamodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.util.List;

public class Student {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private LocalDate dateOfBirth;
    private List<String> booksToRead;

    public Student(String firstName, String lastName, int yearOfBirth, int monthOfBirth, int dayOfBirth) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        this.booksToRead = FXCollections.observableArrayList();
    }

    public List<String> getBooksToRead() {
        return booksToRead;
    }

    public void setBooksToRead(List<String> booksToRead) {
        this.booksToRead = booksToRead;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName(); // + " " + dateOfBirth.toString();
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName=" + firstName +
                ", lastName=" + lastName;
    }
}
