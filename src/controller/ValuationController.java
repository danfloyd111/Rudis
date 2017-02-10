package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Rating;
import model.Valuation;

/**
 * Created by dan on 10/02/17.
 * @author Daniele Paolini
 * Controller class for the valutation view.
 */
public class ValuationController {

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label birthday;
    @FXML
    private Label course;

    @FXML
    private Label valuationId;
    @FXML
    private Label valuationDate;

    @FXML
    private TableView<Rating> ratingsTable;
    @FXML
    private TableColumn<Rating,String> competenceColumn;
    @FXML
    private TableColumn<Rating,String> ratingsColumn;

    @FXML
    private Button backButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button deleteButton;

    // Reference to the main application
    private MainApp mainApp;

    // Reference to the current valuation
    private Valuation currentValuation;

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mApp
     */
    public void setMainApp(MainApp mApp) { mainApp = mApp; }

    /**
     * Initializes the controller class, is called after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        // Setting the cell factory of the columns
        competenceColumn.setCellValueFactory(cellValue -> cellValue.getValue().competenceProperty());
        ratingsColumn.setCellValueFactory(cellValue -> cellValue.getValue().rateProperty());
        // Setting the behaviour of the back button
        backButton.setOnAction(event -> { mainApp.showStudentLayout(currentValuation.getStudentId()); });
    }

    // I don't think we need the getters at all.

    /**
     * First name setter.
     * @param name
     */
    public void setFirstName(String name) { firstName.setText(name); }

    /**
     * Last name setter.
     * @param name
     */
    public void setLastName(String name) { lastName.setText(name); }

    /**
     * Birthday setter.
     * @param date
     */
    public void setBirthday(String date) { birthday.setText("nato il ".concat(date)); }

    /**
     * Course setter.
     * @param crs
     */
    public void setCourse(String crs) { course.setText(crs); }

    /**
     * Valuation id setter.
     * @param id
     */
    public void setValuationId(String id) { valuationId.setText("Codice ".concat(id)); }

    /**
     * Valuation id getter.
     * @param date
     */
    public void setValuationDate(String date) { valuationDate.setText("Data Valutazione ".concat(date)); }

    /**
     * Ratings table setter.
     * @param r
     */
    public void setRatings(ObservableList<Rating> r) { ratingsTable.setItems(r); }

    /**
     * Current valuation setter.
     * @param v
     */
    public void setCurrentValuation(Valuation v) { currentValuation = v; }

    /**
     * Current valuation getter.
     * @return
     */
    public Valuation getCurrentValuation() { return currentValuation; }

}
