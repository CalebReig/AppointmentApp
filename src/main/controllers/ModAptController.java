package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import main.db.DBDataLoader;
import main.utils.AvailableTime;
import main.utils.Popup;
import main.objs.*;
import main.db.DBDataInserter;
import main.db.DBConnection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * This class controls events for the Add Appointment and Update Appointment page.
 */
public class ModAptController extends BaseController{

    private static boolean modifyView;
    private static Appointment selectedAppointment;

    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private TextField typeField;
    @FXML private TextField locationField;
    @FXML private TextArea descriptionArea;

    @FXML private DatePicker dateDatePicker;

     @FXML private ComboBox<LocalTime> startComboBox;
     @FXML private ComboBox<LocalTime> endComboBox;
     @FXML private ComboBox<String> customerComboBox;
     @FXML private ComboBox<String> contactComboBox;
     @FXML private ComboBox<String> userComboBox;
     @FXML private Label actionLabel;

     private static AvailableTime day;

    /**
     * This method sets the initial settings for the Add/Update Appointment page.
     * It sets up the window depending on if the 'ADD' button
     * or the 'UPDATE' button was pressed.
     */
    public void initialize() {
        super.initialize();
        for (Contact contact: Contact.getAllContacts()) {
            contactComboBox.getItems().add(contact.getName());
        }
        for (Customer customer: Customer.getAllCustomers()) {
            customerComboBox.getItems().add(customer.getName());
        }
        for (User user: User.getAllUsers()) {
            userComboBox.getItems().add(user.getUsername());
        }
        if (modifyView) {modifyViewSetup();}
        else {addViewSetup();}
    }

    /**
     * This method sets up the page if the 'ADD' button was pressed.
     */
    private void addViewSetup() {
        actionLabel.setText("ADD");
        selectedAppointment = null;
        idField.setText("Auto-ID");
        titleField.setText(null);
        typeField.setText(null);
        locationField.setText(null);
        descriptionArea.setText(null);
    }

    /**
     * This method sets up the page if the 'UPDATE' button was pressed.
     */
    private void modifyViewSetup() {
        actionLabel.setText("UPDATE");
        idField.setText(String.valueOf(selectedAppointment.getId()));
        titleField.setText(selectedAppointment.getTitle());
        typeField.setText(selectedAppointment.getType());
        locationField.setText(selectedAppointment.getLocation());
        descriptionArea.setText(selectedAppointment.getDescription());
        Customer customer = Customer.findCustomer(selectedAppointment.getCustomerId());
        customerComboBox.getSelectionModel().select(Objects.requireNonNull(customer).getName());
        Contact contact = Contact.findContact(selectedAppointment.getContactId());
        contactComboBox.getSelectionModel().select(Objects.requireNonNull(contact).getName());
        User user = User.findUser(selectedAppointment.getUserId());
        userComboBox.getSelectionModel().select(Objects.requireNonNull(user).getUsername());
        dateDatePicker.setValue(selectedAppointment.getDate());
        LocalTime[] times = selectedAppointment.getTime();
        startComboBox.getSelectionModel().select(times[0]);
        endComboBox.getSelectionModel().select(times[1]);
        createDayObject();
        day.setupEndDay(times[0], times[1]);

    }

    /**
     * This method sets the class's modifyView variable.
     * @param modify True if window is in update appointment view
     */
    public static void setModifyView(boolean modify) {
        modifyView = modify;
    }

    /**
     * This method sets the class's modifyView variable.
     * @param modify True if window is in update appointment view
     * @param appointment The selected appointment to be modified
     */
    public static void setModifyView(boolean modify, Appointment appointment) {
        modifyView = modify;
        selectedAppointment = appointment;
    }

    /**
     * This method clears the start and end time ComboBoxes
     * and creates a new day object when a date in the DatePicker
     * is chosen.
     */
    @FXML private void selectDay() {
        endComboBox.getItems().clear();
        startComboBox.getItems().clear();
        createDayObject();
    }

    /**
     * This method clears the end time ComboBox and changes the
     * options within the end time ComboBox to be within 2 hours of the
     * selected start time.
     */
    @FXML private void selectStartTime() {
        endComboBox.getItems().clear();
        LocalTime startTime = startComboBox.getSelectionModel().getSelectedItem();
        LocalTime[] availableTimes = AvailableTime.alterAvailableTimes(startTime, "start");
        for (LocalTime time : availableTimes) {
            if (time != null) {
                endComboBox.getItems().add(time);
            }
        }
        if (endComboBox.getSelectionModel().getSelectedItem() != null) {
            LocalTime endTime = endComboBox.getSelectionModel().getSelectedItem();
            day.setupEndDay(startTime, endTime);
        }
    }

