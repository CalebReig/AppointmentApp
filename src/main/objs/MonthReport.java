package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * This class creates a report containing metrics on appointments
 * by their month and type.
 */
public class MonthReport extends Report{

    private int year;
    private int month;
    private String type;
    private int total;

    private static String[] columnNames = {"Year", "Month", "Type", "Total Appointments"};

    private static final TableColumn<Report, String>[] allColumns = new TableColumn[]{
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>()
    };
    //A list of all monthly reports.
    private static ObservableList<Report> allReports = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>MonthReport</em> class.
     * @param year The appointment year
     * @param month The appointment month
     * @param type The appointment type
     * @param total The total number of appointments
     */
    public MonthReport(int year, int month, String type, int total) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * This method returns an array of the report's columns.
     * @return Returns the report's columns.
     */
    public static TableColumn<Report, String>[] getAllColumns() {
        return allColumns;
    }

    /**
     * This method adds a report to the <em>allReports</em> list.
     * @param rpt The report to add
     */
    public static void addReport(Report rpt) {allReports.add(rpt);}

    /**
     * Returns the <em>allReports</em> list containing all appointment metrics by month and type.
     * @return Returns a list of all monthly reports.
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
     * This method returns the total amount of appointments.
     * @return Returns the total amount of appointments.
     */
    public int getTotal() {
        return total;
    }

    /**
     * This method returns the type of appointment.
     * @return Returns the type of appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * This method returns the month of appointment.
     * @return Returns the month of appointment.
     */
    public int getMonth() {
        return month;
    }

    /**
     * This method returns the year of appointment.
     * @return Returns the year of appointment.
     */
    public int getYear() {
        return year;
    }

    /**
     * This method resets the list of all reports.
     */
    public static void reset() {
        allReports.clear();
    }
}

