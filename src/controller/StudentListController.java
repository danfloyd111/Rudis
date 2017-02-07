package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Student;

import java.util.List;

/**
 * Created by dan on 06/02/17.
 */
public class StudentListController {

    @FXML
    private ListView<String> studentList;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        studentList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && !studentList.getItems().isEmpty()) {
                    int currentIndex = studentList.getSelectionModel().getSelectedIndex();
                    mainApp.showStudentLayout(currentIndex);
                }
            }
        });
    }

    public void setMainApp(MainApp mApp) {
        mainApp = mApp;
        ObservableList<Student> studentData = mApp.getStudentData();
        ObservableList<String> slist = FXCollections.observableArrayList();
        studentData.forEach(student -> {
            slist.add(student.getLastName().concat(" ").concat(student.getFirstName()));
        });
        studentList.setItems(slist);
    }
}
