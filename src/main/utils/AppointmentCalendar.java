package main.utils;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.objs.Appointment;
import java.util.Calendar;


/**
 * This class contains the logic for the Home page calendar
 * as well as Appointment Month/Week filter.
 */
public class AppointmentCalendar {

    private GridPane grid;
    private static Calendar cal = Calendar.getInstance();
    private static int month;
    private static int year;
    private static int week;

    /**
     * This is a constructor method.
     * It creates an instance of the <em>AppointmentCalendar</em> class.
     */
    public AppointmentCalendar() {
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        week = cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * @param grid The empty GridPane on the Home page
     */
    public AppointmentCalendar(GridPane grid) {
        this.grid = grid;
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        week = cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * This method resets the calendar objects date to the first of the month.
     */
    public void setDate() {
        cal.set(year, month, 1);
    }

    /**
     * This method sets the AppointmentCalendar's week.
     * @param week The week to set.
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * This method returns the AppointmentCalendar's week.
     * @return Returns the week of the AppointmentCalendar.
     */
    public int getWeek() {
        return week;
    }

    /**
     * This method sets the AppointmentCalendar's month.
     * @param month The month to set.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * This method returns the AppointmentCalendar's month.
     * @return Returns the month of the AppointmentCalendar.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * This method sets the AppointmentCalendar's year.
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * This method returns the AppointmentCalendar's year.
     * @return Returns the year of the AppointmentCalendar.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * This method will return the month name based on the AppoinmentCalendar's month number.
     * @return Returns the name of the month.
     */
    public String getMonthName() {
        switch(month) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
            default: return "error";
        }
    }

    /**
     * This method will return the number of weeks in the AppoinmentCalendar's current month.
     * A week here is considered starting on Sunday and ending on a Monday. This results in
     * the first and last week of a month containing less than 7 days usually.
     * @return Returns the number of weeks in the month.
     */
    public int getWeeksInMonth() {
        setDate();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int days = getDaysOfMonth();
        int weeks = 0;
        while (days > 0) {
            if (dayOfWeek == 7) {
                dayOfWeek = 1;
                weeks++;
            }
            else {dayOfWeek ++;}
            days--;
        }
        if (dayOfWeek != 1) {weeks++;}
        return weeks;
    }

    /**
     * This method will find and return the starting and ending date of the AppoinmentCalendar's
     * month and week.
     * @return Returns an integer array containing the starting and ending day of both the month and week.
     */
    public int[] getDateRange() {
        setDate();
        int monthStart = 1;
        int monthEnd = getDaysOfMonth();
        int weekStart = 1;
        int weekCounter = 1;
        int dayCount = 1;
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        while (weekCounter < week) {
            if (dayOfWeek == 7) {
                dayOfWeek = 1;
                weekCounter++;
                weekStart = dayCount +1;
            }
            else {
                dayOfWeek++;
            }
            dayCount++;
        }
        int weekEnd = weekStart;
        while (dayOfWeek < 7) {
            if (weekEnd == monthEnd) {break;}
            dayOfWeek++;
            weekEnd++;
        }
        return new int[]{weekStart, weekEnd, monthStart, monthEnd};
    }

    /**
     * This method returns the day of the week.
     * @return Returns the day of the week.
     */
    private int getDayOfWeek() {
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * This method returns the number of days in the selected month.
     * @return Returns the number of days in the month.
     */
    private int getDaysOfMonth() {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * This method creates a single VBox styled to display a day of the month along with
     * appointments scheduled for that day in a ListView. The VBox will be placed on the
     * appropriate coordinate of the grid on the Home page.
     * @param day The day of the month
     * @return Returns a styled VBox displaying day of month and appointments scheduled for that day.
     */
    private static VBox activeCalendarCellSetup(int day) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_RIGHT);
        vbox.setStyle("-fx-border-color: grey;" + "-fx-background-color: #2ec4b6;");

        Label dayLabel = new Label(Integer.toString(day));
        dayLabel.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 12;" + "-fx-background-color: #2ec4b6;");

        ListView aptList = new ListView();
        ObservableList<Appointment> apts = main.objs.Appointment.getAppointments(year, month, day);
        for (Appointment apt: apts) {
            aptList.getItems().add(apt.getStartTime().toString().substring(11, 16) + "-" +
                                    apt.getEndTime().toString().substring(11, 16) + ": " +
                                    apt.getTitle());
        }
        vbox.getChildren().addAll(dayLabel, aptList);
        return vbox;
    }

    /**
     * This method creates a single VBox styled to fill the extra spots on the grid.
     * The VBox will be placed on the appropriate coordinate of the grid on the Home page.
     * @return Returns a styled VBox displaying nothing.
     */
    private static VBox nullCalendarCellSetup() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #cbf3f0;" + "-fx-border-color: grey;");
        return vbox;
    }

    /**
     * This method will place all the styled VBoxes onto the GridPane to show
     * the user a calendar display.
     */
    public void setup() {
        setDate();
        int count = 1;
        int day = 1;
        for(int i=1; i<7; i++) {
            for(int j=0; j<7; j++) {
                if ((count >= getDayOfWeek()) & (day <= getDaysOfMonth())) {
                    VBox vbox = activeCalendarCellSetup(day);
                    grid.add(vbox, j, i);
                    day++;
                } else {
                    VBox vbox = nullCalendarCellSetup();
                    grid.add(vbox, j, i);
                }
                count++;
            }
        }
    }

    /**
     * This method will remove all VBoxes on the GridPane.
     */
    public void clearGrid() {
        grid.getChildren().removeAll();
    }
}
