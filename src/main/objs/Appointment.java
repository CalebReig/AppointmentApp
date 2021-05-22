package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import main.utils.Popup;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;

/**
 * This class creates and modifies appointment objects which are
 * copies of appointments stored in the application database.
 */
public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startTime;
    private Timestamp endTime;
    private int userId;
    private int contactId;
    private int customerId;
    //A list of all appointments
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>Appointment</em> class.
     * @param id The id of the appointment
     * @param title The title of the appointment
     * @param description The description of the appointment
     * @param location The location of the appointment
     * @param type The type of the appointment
     * @param startTime The starting time of the appointment
     * @param endTime The ending time of the appointment
     * @param userId The id of the user who input the appointment
     * @param contactId The id of the contact associated with the appointment
     * @param customerId The id of the customer associated with the appointment
     */
    public Appointment(int id, String title, String description, String location, String type,
                       Timestamp startTime, Timestamp endTime, int userId, int contactId,
                       int customerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.contactId = contactId;
        this.customerId = customerId;

    }

    /**
     * This method returns the appointment id.
     * @return Returns the appointment id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the appointment id.
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the appointment title.
     * @return Returns the appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the appointment title.
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method returns the appointment description.
     * @return Returns the appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method sets the appointment description.
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method returns the appointment location.
     * @return Returns the appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method sets the appointment location.
     * @param location The location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method returns the appointment type.
     * @return Returns the appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * This method sets the appointment type.
     * @param type The type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method returns the appointment starting time.
     * @return Returns the appointment starting time.
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * This method sets the appointment starting time.
     * @param startTime The starting time to set
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * This method returns the appointment ending time.
     * @return Returns the appointment ending time.
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * This method sets the appointment ending time.
     * @param endTime The ending time to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * This method returns the appointment user id.
     * @return Returns the appointment user id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This method sets the appointment user id.
     * @param id The user id to set
     */
    public void setUserId(int id) {
        this.userId = id;
    }

    /**
     * This method returns the appointment contact id.
     * @return Returns the appointment contact id.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * This method sets the appointment contact id.
     * @param contactId The contact id to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * This method returns the appointment customer id.
     * @return Returns the appointment customer id.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This method sets the appointment customer id.
     * @param customerId The customer id to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * This method extracts a date from the start time timestamp
     * @return Returns the date of the appointment
     */
    public LocalDate getDate() {
        LocalDateTime time = startTime.toLocalDateTime();
        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();

        return LocalDate.of(year, month, day);
    }

    /**
     * This method extracts timestamps from the start and end time timestamp
     * @return Returns the times of the appointment start and end
     */
    public LocalTime[] getTime() {
        LocalDateTime start = startTime.toLocalDateTime();
        LocalDateTime end = endTime.toLocalDateTime();

        int startHour = start.getHour();
        int startMinute = start.getMinute();
        int endHour = end.getHour();
        int endMinute = end.getMinute();

        LocalTime newStart = LocalTime.of(startHour, startMinute);
        LocalTime newEnd = LocalTime.of(endHour, endMinute);
        return new LocalTime[]{newStart, newEnd};
    }

    /**
     * This method adds an appointment to the <em>allAppointments</em> list.
     * @param appointment The appointment to add
     */
    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    /**
     * This method removes an appointment from the <em>allAppointments</em> list.
     * @param appointment The appointment to remove
     */
    public static void remove(Appointment appointment) {
        allAppointments.remove(appointment);
        Popup.displayInformation(appointment.id, appointment.type);
    }

    /**
     * Returns the <em>allAppointments</em> list containing all appointments.
     * @return Returns a list of all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * This method returns all appointments scheduled on a given date.
     * @param year The year of the appointment(s)
     * @param month The month of the appointment(s)
     * @param day The day of the appointment(s)
     * @return Returns a list of appointments scheduled on a given date.
     */
    public static ObservableList<Appointment> getAppointments(int year, int month, int day) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();
        for (Appointment apt: getAllAppointments()) {
            cal.setTime(apt.getStartTime());
            if (cal.get(Calendar.MONTH) == month && cal.get(Calendar.DAY_OF_MONTH) == day && cal.get(Calendar.YEAR) == year) {
                appointments.add(apt);
            }
        }
        return appointments;
    }

    /**
     * This method returns all appointments scheduled on a given date.
     * @param year The year of the appointment(s)
     * @param month The month of the appointment(s)
     * @param day1 The first day of the appointment(s)
     * @param day2 The second day of the appointment(s)
     * @return Returns a list of appointments scheduled on a given date.
     */
    public static ObservableList<Appointment> getAppointments(int year, int month, int day1, int day2) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();
        for (Appointment apt: getAllAppointments()) {
            cal.setTime(apt.getStartTime());
            int calYear = cal.get(Calendar.YEAR);
            int calMonth = cal.get(Calendar.MONTH) + 1;
            int calDay = cal.get(Calendar.DAY_OF_MONTH);
            if ((calYear == year) && (calMonth == month) && (day1 <= calDay) && (calDay <= day2)) {
                appointments.add(apt);
            }
        }
        return appointments;
    }

    /**
     * This method verifies if there is a time conflict between the appointment
     * being registered and other existing appointments
     * @param newStart The starting time of the appointment being registered
     * @param newEnd The ending time of the appointment being registered
     * @param customerId The customer id of the appointment being registered
     * @param aptId The appointment id of the appointment being updated or -1 otherwise
     * @return Returns true if there is a time conflict and otherwise returns false.
     */
    public static boolean isTimeConflict(Timestamp newStart, Timestamp newEnd, int customerId, int aptId) {
        if (newStart.before(new Timestamp(System.currentTimeMillis()))) {return true;}
        for (Appointment appointment: allAppointments) {
            if ((customerId == appointment.customerId) && (aptId != appointment.id)
                    && ((((newStart.after(appointment.startTime)) || (newStart.equals(appointment.startTime)))
                                && (newStart.before(appointment.endTime)))
                        || (((newEnd.before(appointment.endTime)) || (newEnd.equals(appointment.endTime)))
                                && (newEnd.after(appointment.startTime))))){
                return true;
            }
        }
        return false;
    }
}


