package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Main;
import main.utils.TimeLogger;
import main.objs.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * This class is inherited by all other controllers besides the Login page.
 * It control events for every thing on the left navigation area.
 */
public class BaseController {

    @FXML private Label lastLoginLabel;
    @FXML private Label upcomingAptLabel;

    /**
     * This method sets the initial settings for the navigation area.
     */
    public void initialize() {
        lastLoginLabel.setText(Timestamp.valueOf(TimeLogger.getLastLogin()).toString());
        upcomingAptLabel.setText(checkUpcomingApt());
    }

    /**
     * This method checks if an appointment is scheduled within
     * 15 minutes from the current time.
     * @return Returns text of the upcoming appointment's starting time, else returns "None".
     */
    private String checkUpcomingApt() {
        int userId = User.getCurrentUser().getId();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Date now15 = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15));
        Timestamp now15ts = new Timestamp(now15.getTime());
        for (Appointment apt: Appointment.getAllAppointments()) {
            if (apt.getUserId() == userId) {
                if ((apt.getStartTime().after(now))
                && (apt.getStartTime().before(now15ts))) {
                    return "Appointment\nID: " + apt.getId() + "\n" +
                            "starts:\n" + apt.getStartTime();
                }
            }
        }
        return "None";
    }

    /**
     * This method returns a word from the resource bundle depending on
     * the system language.
     * @param key The resource bundle key
     * @return Returns a word translated to the systems language.
     */
    String getWord(String key) {
        Locale loc = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("main/resources/words", loc);
        return rb.getString(key);
    }

    /**
     * This method switches the application to the Home page.
     */
    @FXML private void switchToHome() throws IOException {
        Main.setRoot("homepage", 670, 875);
    }

    /**
     * This method switches the application to the Customers page.
     */
    @FXML void switchToCustomers() throws IOException {
        Main.setRoot("customers", 670, 875);
    }

    /**
     * This method switches the application to the Appointments page.
     */
    @FXML void switchToAppointments() throws IOException {
        Main.setRoot("appointments", 670, 875);
    }

    /**
     * This method switches the application to the Reports page.
     */
    @FXML private void switchToReports() throws IOException {
        Main.setRoot("reports", 670, 875);
    }

    /**
     * This method closes the application.
     */
    @FXML private void exit() {
        Main.window.close();
    }
}
