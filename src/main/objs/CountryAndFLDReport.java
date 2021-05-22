package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * This class creates a report containing metrics on appointments
 * by their country and first level division.
 */
public class CountryAndFLDReport extends Report{

    private String country;
    private String fld;
    private int totalCustomers;
    private int totalAppointments;
    //A list of all reports
    private static ObservableList<Report> allReports = FXCollections.observableArrayList();

    private static String[] columnNames = {"Country", "Division", "Total Customers", "Total Appointments"};

    private static final TableColumn<Report, String>[] allColumns = new TableColumn[]{
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>(),
            new TableColumn<Report, String>()
    };

    /**
     * This is a constructor method.
     * It creates an instance of the <em>CountryAndFLDReport</em> class.
     * @param country The country of the customer with a registered appointment
     * @param fld The first level division of the customer with a registered appointment
     * @param totalCustomers The total amount of customers
     * @param totalAppointments The total amount of appointments
     */
    public CountryAndFLDReport(String country, String fld, int totalCustomers, int totalAppointments) {
        this.country = country;
        this.fld = fld;
        this.totalCustomers = totalCustomers;
        this.totalAppointments = totalAppointments;
    }

    /**
     * This method adds a report to the <em>allReports</em> list.
     * @param rpt The report to add
     */
    public static void addReport(Report rpt) {allReports.add(rpt);}

    /**
     * Returns the <em>allReports</em> list containing all appointment metrics by country and division.
     * @return Returns a list of all country and division reports.
     */
    public static ObservableList<Report> getAllReports() {return allReports;}

    /**
     * This method returns an array of the report's columns.
     * @return Returns the report's columns.
     */
    public static TableColumn<Report, String>[] getAllColumns() {
        return allColumns;
    }

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
    public int getTotalAppointments() {
        return totalAppointments;
    }

    /**
     * This method returns the total amount of customers.
     * @return Returns the total amount of customers.
     */
    public int getTotalCustomers() {
        return totalCustomers;
    }

    /**
     * This method returns the report country.
     * @return Returns the report country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method returns the report first level division.
     * @return Returns the report first level division.
     */
    public String getFld() {
        return fld;
    }

    /**
     * This method resets the list of all reports.
     */
    public static void reset() {
        allReports.clear();
    }
}
