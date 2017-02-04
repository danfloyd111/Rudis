package controller; /**
 * Created by dan on 03/02/17.
 * @author Daniele Paolini
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Student;

import java.io.IOException;
import java.time.LocalDate;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    /**
     * Default constructor, for now is only for debug purpose.
     */
    public MainApp() {
        studentData.add(new Student("John", "Doe", "Classe A", LocalDate.now()));
        studentData.add(new Student("Jane", "Doe", "Classe B", LocalDate.now()));
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Rudis - Versione 0.1");
        initRootLayout();
        //showHomeLayout();
        showStudentLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try{
            //Load root layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            //Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error in initRootLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Show the home page inside the root layout.
     */
    public void showHomeLayout() {
        try {
            //Load home layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/HomeLayout.fxml"));
            AnchorPane homeLayout = (AnchorPane) loader.load();
            //Set home into the center of the root layout
            rootLayout.setCenter(homeLayout);
        } catch (IOException e) {
            System.out.println("Error in showHomeLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Show the student view inside the root layout.
     */
    public void showStudentLayout() {
        try {
            //Load student layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/StudentLayout.fxml"));
            AnchorPane studentLayout = (AnchorPane) loader.load();
            //Set student view into the center of the root layout
            rootLayout.setCenter(studentLayout);
            //Give the controller access to the main app
            StudentController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            System.out.println("Error in the showStudentLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage
     * @return
     */
    public Stage getPrimaryStage () { return primaryStage; }

    /**
     * Returns the data as an observable list of students.
     * @return
     */
    public ObservableList<Student> getStudentData() { return studentData; }

}

