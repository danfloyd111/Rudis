package controller;

import javafx.fxml.FXML;

/**
 * Created by dan on 06/02/17.
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mApp
     */
    public void setMainApp(MainApp mApp) {
        mainApp = mApp;
    }

    /**
     * Shows the home page of the application.
     */
    @FXML
    private void handleHome() { mainApp.showHomeLayout(); }

    /**
     * Shows the student list.
     */
    @FXML
    private void handleStudentVisualization() { mainApp.showStudentListLayout(); }

    /**
     * Shows the view used for adding a student.
     */
    @FXML
    private void handleAddStudent() { mainApp.showAddStudentLayout(); }
}
