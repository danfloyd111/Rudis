package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Rating;
import model.Student;
import model.Valuation;

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

    @FXML
    private TableView<Rating> ratingsTable;

    @FXML
    private TableColumn<Rating, String> competenceColumn;

    @FXML
    private TableColumn<Rating, String> ratingsColumn;

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

        // RATING TABLE TEST
        ObservableList<Rating> testList = FXCollections.observableArrayList();
        for(int i = 0; i < 10; i++)
            testList.add(new Rating("valutationID","Sa fare la cacca", "A"));

        // Here you will have to set the right E
        ratingsTable.setItems(testList);
        competenceColumn.setCellValueFactory(cellValue -> cellValue.getValue().competenceProperty());
        ratingsColumn.setCellValueFactory(cellValue -> cellValue.getValue().rateProperty());
        // This column needs to be editable
        ratingsColumn.setCellValueFactory(cellValue -> cellValue.getValue().rateProperty());
        ratingsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ratingsColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setRate(event.getNewValue());
        });

        rateButton.setOnAction(event -> {
            if(ratingDate.getValue() != null) {
                ObservableList<Rating> ratings = ratingsTable.getItems();
                // valutation ID needs to be the same in the next statements
                Valuation valutation = new Valuation(currentStudent.getId(), "valutation ID", ratingDate.getValue());
                ratings.forEach(rate -> rate.setValutationID("valutation ID"));

                // Now call a method in the main app that stores the valutation and refresh the valutation list of the student
                mainApp.storeValutation(valutation);
                mainApp.storeRatings(ratings);
                // CHECK valutation data and alert ok if all gone fine
                mainApp.showStudentLayout(mainApp.getStudentData().indexOf(currentStudent)); // we need to change this reference and search by student ID
            } else {
                // alert date cannot be null
            }
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
