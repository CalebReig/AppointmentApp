package main.objs;

/**
 * This class is used as an abstraction for the 3 report types
 * to be displayed on the Report page. It also holds the names
 * of each type of report.
 */
public class Report {

    //Names of the 3 report types to be displayed in a ComboBox on the Report page
    private static String[] reportNames = {"Total Appointments by Month", "Contact Schedules",
            "Total Customers and Appointments by Division"};

    /**
     * This method returns a list of report names.
     * @return Returns a list of report names.
     */
    public static String[] getReportNames() {
        return reportNames;
    }
}
