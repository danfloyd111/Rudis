package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Student;

import java.time.LocalDate;

/**
 * Created by dan on 03/02/17.
 */
public class StudentController {

    @FXML
    private ListView<LocalDate> evalDateList;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label courseLabel;

    //Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor, called before the initialize method.
     */
    public StudentController() {
    }

    /**
     * Initializes the controller class, is called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mApp) {
        mainApp = mApp;
        //Add some foo evals to the list
        ObservableList<LocalDate> foo = FXCollections.observableArrayList();
        foo.add(LocalDate.MIN);
        foo.add(LocalDate.now());
        foo.add(LocalDate.MAX);
        foo.add(LocalDate.MIN);
        foo.add(LocalDate.now());
        foo.add(LocalDate.MAX);
        foo.add(LocalDate.MIN);
        foo.add(LocalDate.now());
        foo.add(LocalDate.MAX);
        foo.add(LocalDate.MIN);
        foo.add(LocalDate.now());
        foo.add(LocalDate.MAX);
        foo.add(LocalDate.MIN);
        foo.add(LocalDate.now());
        foo.add(LocalDate.MAX);
        foo.add(LocalDate.MIN);
        foo.add(LocalDate.now());
        foo.add(LocalDate.MAX);
        evalDateList.setItems(foo);
        ObservableList<Student> studentData = mApp.getStudentData();
        Student student = studentData.get(0);
        firstNameLabel.setText(student.getFirstName());
        lastNameLabel.setText(student.getLastName());
        birthdayLabel.setText("Nato il ".concat(student.getBirthday().toString()));
        courseLabel.setText(student.getCourse());
    }
}
