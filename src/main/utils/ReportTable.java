package main.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.objs.Report;

/**
 * This class contains the logic for the TableView on the Reports page.
 */
public class ReportTable {

    public TableView<Report> reportTable;

    /**
     * This is a constructor method.
     * It creates an instance of the <em>ReportTable</em> class.
     * @param reportTable The TableView that displays reports.
     */
    public ReportTable(TableView<Report> reportTable) {
        this.reportTable = reportTable;
    }

    /**
     * This method will modify the TableView to display the contents of a specific report.
     * @param rpts The records of a specific report.
     * @param reportColumns A specific amount empty columns.
     * @param reportColumnReferences References to variable names of a report.
     * @param reportColumnNames The names of the columns of a report.
     */
    public void setup(ObservableList<Report> rpts, TableColumn<Report, String>[] reportColumns,
                      String[] reportColumnReferences, String[] reportColumnNames) {
        for (int i=0; i < reportColumns.length; i++) {
            reportTable.getColumns().add(reportColumns[i]);
            reportColumns[i].setCellValueFactory(new PropertyValueFactory<>(reportColumnReferences[i]));
            reportColumns[i].setMinWidth(reportTable.getPrefWidth() / reportColumns.length);
            reportColumns[i].setText(reportColumnNames[i]);
        }
        reportTable.setItems(rpts);
    }

    /**
     * This method will clear all items from the TableView displaying reports.
     */
    public void clearTable() {
        reportTable.getColumns().clear();
    }
}
