package controller;

import com.sun.xml.internal.ws.api.FeatureConstructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Student;
import sun.applet.Main;

import java.time.LocalDate;

/**
 * Created by dan on 06/02/17.
 */
public class AddStudentController {

    // Reference to the main application
    private MainApp mainApp;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private ComboBox<String> course;

    @FXML
    private DatePicker birthday;

    @FXML
    private Button insertButton;

    @FXML
    private Button cancelButton;

    /**
     * It's called by the main application to give a reference back to itself.
     * @param mApp
     */
    public void setMainApp(MainApp mApp) {
        mainApp = mApp;
    }

    @FXML
    private void initialize() {
        // Initialize the course combo box
        ObservableList<String> courses = FXCollections.observableArrayList();
        courses.addAll("1A","1B","2A","2B","3A","3B","4A","4B","5A","5B"); // this need to be setted at least from the main app
        course.setItems(courses);
        // Setting the handler for the insert button
        insertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fName = firstName.getText();
                String lName = lastName.getText();
                String crs = course.getValue();
                LocalDate bday = birthday.getValue();
                if (fName!=null && lName!=null && bday!=null) {
                    Student student = new Student(fName, lName, crs, bday);
                    mainApp.addStudent(student);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Successo");
                alert.setHeaderText("Operazione completata");
                alert.setContentText("Lo studente è stato correttamente aggiunto al database.");
                alert.showAndWait();
            }
        });
        // Setting the handler for the cancel button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.showHomeLayout();
            }
        });
    }
}
