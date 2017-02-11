package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Rating;
import model.Valuation;

/**
 * Created by dan on 11/02/17.
 * @Author Daniele Paolini
 * Controller class for ModifyValuation view
 */
public class ModifyValuationController {

    // Reference to the main application
    private MainApp mainApp;

    // Reference to the current valuation
    private Valuation currentValuation;

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label course;
    @FXML
    private Label birthday;

    @FXML
    private TableView<Rating> ratingsTable;
    @FXML
    private TableColumn<Rating, String> competenceColumn;
    @FXML
    private TableColumn<Rating, String> ratingsColumn;

    @FXML
    private DatePicker ratingDate;
}
