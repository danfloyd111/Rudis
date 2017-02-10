package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by dan on 03/02/17.
 * @author Daniele Paolini
 *
 * Model class for a student.
 */

public class Student {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty course;
    private final ObjectProperty<LocalDate> birthday;
    private final String id;

    /**
     * Default constructor.
     */
    public Student () { this(null, null, null, null, null); }

    /**
     * Constructor with some initial data.
     *
     * @param fName
     * @param lName
     * @param crs
     * @param bday
     */
    public Student(String fName, String lName, String crs, LocalDate bday, String id) {
        firstName = new SimpleStringProperty(fName);
        lastName = new SimpleStringProperty(lName);
        course = new SimpleStringProperty(crs);
        birthday = new SimpleObjectProperty<LocalDate>(bday);
        this.id = id;
    }

    /**
     * Id getter.
     * @return
     */
    public String getId() { return id; }

    /**
     * First name getter.
     * @return
     */
    public String getFirstName() { return firstName.get(); }

    /**
     * First name setter.
     * @param fName
     */
    public void setFirstName(String fName) { firstName.set(fName); }

    /**
     * First name property.
     * @return
     */
    public StringProperty firstNameProperty() { return firstName; }

    /**
     * Last name getter.
     * @return
     */
    public String getLastName() { return lastName.get(); }

    /**
     * Last name setter.
     * @param lName
     */
    public void setLastName(String lName) { lastName.set(lName); }

    /**
     * Last name property.
     * @return
     */
    public StringProperty lastNameProperty() { return lastName; }

    /**
     * Birthday getter.
     * @return
     */
    public LocalDate getBirthday() { return birthday.get(); }

    /**
     * Birthday setter.
     * @param bday
     */
    public void setBirthday(LocalDate bday) { birthday.set(bday); }

    /**
     * Birthday property.
     * @return
     */
    public ObjectProperty<LocalDate> birthdayProperty() { return birthday; }

    /**
     * Course getter.
     * @return
     */
    public String getCourse() { return course.get(); }

    /**
     * Course setter.
     * @param crs
     */
    public void setCourse(String crs) { course.set(crs); }

    /**
     * Course property.
     * @return
     */
    public StringProperty courseProperty() { return course; }

    /**
     * Print the student informations well formatted.
     * @return
     */
    @Override
    public String toString() {
        return lastName.get().concat(" ").concat(firstName.get()).concat(", frequentante la ").concat(course.get());
    }
}
