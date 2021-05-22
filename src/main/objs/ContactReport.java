package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import main.Main;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * This class creates a report containing contact schedules
 */
public class ContactReport extends Report{
    private String name;
    private int appointmentId;
    private String title;
    private String type;
    private String description;
    private Timestamp start;
    private Timestamp end;
    private int customerId;

    private static String[] columnNames = {"Contact", "Appointment ID", "Title", "Type", "Description", "Start",
        "End", "Customer ID"};

    private static final TableColumn<Report, String>[] allColumns = new TableColumn[]{
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>()
    };
    //A list of all contact schedules
    private static ObservableList<Report> allReports = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>ContactReport</em> class.
     * @param name The name of the contact
     * @param appointmentId The appointment id
     * @param title The appointment title
     * @param type The appointment type
     * @param description The appointment description
     * @param start The appointment starting time
     * @param end The appointment ending time
     * @param customerId The customer id
     */
    public ContactReport(String name, int appointmentId, String title, String type,
                         String description, Timestamp start, Timestamp end, int customerId) {
        this.name = name;
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        ZonedDateTime utcStart = ZonedDateTime.of(start.toLocalDateTime(), Main.UTC);
        ZonedDateTime utcEnd = ZonedDateTime.of(end.toLocalDateTime(), Main.UTC);
        ZonedDateTime localStart = utcStart.withZoneSameInstant(Main.LOCALTIMEZONE);
        ZonedDateTime localEnd = utcEnd.withZoneSameInstant(Main.LOCALTIMEZONE);
        LocalDateTime localStartDT = localStart.toLocalDateTime();
        LocalDateTime localEndDT = localEnd.toLocalDateTime();
        Timestamp localStartTS = Timestamp.valueOf(localStartDT);
        Timestamp localEndTS = Timestamp.valueOf(localEndDT);
        this.start = localStartTS;
        this.end = localEndTS;
        this.customerId = customerId;
    }

    /**
     * This method adds a report to the <em>allReports</em> list.
     * @param rpt The report to add
     */
    public static void addReport(Report rpt) {allReports.add(rpt);}

    /**
     * Returns the <em>allReports</em> list containing all contact schedules.
     * @return Returns a list of all contact schedules.
     */
    public static ObservableList<Report> getAllReports() {return allReports;}

    /**
     * This method returns an array of report column names.
     * @return Returns the report's column names
     */
    public static String[] getColumnNames() {
        return columnNames;
    }

    /**
     * This method returns an array of the report's columns.
     * @return Returns the report's columns.
     */
    public static TableColumn<Report, String>[] getAllColumns() {
        return allColumns;
    }

    /**
     * This method returns the contact name.
     * @return Returns the contact name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the appointment id.
     * @return Returns the appointment id.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * This method returns the appointment title.
     * @return Returns the appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method returns the appointment type.
     * @return Returns the appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * This method returns the appointment description.
     * @return Returns the appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method returns the appointment starting time.
     * @return Returns the appointment starting time.
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * This method returns the appointment ending time.
     * @return Returns the appointment ending time.
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * This method returns the customer id.
     * @return Returns the customer id.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This method resets the list of all reports.
     */
    public static void reset() {
        allReports.clear();
    }
}
