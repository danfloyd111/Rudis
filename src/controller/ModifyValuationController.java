package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Rating;
import model.Student;
import model.Valuation;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

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

    // Controls
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

    @FXML
    private Button modifyButton;
    @FXML
    private Button cancelButton;

    /**
     * It's called by the main application to give a reference back to itself and initialize all the attributes.
     * @param mApp
     * @param valuation
     */
    public void setup(MainApp mApp, Valuation valuation) {
        mainApp = mApp;
        currentValuation = valuation;
        Student student = mainApp.getStudentData().filtered(s -> s.getId().equals(currentValuation.getStudentId())).get(0);
        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        course.setText(student.getCourse());
        birthday.setText(student.getBirthday().toString());
        ratingDate.setValue(currentValuation.getValuationDate());
        // The next instructions have to stay here because they needs the currentValuation
        ObservableList<Rating> currentRatings = mainApp.getRatingData().filtered(r -> r.getValutationID().equals(currentValuation.getValuationId()));
        ratingsTable.setItems(currentRatings);
        competenceColumn.setCellValueFactory(cellValue -> cellValue.getValue().competenceProperty());
        ratingsColumn.setCellValueFactory(cellValue -> cellValue.getValue().rateProperty());
    }

    /**
     * Called after the constructor.
     */
    @FXML
    private void initialize() {
        // The ratings column need to be editable
        ratingsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ratingsColumn.setOnEditCommit(event -> {event.getTableView().getItems().get(event.getTablePosition().getRow()).setRate(event.getNewValue());});
        // Setting the behaviour of the buttons
        cancelButton.setOnAction(event -> {mainApp.showValuationLayout(currentValuation.getValuationId());});
        modifyButton.setOnAction((ActionEvent event) -> {
            ObservableList<Rating> currentRatings = mainApp.getRatingData().filtered(r -> r.getValutationID().equals(currentValuation.getValuationId()));
            ObservableList<Rating> newRatings = ratingsTable.getItems();
            Predicate<Rating> pred = rate -> !rate.getRate().equals("A") && !rate.getRate().equals("B")
                    && !rate.getRate().equals("C") && !rate.getRate().equals("D");
            // Alert setting
            Alert.AlertType atype = Alert.AlertType.INFORMATION;
            String atitle = "Successo";
            String aheader = "Operazione completata";
            String acontent = "La valutazione è stata correttamente modificata.";
            boolean go = true;
            if(ratingDate.getValue() != null && newRatings.filtered(pred).isEmpty()) {
                currentValuation.setValuationDate(ratingDate.getValue());
                // TODO: Before sorting you need to extract the data from currentRatings and newRatings and put into a modifiable list!!!
                // Sorting the ratings collections
                //currentRatings.sort(Comparator.comparing(Rating::getCompetence));
                //newRatings.sort(Comparator.comparing(Rating::getCompetence));
                // Now we can refresh the data
                for(int i = 0; i < currentRatings.size(); i++)
                    currentRatings.get(i).setRate(newRatings.get(i).getRate());
                // TODO: Here we have to refresh also the Database !!!
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
                mainApp.showValuationLayout(currentValuation.getValuationId());
        });
    }

}
