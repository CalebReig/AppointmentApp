package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.utils.AppointmentCalendar;

/**
 * This class controls events for the Home page.
 */
public class HomeController extends BaseController{
    @FXML private Label dateLabel;
    @FXML private GridPane calendarGrid;
    private AppointmentCalendar calendar;

    /**
     * This method sets the initial settings for the Home page.
     */
    public void initialize() {
        super.initialize();
        calendar = new AppointmentCalendar(calendarGrid);
        updateCalendar();
    }

    /**
     * This method updates the graphical calendar that
     * is displayed on the home screen as well as the data label.
     */
    private void updateCalendar() {
        calendar.setup();
        dateLabel.setText(calendar.getMonthName() + ", " + calendar.getYear());
    }

    /**
     * This method moves the calendar 1 month forward.
     */
    @FXML private void nextMonth() {
        if (calendar.getMonth() < 11) {
            calendar.setMonth(calendar.getMonth() + 1);
        }
        else {
            calendar.setMonth(0);
            calendar.setYear(calendar.getYear() + 1);
        }
        calendar.clearGrid();
        updateCalendar();
    }

    /**
     * This method moves the calendar 1 month backward.
     */
    @FXML private void prevMonth() {
        if (calendar.getMonth() > 0) {
            calendar.setMonth(calendar.getMonth() - 1);
        }
        else {
            calendar.setMonth(11);
            calendar.setYear(calendar.getYear() - 1);
        }
        calendar.clearGrid();
        updateCalendar();
    }
}
