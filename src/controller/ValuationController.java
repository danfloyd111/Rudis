package controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Rating;
import model.Valuation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
        // Setting the behaviour of the buttons
        backButton.setOnAction(event -> { mainApp.showStudentLayout(currentValuation.getStudentId()); });
        deleteButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Conferma eliminazione");
            alert.setHeaderText("Siete sicuri di voler eliminare la valutazione?");
            alert.setContentText("Il procedimento non è reversibile.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK)
                    deleteValuation();
                // else do nothing
            });
        });
        modifyButton.setOnAction(event -> { mainApp.showModifyValuationLayout(currentValuation.getValuationId());});
        exportButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salva con nome");
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Pdf files (*.pdf","*.pdf");
            File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
            if(file!=null){
                try {
                    PdfReader reader = new PdfReader("resources/pdf/rudis-template.pdf");
                    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file.getAbsolutePath()), '\0',true);
                    AcroFields form = stamper.getAcroFields();
                    form.setField("first",firstName.getText());
                    form.setField("last",lastName.getText());
                    form.setField("course",course.getText());
                    form.setField("birthday",birthday.getText().split(" ")[2]);
                    ObservableList<Rating> ratings = ratingsTable.getItems();
                    ArrayList<String> votes = new ArrayList<>();
                    ratings.forEach(rate -> votes.add(rate.getRate()));
                    for(int i=0; i< votes.size(); i++)
                        form.setField("c"+(i+1),votes.get(i));
                    stamper.close();
                    reader.close();

                } catch (IOException | DocumentException e) {
                    System.err.println("Error during the export of the pdf certificate, in initalize() - ValuationController");
                    e.printStackTrace();
                }
            }
        });

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
    public void setBirthday(String date) { birthday.setText("Nato/a il ".concat(date)); }

    /**
     * Course setter.
     * @param crs
     */
    public void setCourse(String crs) { course.setText(" Classe " + crs); }

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

    /**
     * Tells the main app to delete this valuation from the list and the db.
     */
    private void deleteValuation() {
        // We need to save the id before the deletion to prevent a null pointer exception
        String studentId = currentValuation.getStudentId();
        mainApp.deleteValuation(currentValuation.getValuationId());
        mainApp.showStudentLayout(studentId);
    }

}
