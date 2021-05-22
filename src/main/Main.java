package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.db.DBDataLoader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;

/**
 * This class starts the application, implements methods to allowing changing fxml documents dynamically,
 * loads data from the application's database and initializes the global variables.
 */

public class Main extends Application {

    private static Scene mainPage;
    public static Stage window;

    public static String LANGUAGE = Locale.getDefault().getLanguage();
    public static ZoneId UTC = ZoneId.of("UTC");
    public static ZoneId EST = ZoneId.of("America/New_York");
    public static ZoneId LOCALTIMEZONE = ZoneId.systemDefault();

    /**
     * This method starts the application.
     * The sample parts and products are initialized in this method.
     * @param stage The stage of the JavaFX application
     */
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        mainPage = new Scene(loadFXML("login"));
        window.setTitle("Appointment Scheduler");
        window.setResizable(false);
        window.setScene(mainPage);
        window.show();
    }

    /**
     * This method sets the root of the application's FXML documents.
     * @param fxml The FXML document
     * @param height The height of the window
     * @param width The width of the window
     */
    public static void setRoot(String fxml, int height, int width) throws IOException {
        mainPage.setRoot(loadFXML(fxml));
        window.setHeight(height);
        window.setWidth(width);
    }

    /**
     * This method loads the application's FXML documents.
     * @param fxml The FXML document
     * @return Returns and loads the FXML document.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * This method begins running the application.
     * @param args System arguments
     */
    public static void main(String[] args) {
        DBDataLoader.loadData();
        launch(args);
    }
}
