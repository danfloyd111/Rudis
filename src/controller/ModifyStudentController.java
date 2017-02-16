package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by dan on 08/02/17.
 */
public class ModifyStudentController {

    // Reference to the main application
    private MainApp mainApp;

    // Reference to the student being modified
    private Student oldStudent;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private ComboBox<String> course;

    @FXML
    private DatePicker birthday;

    @FXML
    private Button modifyButton;

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
        // Setting the handle for the modify button
        modifyButton.setOnAction(event -> {
            String fName = firstName.getText();
            String lName = lastName.getText();
            String crs = course.getValue();
            Alert.AlertType aType;
            String aTitle, aHeader, aContent;
            LocalDate bday = birthday.getValue();
            boolean go = false;
            if (!fName.isEmpty() && !lName.isEmpty() && bday!=null && !crs.isEmpty() && bday.isBefore(LocalDate.now())) {
                oldStudent.setFirstName(fName);
                oldStudent.setLastName(lName);
                oldStudent.setCourse(crs);
                oldStudent.setBirthday(bday);
                aType = Alert.AlertType.INFORMATION;
                aTitle = "Successo";
                aHeader = "Operazione completata";
                aContent = "Lo studente è stato correttamente modificato.";
                go = true;
                // Updating the database
                try {
                    String updateQuery = "UPDATE students SET firstName = '" + fName + "', "
                            + "lastName = '" + lName + "', "
                            + "course = '" + crs + "' , "
                            + "birthday = '" + bday.toString() + "' "
                            + "WHERE id = '" + oldStudent.getId() + "';";
                    PreparedStatement statement = mainApp.getDatabaseConnection().prepareStatement(updateQuery);
                    statement.executeUpdate();

                } catch (SQLException e) {
                    System.err.println("Error in ModifyStudentController - Cannot update the database");
                    e.printStackTrace();
                    System.exit(-1);
                }
            } else {
                aType = Alert.AlertType.ERROR;
                aTitle = "Errore";
                aHeader = "Impossibile completare la modifica";
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
        // Setting the handle for the cancel button
        cancelButton.setOnAction(event -> { mainApp.showStudentListLayout(); });
    }

    /**
     * First name setter.
     * @param fName
     */
    public void setFirstName(String fName) { firstName.setText(fName); }

    /**
     * Last name setter.
     * @param lName
     */
    public void setLastName(String lName) { lastName.setText(lName); }

    /**
     * Course setter.
     * @param crs
     */
    public void setCourse(String crs) { course.setValue(crs); }

    /**
     * Birthday setter.
     * @param bDay
     */
    public void setBirthday(LocalDate bDay) { birthday.setValue(bDay); }

    /**
     * Set the student to modify.
     * @param stud
     */
    public void setOldStudent(Student stud) { oldStudent = stud; }
}
