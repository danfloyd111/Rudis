package controller;

/**
 * Created by dan on 03/02/17.
 * @author Daniele Paolini
 * Main application.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Rating;
import model.Student;
import model.Valuation;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Student> studentData;
    private ObservableList<Valuation> valuationData;
    private ObservableList<Rating> ratingData;

    private Connection databaseConnection;

    /**
     * Default constructor.
     */
    public MainApp() {
        studentData = FXCollections.observableArrayList();
        valuationData = FXCollections.observableArrayList();
        ratingData = FXCollections.observableArrayList();
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Rudis - Versione 0.1");
        this.primaryStage.getIcons().add(new Image("file:resources/images/rudis-icon.png"));
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(850);
        initRootLayout();
        showHomeLayout();
        // Database initialization
        Properties properties = new Properties();
        properties.setProperty("PRAGMA foreign_keys", "ON");
        databaseConnection = null;
        try {
            databaseConnection = DriverManager.getConnection("jdbc:sqlite:resources/db/rudis.db", properties);
            databaseConnection.setAutoCommit(true);
            Statement statement = databaseConnection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");
            String studentCreationQuery = "CREATE TABLE IF NOT EXISTS students(\n"
                    + "id TEXT PRIMARY KEY NOT NULL,\n"
                    + "firstName TEXT NOT NULL,\n"
                    + "lastName TEXT NOT NULL,\n"
                    + "course TEXT NOT NULL,\n"
                    + "birthday  TEXT NOT NULL\n"
                    + ");";
            String valuationsCreationQuery = "CREATE TABLE IF NOT EXISTS valuations(\n"
                    + "id TEXT PRIMARY KEY NOT NULL,\n"
                    + "studentId TEXT NOT NULL,\n"
                    + "date TEXT NOT NULL,\n"
                    + "FOREIGN KEY(studentId) REFERENCES students(id) ON UPDATE CASCADE ON DELETE CASCADE\n"
                    + ");";
            String ratingsCreationQuery = "CREATE TABLE IF NOT EXISTS ratings(\n"
                    + "id INTEGER PRIMARY KEY NOT NULL,\n"
                    + "valuationId TEXT NOT NULL,\n"
                    + "competence TEXT NOT NULL,\n"
                    + "rate TEXT NOT NULL,\n"
                    + "FOREIGN KEY(valuationId) REFERENCES valuations(id) ON UPDATE CASCADE ON DELETE CASCADE\n"
                    + ");";
            statement.execute(studentCreationQuery);
            statement.execute(valuationsCreationQuery);
            statement.execute(ratingsCreationQuery);
            // Loading data stored into the db
            loadStudents();
            loadValuations();
            loadRatings();
        } catch (SQLException e) {
            System.err.println("Error in start() - MainApp. Couldn't get a connection with the db.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try{
            // Load root layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            // Give the controller access to the main application
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
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
            loader.setLocation(MainApp.class.getResource("/view/HomeLayout.fxml"));
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
    public void showStudentLayout(String studentId) {
        try {
            //Load student layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/StudentLayout.fxml"));
            AnchorPane studentLayout = (AnchorPane) loader.load();
            //Set student view into the center of the root layout
            rootLayout.setCenter(studentLayout);
            //Give the controller access to the main app
            StudentController controller = loader.getController();
            controller.setValuationData(valuationData.filtered(v -> v.getStudentId().equals(studentId)));
            controller.setMainApp(this, studentId);
        } catch (IOException e) {
            System.out.println("Error in the showStudentLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Show the student list inside the root layout.
     */
    public void showStudentListLayout() {
        try {
            // Load student layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/StudentListLayout.fxml"));
            AnchorPane studentLayout = (AnchorPane) loader.load();
            // Set student view into the center of the root layout
            rootLayout.setCenter(studentLayout);
            // Give the controller access to the main app
            StudentListController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            System.out.println("Error in the showStudentListLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Show the form for adding a student.
     */
    public void showAddStudentLayout() {
        try {
            // Load the specific layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AddStudentLayout.fxml"));
            AnchorPane addStudentLayout = (AnchorPane) loader.load();
            // Set this view into the center of the root layout
            rootLayout.setCenter(addStudentLayout);
            // Give the controller access to the main app
            AddStudentController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            System.out.println("Error in the showAddStudentLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Shows the modifying form.
     * @param oldStudent
     */
    public void showModifyStudentLayout(Student oldStudent) {
        try {
            // Load the layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/ModifyStudentLayout.fxml"));
            AnchorPane modifyStudentLayout = (AnchorPane) loader.load();
            // Set this view into the center of the root layout
            rootLayout.setCenter(modifyStudentLayout);
            // Give the controller access to the main app
            ModifyStudentController controller = loader.getController();
            controller.setMainApp(this);
            controller.setFirstName(oldStudent.getFirstName());
            controller.setLastName(oldStudent.getLastName());
            controller.setCourse(oldStudent.getCourse());
            controller.setBirthday(oldStudent.getBirthday());
            controller.setOldStudent(oldStudent); // Refactor this you don't need to pass the student if you set the other attributes
        } catch (IOException e) {
            System.out.println("Error in the showModifyStudentLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Shows the rating form.
     * @param current
     */
    public void showRateStudentLayout(Student current) {
        try {
            // Load the layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/RateStudentLayout.fxml"));
            AnchorPane rateStudentLayout = (AnchorPane) loader.load();
            // Set this view into the center of the root layout
            rootLayout.setCenter(rateStudentLayout);
            // Give the controller access to the main app
            RateStudentController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCurrentStudent(current); // Refactor this you don't need to pass the student if you set the other attributes
            controller.setFirstName(current.getFirstName());
            controller.setLastName(current.getLastName());
            controller.setBirthday(current.getBirthday());
            controller.setCourse(current.getCourse());
        } catch (IOException e) {
            System.out.println("Error in the showRateStudentLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Shows the Valuation view.
     * @param valuationId
     */
    public void showValuationLayout(String valuationId) {
        try {
            // Load the layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/ValuationLayout.fxml"));
            AnchorPane valuationLayout = (AnchorPane) loader.load();
            // Set this view into the center of the root layout
            rootLayout.setCenter(valuationLayout);
            // Give the controller acces to the main app
            ValuationController controller = loader.getController();
            controller.setMainApp(this);
            Valuation valuation = valuationData.filtered(v -> v.getValuationId().equals(valuationId)).get(0);
            Student student = studentData.filtered(s -> s.getId().equals(valuation.getStudentId())).get(0);
            controller.setFirstName(student.getFirstName()); // all of these are ugly, pass only the valuation object and let the setCurrentValuation do all the initialization
            controller.setLastName(student.getLastName()); // REFACTOR
            controller.setBirthday(student.getBirthday().toString()); // REFACTOR
            controller.setCourse(student.getCourse()); // REFACTOR
            controller.setValuationId(valuationId); // REFACTOR
            controller.setValuationDate(valuation.getValuationDate().toString()); // REFACTOR
            controller.setRatings(ratingData.filtered(rate -> rate.getValuationID().equals(valuationId))); // REFACTOR
            controller.setCurrentValuation(valuation); // This is only thing you have to pass, check and refactor
        } catch (IOException e) {
            System.out.println("Error in the showValuationLayout !");
            e.printStackTrace();
        }
    }

    /**
     * Shows the ModifyValuation view.
     * @param valuationId
     */
    public void showModifyValuationLayout(String valuationId) {
        try {
            // TODO: keep the comments in only one of the show... methods
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/ModifyValuationLayout.fxml"));
            // TODO: refactor the next two lines in one
            AnchorPane modifyValuationLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(modifyValuationLayout);
            ModifyValuationController controller = loader.getController();
            controller.setup(this, valuationData.filtered(v -> v.getValuationId().equals(valuationId)).get(0));
        } catch (IOException e) {
            System.out.println("Error in the showModifyValuation method !");
            e.printStackTrace();
        }
    }

    /**
     * Opens the browser and shows the disclaimer.
     */
    public void showDisclaimer() {
        String os = System.getProperty("os.name");
        String uri = "resources/conf/disclaimer.html";
        if(os.startsWith("Windows"))
            uri = "resources\\conf\\disclaimer.html";
        getHostServices().showDocument(uri);
    }

    /**
     * Opens the browser and shows the guide.
     */
    public void showGuide() {
        String os = System.getProperty("os.name");
        String uri = "resources/conf/guide.html";
        if(os.startsWith("Windows"))
            uri = "resources\\conf\\guide.html";
        getHostServices().showDocument(uri);
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
    public ObservableList<Student> getStudentData() { return studentData; } // this is not so SAFE! Return a copy!!!

    /**
     * Returns the data as an observable list of ratings.
     * @return
     */
    public ObservableList<Valuation> getValuationData() { return valuationData; } // this is not so SAFE! Return a copy!!!

    /**
     * Returns the data as an observable list of ratings.
     * @return
     */
    public ObservableList<Rating> getRatingData() { return  ratingData; } // this is not so SAFE! Return a copy!!!

    /**
     * Add a student to the observable list of students.
     * @param student
     */
    public void addStudent(Student student) { studentData.add(student); }

    /**
     * Remove a student from the observable list of students.
     * @param id
     */
    public void removeStudent(String id) { studentData.removeAll(studentData.filtered(s -> s.getId().equals(id))); }

    /**
     * Modifies a student.
     * @param id
     */
    public void modifyStudent(String id) {
        Student old = studentData.filtered(s -> s.getId().equals(id)).get(0);
        showModifyStudentLayout(old);
    }

    /**
     * Rates a student.
     * @param id
     */
    public void rateStudent(String id) {
        Student current = studentData.filtered(s -> s.getId().equals(id)).get(0);
        showRateStudentLayout(current);
    }

    /**
     * Stores the valutation in the db and refresh the valutation list of the student.
     * @param valutation
     */
    public void storeValutation(Valuation valutation) {
        valuationData.add(valutation);
    }

    /**
     * Stores the ratings in the db.
     * @param ratings
     */
    public void storeRatings(ObservableList<Rating> ratings) { ratingData.addAll(ratings); }

    /**
     * Deletes a single evaluation and all the ratings related to it
     * @param valuationId
     */
    public void deleteValuation(String valuationId) {
        ratingData.removeAll(ratingData.filtered( rate -> rate.getValuationID().equals(valuationId)));
        valuationData.removeAll(valuationData.filtered((val -> val.getValuationId().equals(valuationId))));
        // Remember to delete all these things from the db also
    }

    /**
     * Deletes all evaluations related with the student and deletes all the ratings related to it.
     * @param studentId
     */
    public void deleteValutations(String studentId) {
        // Ok, this try-catch is ugly and bad managed but it can be useful to break the exception chain in case of bug.
        try {
            List<String> valuationIds = new ArrayList<>();
            valuationData.forEach(v -> {
                if(v.getStudentId().equals(studentId))
                    valuationIds.add(v.getValuationId());
            });
            // Removing all the ratings connected with the valuations
            valuationIds.forEach(valId -> deleteValuation(valId));
        } catch (NullPointerException e) {
            System.err.println("Null pointer exception into deleteValuations! Something is gone really wrong!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Returns the connection with the database.
     * @return
     */
    public Connection getDatabaseConnection() { return databaseConnection; }

    /**
     * Loads the students data from the database.
     */
    private void loadStudents() {
        try {
            Statement statement = databaseConnection.createStatement();
            String query = "SELECT id, firstName, lastName, course, birthday FROM students";
            ResultSet results = statement.executeQuery(query);
            while(results.next()){
                String id = results.getString("id");
                String fName = results.getString("firstName");
                String lName = results.getString("lastName");
                String course = results.getString("course");
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                //formatter = formatter.withLocale(Locale.ITALIAN);
                LocalDate bday = LocalDate.parse(results.getString("birthday"));
                studentData.add(new Student(fName,lName,course,bday,id));
            }
        } catch (SQLException e) {
            System.err.println("Cannot load students data - MainApp");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Loads the valuations data from the database.
     */
    private void loadValuations() {
        try {
            Statement statement = databaseConnection.createStatement();
            String query = "SELECT id, studentId, date FROM valuations";
            ResultSet results = statement.executeQuery(query);
            while(results.next()){
                String valuationId = results.getString("id");
                String studentId = results.getString("studentId");
                LocalDate date = LocalDate.parse(results.getString("date"));
                valuationData.add(new Valuation(studentId,valuationId,date));
            }
        } catch (SQLException e) {
            System.err.println("Cannot load valuations data - MainApp");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Loads the ratings data from the database.
     */
    private void loadRatings() {
        try {
            Statement statement = databaseConnection.createStatement();
            String query = "SELECT valuationId, competence, rate FROM ratings";
            ResultSet results = statement.executeQuery(query);
            while(results.next()){
                String valuationId = results.getString("valuationId");
                String competence = results.getString("competence");
                String rate = results.getString("rate");
                ratingData.add(new Rating(valuationId, competence, rate));
            }
        } catch (SQLException e) {
            System.err.println("Cannot load ratings data - MainApp");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}