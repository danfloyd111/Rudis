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
import java.util.Random;
import java.util.function.Predicate;

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
            mainApp.showStudentLayout(currentStudent.getId());
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
            ObservableList<Rating> ratings = ratingsTable.getItems();
            // This predicate will check the data
            Predicate<Rating> pred = rate -> !rate.getRate().equals("A") && !rate.getRate().equals("B")
                    && !rate.getRate().equals("C") && !rate.getRate().equals("D");
            // Alert setting
            Alert.AlertType atype = Alert.AlertType.INFORMATION;
            String atitle = "Successo";
            String aheader = "Operazione completata";
            String acontent = "La valutazione è stata correttamente inserita nel database";
            boolean go = true;
            if(ratingDate.getValue() != null && ratings.filtered(pred).isEmpty()) {
                // valutation ID needs to be the same in the next statements
                String id = generateValuationId();
                Valuation valutation = new Valuation(currentStudent.getId(), id, ratingDate.getValue());
                ratings.forEach(rate -> rate.setValutationID(id));
                // Now call a method in the main app that stores the valutation and refresh the valutation list of the student
                mainApp.storeValutation(valutation);
                mainApp.storeRatings(ratings);
            } else {
                atype = Alert.AlertType.ERROR;
                atitle = "Errore";
                aheader = "Uno o più campi sono vuoti o contengono errori";
                acontent = "Assicurarsi che le valutazioni siano indicate unicamente con un\n" +
                        "solo carattere maiuscolo (es:\"A\") e di aver inserito la data.\n" +
                        "Si consiglia di consultare la guida.";
                go = false;
            }
            Alert alert = new Alert(atype);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle(atitle);
            alert.setHeaderText(aheader);
            alert.setContentText(acontent);
            alert.showAndWait();
            if(go)
                mainApp.showStudentLayout(currentStudent.getId());
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

    /**
     * Generate a random string.
     * @return
     */
    private String generateValuationId() {
        Random generator = new Random();
        int rand = generator.nextInt();
        return firstName.getText() + lastName.getText() + rand;
    }
}
