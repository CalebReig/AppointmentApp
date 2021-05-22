package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.db.DBDataLoader;
import main.utils.Popup;
import main.objs.Appointment;
import main.objs.Customer;
import main.db.DBConnection;
import java.io.IOException;

/**
 * This class controls events for the Customers page.
 */
public class CustomersController extends BaseController{

    @FXML private TableView<Customer> allCustomersTable;
    @FXML private TableColumn<Customer, String> customerIdColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> customerAddressColumn;
    @FXML private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML private TableColumn<Customer, String> customerFLDColumn;
    @FXML private TableColumn<Customer, String> customerPhoneColumn;

    /**
     * This method sets the initial settings for the Customers page.
     */
    public void initialize() {
        super.initialize();
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerFLDColumn.setCellValueFactory(new PropertyValueFactory<>("fldId"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        allCustomersTable.setItems(Customer.getAllCustomers());
    }

    /**
     * This method will delete a selected customer once the
     * "DELETE" button is pressed and the deletion action is confirmed.
     */
    @FXML private void delete() {
        Customer cus = allCustomersTable.getSelectionModel().getSelectedItem();
        if (cus != null) {
            if (Popup.displayConfirmation("Customer and \nAssociated Appointments", "Delete")) {
                int id = cus.getId();
                DBConnection.deleteObject(id);
                new Thread(() -> DBConnection.deleteObject(true, id)).start();
                ObservableList<Appointment> associatedApts = FXCollections.observableArrayList();
                for (Appointment apt: Appointment.getAllAppointments()) {
                    if (apt.getCustomerId() == id) {
                        associatedApts.add(apt);
                    }
                }
                for (Appointment apt: associatedApts) {
                    Appointment.remove(apt);
                }
                Customer.remove(cus);
                allCustomersTable.getItems().remove(cus);
                DBDataLoader.loadAllReports();
                Popup.displayInformation("Customer and \nAssociated Appointments", "Delete");
            }
        }
        else {
            Popup.displayError(1);
        }
    }

    /**
     * This method switches the application to the Update Customer page.
     */
    @FXML private void switchToModCus() {
        try {
            Customer selectedCustomer = allCustomersTable.getSelectionModel().getSelectedItem();
            main.controllers.ModCusController.setModifyView(true, selectedCustomer);
            main.Main.setRoot("add_mod_customer", 670, 875);
        }
        catch (Exception e) {
            Popup.displayError(1);
        }
    }

    /**
     * This method switches the application to the Add Customer page.
     */
    @FXML private void switchToAddCus() throws IOException {
        main.controllers.ModCusController.setModifyView(false);
        main.Main.setRoot("add_mod_customer", 670, 875);
    }
}
