package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import main.utils.ReportTable;
import main.objs.ContactReport;
import main.objs.CountryAndFLDReport;
import main.objs.MonthReport;
import main.objs.Report;

/**
 * This class controls events for the Reports page.
 */
public class ReportsController extends BaseController{

    @FXML private ComboBox<String> reportsComboBox;
    @FXML private TableView<Report> reportsTable;

    ReportTable rptTable;

    static String[] monthReportColumnReferences = {"year", "month", "type", "total"};
    String[] contactReportColumnReferences = {"name", "appointmentId", "title", "type", "description",
        "start", "end", "customerId"};
    String[] countryReportColumnReferences = {"country", "fld", "totalCustomers", "totalAppointments"};

    /**
     * This method sets the initial settings for the Reports page.
     */
    public void initialize() {
        super.initialize();
        for (String name: Report.getReportNames()) {
            reportsComboBox.getItems().add(name);
        }
        reportsComboBox.getSelectionModel().select(Report.getReportNames()[0]);
        rptTable = new ReportTable(reportsTable);
        rptTable.setup(MonthReport.getAllReports(), MonthReport.getAllColumns(),
                monthReportColumnReferences, MonthReport.getColumnNames());
    }

    /**
     * This method will clear the Reports page TableView and fill it with the selected report.
     */
    @FXML private void switchReport() {
        String reportName = reportsComboBox.getSelectionModel().getSelectedItem();
        rptTable.clearTable();

        if (reportName.equals(Report.getReportNames()[0])) {
            rptTable.setup(MonthReport.getAllReports(), MonthReport.getAllColumns(),
                    monthReportColumnReferences, MonthReport.getColumnNames());
        }
        else if (reportName.equals(Report.getReportNames()[1])) {
            rptTable.setup(ContactReport.getAllReports(), ContactReport.getAllColumns(),
                    contactReportColumnReferences, ContactReport.getColumnNames());
        }
        else {
            rptTable.setup(CountryAndFLDReport.getAllReports(), CountryAndFLDReport.getAllColumns(),
                    countryReportColumnReferences, CountryAndFLDReport.getColumnNames());
        }
    }
}