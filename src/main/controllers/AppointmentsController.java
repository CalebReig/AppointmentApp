package main.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.db.DBDataLoader;
import main.utils.Popup;
import main.objs.Appointment;
import main.utils.AppointmentCalendar;
import main.db.DBConnection;
import java.io.IOException;
import java.time.LocalDate;

/**
 * This class controls events for the Appointments page.
 */
public class AppointmentsController extends BaseController{

    @FXML private TableView<Appointment> allAppointmentsTable;
    @FXML private TableColumn<Appointment, String> appointmentIdColumn;
    @FXML private TableColumn<Appointment, String> appointmentTitleColumn;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionColumn;
    @FXML private TableColumn<Appointment, String> appointmentLocationColumn;
    @FXML private TableColumn<Appointment, String> appointmentContactColumn;
    @FXML private TableColumn<Appointment, String> appointmentTypeColumn;
    @FXML private TableColumn<Appointment, String> appointmentStartColumn;
    @FXML private TableColumn<Appointment, String> appointmentEndColumn;
    @FXML private TableColumn<Appointment, String> customerIdColumn;

    private AppointmentCalendar calendar;

    @FXML private RadioButton monthlyRadioButton;
    @FXML private RadioButton weeklyRadioButton;
    @FXML private RadioButton allRadioButton;
    @FXML private Label yearLabel;
    @FXML private Label monthLabel;
    @FXML private Label weekLabel;
    @FXML private Label day1Label;
    @FXML private Label day2Label;

    /**
     * This method sets the initial settings for the Appointments page.
     */
    public void initialize() {
        super.initialize();
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        ToggleGroup group = new ToggleGroup();
        monthlyRadioButton.setToggleGroup(group);
        weeklyRadioButton.setToggleGroup(group);
        allRadioButton.setToggleGroup(group);

        calendar = new AppointmentCalendar();
        allAppointmentsTable.setItems(Appointment.getAllAppointments());
    }

    /**
     * This method updates the year/month/week/day labels on the
     * top of the Appointments page.
     */
    private void updateCalendar() {
        monthLabel.setText(calendar.getMonthName());
        weekLabel.setText(String.valueOf(calendar.getWeek()));
        yearLabel.setText(String.valueOf(calendar.getYear()));
        day1Label.setText(String.valueOf(getDates()[0]));
        day2Label.setText(String.valueOf(getDates()[1]));
        updateAppointmentsTable();
    }

    /**
     * This method changes the color of the week/month label
     * when the radio button is selected for monthly filtering.
     */
    @FXML private void monthlyView() {
        monthLabel.setStyle("-fx-text-fill: black");
        weekLabel.setStyle("-fx-text-fill: grey");
        updateCalendar();
    }

    /**
     * This method changes the color of the week/month label
     * when the radio button is selected for weekly filtering.
     */
    @FXML private void weeklyView() {
        weekLabel.setStyle("-fx-text-fill: black");
        monthLabel.setStyle("-fx-text-fill: grey");
        updateCalendar();
    }

    /**
     * This method changes the color of the week/month label
     * and changes the text of the week/month/year filter labels
     * when the radio button is selected for all appointments.
     */
    @FXML private void allView() {
        weekLabel.setStyle("-fx-text-fill: grey");
        monthLabel.setStyle("-fx-text-fill: grey");
        monthLabel.setText("ALL");
        weekLabel.setText("ALL");
        yearLabel.setText("ALL");
        day1Label.setText(null);
        day2Label.setText(null);
        allAppointmentsTable.setItems(Appointment.getAllAppointments());
    }
    /**
     * This method adds either 1 week or 1 month
     * depending on whether the weekly or monthly
     * radio button is selected.
     */
    @FXML private void next() {
        if (monthlyRadioButton.isSelected()) {
            if (calendar.getMonth() < 11) {
                calendar.setMonth(calendar.getMonth() + 1);
            }
            else {
                calendar.setYear(calendar.getYear() + 1);
                calendar.setMonth(0);
            }
            calendar.setWeek(1);
        }
        else if (weeklyRadioButton.isSelected()) {
            if (calendar.getWeek() == calendar.getWeeksInMonth()) {
                calendar.setWeek(1);
                if (calendar.getMonth() < 11) {
                    calendar.setMonth(calendar.getMonth() + 1);
                }
                else {
                    calendar.setMonth(0);
                    calendar.setYear(calendar.getYear() + 1);
                }
            }
            else {
                calendar.setWeek(calendar.getWeek() + 1);
            }
        }
        else {return;}
        updateCalendar();
    }

