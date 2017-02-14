package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Student;
import model.Valuation;

/**
 * Created by dan on 03/02/17.
 */
public class StudentController {

    @FXML
    private ListView<Valuation> valuationList;
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

    // Reference to the current student
    private String studentId;

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

        // Setting the behaviour of double click on the list
        valuationList.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !valuationList.getItems().isEmpty()){
                Valuation valuation = valuationList.getSelectionModel().getSelectedItem();
                if(valuation!=null){
                    String id = valuation.getValuationId();
                    mainApp.showValuationLayout(id);
                }
            }
        });

        // NON PUOI FARE RIFERIMENTO ALLA MAIN APP QUI DENTRO PERCHE' ANCORA NON E' STATA REFERENZIATA
    }

    /**
     * Is called by the main application to give a reference back to itself and initialize some attributes.
     * @param mApp
     * @param id
     */
    public void setMainApp(MainApp mApp, String id) {
        mainApp = mApp;
        studentId = id;
        // Initialize the student attributes
        ObservableList<Student> studentData = mApp.getStudentData().filtered(student -> student.getId().equals(studentId)); // questo per fare una bella cosa dovrebbe essere la main app ad iniettarlo
        Student student = studentData.get(0); // questo per fare una bella cosa dovrebbe essere la main app ad iniettarlo
        firstNameLabel.setText(student.getFirstName()); // questo per fare una bella cosa dovrebbe essere la main app ad iniettarlo
        lastNameLabel.setText(student.getLastName()); // questo per fare una bella cosa dovrebbe essere la main app ad iniettarlo
        birthdayLabel.setText("Nato/a il ".concat(student.getBirthday().toString())); // questo per fare una bella cosa dovrebbe essere la main app ad iniettarlo
        courseLabel.setText("Classe " + student.getCourse()); // questo per fare una bella cosa dovrebbe essere la main app ad iniettarlo
    }

    /**
     * Tells the main application to delete the current student indexed by local index variable
     */
    private void deleteStudent() {
        mainApp.removeStudent(studentId);
        mainApp.deleteValutations(studentId);
        mainApp.showStudentListLayout();
    }

    /**
     * Tells the main application to open the ModifyStudentLayout
     */
    private void modifyStudent() {
        mainApp.modifyStudent(studentId);
    }

    /**
     * Tells the main application to open the RateStudentLayout
     */
    private void rateStudent() { mainApp.rateStudent(studentId); }

    /**
     * Sets the valutation list with proper data.
     * @param vlist
     */
    public void setValuationData(ObservableList<Valuation> vlist) { valuationList.setItems(vlist); }
}
