package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button rateButton;

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to the current student as index of a list
    private int index;

    /**
     * Initializes the controller class, is called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Setting the behaviour of the delete button
        deleteButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Conferma eliminazione");
            alert.setHeaderText("Siete sicuri di voler eliminare l'alunno?");
            alert.setContentText("Il procedimento non è reversibile.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK)
                    deleteStudent();
                // else do nothing
            });
        });
        // Setting the behaviour of the modify button
        modifyButton.setOnAction(event -> {modifyStudent();});
        // Setting the behaviour of the rate button
        rateButton.setOnAction(event -> {rateStudent();});
    }

    /**
     * Is called by the main application to give a reference back to itself and initialize some attributes.
     */
    public void setMainApp(MainApp mApp, int id) {
        mainApp = mApp;
        index = id;
        // Initialize the student attributes
        ObservableList<Student> studentData = mApp.getStudentData();
        Student student = studentData.get(index);
        firstNameLabel.setText(student.getFirstName());
        lastNameLabel.setText(student.getLastName());
        birthdayLabel.setText("Nato il ".concat(student.getBirthday().toString()));
        courseLabel.setText(student.getCourse());
    }

    /**
     * Tells the main application to delete the current student indexed by local index variable
     */
    private void deleteStudent() {
        mainApp.removeStudent(index);
        mainApp.showStudentListLayout();
    }

    /**
     * Tells the main application to open the ModifyStudentLayout
     */
    private void modifyStudent() {
        mainApp.modifyStudent(index);
    }

    /**
     * Tells the main application to open the RateStudentLayout
     */
    private void rateStudent() { mainApp.rateStudent(index); }
}