    /**
     * This method subtracts either 1 week or 1 month
     * depending on whether the weekly or monthly
     * radio button is selected.
     */
    @FXML private void prev() {
        if (monthlyRadioButton.isSelected()) {
            if (calendar.getMonth() > 0) {
                calendar.setMonth(calendar.getMonth() - 1);
            }
            else {
                calendar.setYear(calendar.getYear() - 1);
                calendar.setMonth(11);
            }
            calendar.setWeek(1);
        }
        else if (weeklyRadioButton.isSelected()) {
            if (calendar.getWeek() == 1) {
                if (calendar.getMonth() > 0) {
                    calendar.setMonth(calendar.getMonth() - 1);
                }
                else {
                    calendar.setMonth(11);
                    calendar.setYear(calendar.getYear() - 1);
                }
                calendar.setWeek(calendar.getWeeksInMonth());
            }
            else {
                calendar.setWeek(calendar.getWeek() - 1);
            }
        }
        else {return;}
        updateCalendar();
    }

    /**
     * This method returns the date range if the current appointment
     * filter.
     * @return Returns a range of dates for the selected filter.
     */
    private LocalDate[] getDates() {
        int day1 = findDays()[0];
        int day2 = findDays()[1];
        int month = calendar.getMonth() + 1;
        int year = calendar.getYear();
        LocalDate startDate = LocalDate.of(year, month, day1);
        LocalDate endDate = LocalDate.of(year, month, day2);
        return new LocalDate[]{startDate, endDate};
    }

    /**
     * This method finds the start and end date for the filtered date range.
     * @return Returns the start and end day of an appointment time filter.
     */
    private int[] findDays() {
        int start;
        int stop;
        if (monthlyRadioButton.isSelected()) {
            start = 2;
            stop = 3;
        }
        else {
            start = 0;
            stop = 1;
        }
        int day1 = calendar.getDateRange()[start];
        int day2 = calendar.getDateRange()[stop];
        return new int[]{day1, day2};
    }

    /**
     * This method fills the table on the Appointments page
     * with appointments that fall within the filtered time.
     */
    private void updateAppointmentsTable() {
        int day1 = findDays()[0];
        int day2 = findDays()[1];
        int year = calendar.getYear();
        int month = calendar.getMonth() + 1;
        ObservableList<Appointment> appointments = Appointment.getAppointments(year, month, day1, day2);
        allAppointmentsTable.setItems(appointments);
    }

    /**
     * This method will delete a selected appointment once the
     * "DELETE" button is pressed and the deletion action is confirmed.
     */
    @FXML private void delete() {
        Appointment apt = allAppointmentsTable.getSelectionModel().getSelectedItem();
        if (apt != null) {
            if (Popup.displayConfirmation("Appointment", "Delete")) {
                int id = apt.getId();
                new Thread(() -> DBConnection.deleteObject(false, id)).start();
                Appointment.remove(apt);
                allAppointmentsTable.getItems().remove(apt);
                DBDataLoader.loadAllReports();
            }
        }
        else {
            Popup.displayError(1);
        }
    }

    /**
     * This method switches the application to the Update Appointment page.
     */
    @FXML private void switchToModApt(){
        try {
            Appointment selectedAppointment = allAppointmentsTable.getSelectionModel().getSelectedItem();
            main.controllers.ModAptController.setModifyView(true, selectedAppointment);
            main.Main.setRoot("add_mod_appointment", 670, 875);
        }
        catch (Exception e) {
            Popup.displayError(1);
        }
    }

    /**
     * This method switches the application to the Add Appointment page.
     */
    @FXML private void switchToAddApt() throws IOException {
        main.controllers.ModAptController.setModifyView(false);
        main.Main.setRoot("add_mod_appointment", 670, 875);
    }
}