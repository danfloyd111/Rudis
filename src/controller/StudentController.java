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

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to the current student as index of a list
    private int index;

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
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
            }
        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mApp, int id) {
        mainApp = mApp;
        index = id;
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
        Student student = studentData.get(index);
        firstNameLabel.setText(student.getFirstName());
        lastNameLabel.setText(student.getLastName());
        birthdayLabel.setText("Nato il ".concat(student.getBirthday().toString()));
        courseLabel.setText(student.getCourse());
    }

    /**
     * Delete the current student indexed by local index variable
     */
    private void deleteStudent() {
        mainApp.removeStudent(index);
        mainApp.showStudentListLayout();
    }
}