    /**
     * This method clears the start time ComboBox and changes the
     * options within the start time ComboBox to be within 2 hours of the
     * selected end time.
     */
    @FXML private void selectEndTime() {
       LocalTime endTime = endComboBox.getSelectionModel().getSelectedItem();
       LocalTime[] availableTimes = AvailableTime.alterAvailableTimes(endTime, "end");
       for (LocalTime time : availableTimes) {
           if (time != null) {startComboBox.getItems().add(time);}
       }
       if (startComboBox.getSelectionModel().getSelectedItem() != null) {
           LocalTime startTime = startComboBox.getSelectionModel().getSelectedItem();
           day.setupEndDay(startTime, endTime);
       }
    }

    /**
     * This method submits the new or modified appointment to the application database.
     */
    @FXML private void submit() {
        try {
            String title = titleField.getText();
            String description = descriptionArea.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            LocalDateTime start = getDateTime(startComboBox, day.getStartDay());
            LocalDateTime end = getDateTime(endComboBox, day.getEndDay());
            ZonedDateTime localStart = ZonedDateTime.of(start, Main.LOCALTIMEZONE);
            ZonedDateTime localEnd = ZonedDateTime.of(end, Main.LOCALTIMEZONE);
            ZonedDateTime utcStart = localStart.withZoneSameInstant(Main.UTC);
            ZonedDateTime utcEnd = localEnd.withZoneSameInstant(Main.UTC);
            LocalDateTime utcStartDT = utcStart.toLocalDateTime();
            LocalDateTime utcEndDT = utcEnd.toLocalDateTime();
            Timestamp utcStartTS = Timestamp.valueOf(utcStartDT);
            Timestamp utcEndTS = Timestamp.valueOf(utcEndDT);
            int customerId = Objects.requireNonNull(Customer.findCustomer(customerComboBox.getValue())).getId();
            int contactId = Objects.requireNonNull(Contact.findContact(contactComboBox.getValue())).getId();
            int userId = Objects.requireNonNull(User.findUser(userComboBox.getValue())).getId();
            String currentUser = User.getCurrentUser().getUsername();
            int aptId = -1;
            if (modifyView) {aptId = selectedAppointment.getId();}

            if (Appointment.isTimeConflict(Timestamp.valueOf(start), Timestamp.valueOf(end), customerId, aptId)) {
                Popup.displayError(3);
                return;
            }
            if (modifyView) {
                String[] vals = {title, description, location, type, String.valueOf(utcStartTS),
                        String.valueOf(utcEndTS), String.valueOf(customerId), String.valueOf(userId),
                        String.valueOf(contactId), currentUser, String.valueOf(selectedAppointment.getId())};
                if (Popup.displayConfirmation("Appointment", "Update")) {
                    DBDataInserter.updateAppointmentToDB(vals);
                    selectedAppointment.setTitle(title);
                    selectedAppointment.setDescription(description);
                    selectedAppointment.setLocation(location);
                    selectedAppointment.setType(type);
                    selectedAppointment.setStartTime(Timestamp.valueOf(start));
                    selectedAppointment.setEndTime(Timestamp.valueOf(end));
                    selectedAppointment.setContactId(contactId);
                    selectedAppointment.setCustomerId(customerId);
                    selectedAppointment.setUserId(userId);
                    Popup.displayInformation("Appointment", "Update");
                }
                else {return;}
            }
            else {
                String[] vals = {title, description, location, type, String.valueOf(utcStartTS),
                        String.valueOf(utcEndTS), String.valueOf(customerId), String.valueOf(userId),
                        String.valueOf(contactId), currentUser, currentUser};
                if (Popup.displayConfirmation("Appointment", "Add")) {
                    DBDataInserter.addAppointmentToDB(vals);
                    int id = DBConnection.idQuery(false);
                    Appointment apt = new Appointment(id, title, description, location, type, Timestamp.valueOf(start),
                            Timestamp.valueOf(end), userId, contactId, customerId);
                    Appointment.addAppointment(apt);
                    Popup.displayInformation("Appointment", "Add");
                }
                else {return;}
            }
            DBDataLoader.loadAllReports();
            switchToAppointments();
        }
        catch (Exception e) {
            Popup.displayError(2);
        }

    }

    /**
     * This method creates an instance of the <em>AvailableTime</em>
     * class with a given date.
     */
    private void createDayObject() {
        day = new AvailableTime(dateDatePicker.getValue());
        for (LocalTime t : day.getAvailableStartTimes()) {
            startComboBox.getItems().add(t);
        }
        for (LocalTime t : day.getAvailableEndTimes()) {
            endComboBox.getItems().add(t);
        }
    }

    /**
     * This method will combine a time and date into a LocalDateTime.
     * @param reference The ComboBox that time is being extracted
     * @param day The date of the start or end of the appointment
     * @return Returns a datetime of an appointment start or end.
     */
    private LocalDateTime getDateTime(ComboBox<LocalTime> reference, LocalDate day) {
        LocalTime time = reference.getValue();
        return LocalDateTime.of(day, time);
    }
}
