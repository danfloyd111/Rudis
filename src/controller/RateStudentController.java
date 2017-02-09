package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.Student;

import java.time.LocalDate;

/**
 * Created by dan on 09/02/17.
 * @author Daniele Paolini
 * Controller class for rating.
 */
public class RateStudentController {

    // Reference to the main application
    private MainApp mainApp;

    // Reference to the student
    private Student currentStudent;

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label course;

    @FXML
    private Label birthday;

    @FXML
    private DatePicker ratingDate;

    @FXML
    private Button rateButton;

    @FXML
    private Button cancelButton;

    /**
     * It's called by the main application to give a reference back to itself.
     * @param mApp
     */
    public void setMainApp(MainApp mApp) { mainApp = mApp; }

    /**
     * Called after the constructor.
     */
    @FXML
    public void initialize() {
        // I can't initialize the attributes here because the reference for the student is passed to this object after the
        // initialization that occurs immediately after the constructor ends. So the attributes must be setted from the Main
        // application by calling the apposite methods.
        cancelButton.setOnAction(event -> {
            mainApp.showStudentLayout(mainApp.getStudentData().indexOf(currentStudent));
        });
    }

    /**
     * Current student setter.
     * @param current
     */
    public void setCurrentStudent(Student current) { currentStudent = current; }

    /**
     * First name getter.
     * @param fName
     */
    public void setFirstName(String fName) { firstName.setText(fName); }

    /**
     * Last name getter.
     * @param lName
     */
    public void setLastName(String lName) { lastName.setText(lName); }

    /**
     * Course getter.
     * @param crs
     */
    public void setCourse(String crs) { course.setText(crs); }

    /**
     * Birthday getter.
     * @param bday
     */
    public void setBirthday(LocalDate bday) { birthday.setText("Nato il ".concat(bday.toString())); }

    /**
     * Rating date setter.
     * @return
     */
    public LocalDate getRatingDate() { return ratingDate.getValue(); }
}
