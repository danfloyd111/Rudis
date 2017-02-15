package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Student;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

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
        insertButton.setOnAction(event -> {
            String fName = firstName.getText();
            String lName = lastName.getText();
            String crs = course.getValue();
            Alert.AlertType aType;
            String aTitle, aHeader, aContent;
            boolean go = false;
            LocalDate bday = birthday.getValue();
            if (fName!=null && lName!=null && bday!=null && crs!=null && bday.isBefore(LocalDate.now())) {
                Student student = new Student(fName, lName, crs, bday, generateStudentId());
                mainApp.addStudent(student);
                store(student);
                aType = Alert.AlertType.INFORMATION;
                aTitle = "Successo";
                aHeader = "Operazione completata";
                aContent = "Lo studente è stato correttamente aggiunto al database.";
                go = true;
            } else {
                aType = Alert.AlertType.ERROR;
                aTitle = "Errore";
                aHeader = "Impossibile completare l'aggiunta";
                aContent = "Uno dei campi soprastanti è vuoto o contiene un errore.\nControllare che la data di nascita sia antecedente\na quella odierna.";
            }
            Alert alert = new Alert(aType);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle(aTitle);
            alert.setHeaderText(aHeader);
            alert.setContentText(aContent);
            alert.showAndWait();
            if (go)
                mainApp.showStudentListLayout();
        });
        // Setting the handler for the cancel button
        cancelButton.setOnAction(event -> {mainApp.showHomeLayout();});
    }

    /**
     * Generates a random string.
     * @return
     */
    private String generateStudentId() {
        Random generator = new Random();
        int rand = generator.nextInt();
        return firstName.getText() + lastName.getText() + rand;
    }

    /**
     * Stores the student into the database.
     * @param student
     */
    private void store(Student student) {
        String insertQuery = "INSERT INTO students "
                + "VALUES ('" + student.getId() + "', '" + student.getFirstName() + "', '" + student.getLastName()
                + "', '" + student.getCourse() + "', '" + student.getBirthday().toString() + "');";
        try {
            Statement statement = mainApp.getDatabaseConnection().createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");
            statement.execute(insertQuery);
        } catch (SQLException e) {
            System.err.println("Error in addStudentController() - couldn't store into the database");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
