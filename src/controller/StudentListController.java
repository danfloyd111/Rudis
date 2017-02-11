package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Student;

/**
 * Controller for the student list view.
 * Created by dan on 06/02/17.
 * @author Daniele Paolini
 */
public class StudentListController {

    @FXML
    private ListView<Student> studentList;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        studentList.setOnMouseClicked(event -> {
            if (event.getClickCount()==2 && !studentList.getItems().isEmpty()) {
                Student student = studentList.getSelectionModel().getSelectedItem();
                if(student!=null) {
                    String id = student.getId();
                    mainApp.showStudentLayout(id);
                }
            }
        });
    }

    public void setMainApp(MainApp mApp) {
        mainApp = mApp;
        studentList.setItems(mApp.getStudentData()); // this should be injected by the main application
    }
}
