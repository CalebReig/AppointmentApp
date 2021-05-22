package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.db.DBDataLoader;
import main.utils.Popup;
import main.objs.Country;
import main.objs.Customer;
import main.objs.FLD;
import main.objs.User;
import main.db.DBDataInserter;
import main.db.DBConnection;
import java.util.Objects;

/**
 * This class controls events for the Add Customer and Update Customer page.
 */
public class ModCusController extends BaseController{

    private static boolean modifyView;
    private static Customer selectedCustomer;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postalCodeField;
    @FXML private TextField phoneField;

    @FXML private ComboBox<String> fldComboBox;
    @FXML private ComboBox<String> countryComboBox;
    @FXML private Label actionLabel;

    /**
     * This method sets the initial settings for the Add/Update Customer page.
     * It sets up the window depending on if the 'ADD' button
     * or the 'UPDATE' button was pressed.
     */
    public void initialize() {
        super.initialize();
        for (Country country: Country.getAllCountries()) {
            countryComboBox.getItems().add(country.getName());
        }
        if (modifyView) {modifyViewSetup();}
        else {addViewSetup();}
    }

    /**
     * This method sets up the page if the 'ADD' button was pressed.
     */
    private void addViewSetup() {
        actionLabel.setText("ADD");
        idField.setText("Auto-ID");
        nameField.setText(null);
        addressField.setText(null);
        postalCodeField.setText(null);
        phoneField.setText(null);

    }

    /**
     * This method sets up the page if the 'UPDATE' button was pressed.
     */
    private void modifyViewSetup() {
        actionLabel.setText("UPDATE");
        idField.setText(String.valueOf(selectedCustomer.getId()));
        nameField.setText(selectedCustomer.getName());
        addressField.setText(selectedCustomer.getAddress());
        postalCodeField.setText(selectedCustomer.getPostalCode());
        phoneField.setText(selectedCustomer.getPhoneNumber());
        FLD fld = FLD.findFLD(selectedCustomer.getFldId());
        Country country = Country.findCountry(fld.getCountryId());
        countryComboBox.getSelectionModel().select(country.getName());
        setValidFLDs();
        fldComboBox.getSelectionModel().select(fld.getName());
    }

    /**
     * This method sets the class's modifyView variable.
     * @param modify True if window is in update customer view
     */
    public static void setModifyView(boolean modify) {
        modifyView = modify;
    }

    /**
     * This method sets the class's modifyView variable.
     * @param modify True if window is in update customer view
     * @param customer The selected customer to be modified
     */
    public static void setModifyView(boolean modify, Customer customer) {
        modifyView = modify;
        selectedCustomer = customer;
    }

    /**
     * This method will set the items of the ComboBox containing
     * divisions to only show divisions that are associated with
     * the selected country.
     */
    @FXML private void setValidFLDs() {
        fldComboBox.getItems().clear();
        String countryName = countryComboBox.getSelectionModel().getSelectedItem();
        for (FLD fld: FLD.findCountryFLDs(countryName)) {
            fldComboBox.getItems().add(fld.getName());
        }
    }

    /**
     * This method submits the new or modified customer to the application database.
     */
    @FXML private void submit() {
        try {
            String name = nameField.getText();
            String address = addressField.getText();
            String postalCode = postalCodeField.getText();
            String phone = phoneField.getText();
            int fldId = Objects.requireNonNull(FLD.findFLD(fldComboBox.getValue())).getId();
            String currentUser = User.getCurrentUser().getUsername();
            if (modifyView) {
                String[] vals = {name, address, postalCode, phone, String.valueOf(fldId), currentUser,
                        String.valueOf(selectedCustomer.getId())};
                if (Popup.displayConfirmation("Customer", "Update")) {
                    DBDataInserter.updateCustomerToDB(vals);
                    selectedCustomer.setName(name);
                    selectedCustomer.setAddress(address);
                    selectedCustomer.setPostalCode(postalCode);
                    selectedCustomer.setPhoneNumber(phone);
                    selectedCustomer.setFldId(fldId);
                    Popup.displayInformation("Customer", "Update");
                }
                else {return;}
            } else {
                String[] vals = {name, address, postalCode, phone, String.valueOf(fldId), currentUser, currentUser};
                if (Popup.displayConfirmation("Customer", "Add")) {
                    DBDataInserter.addCustomerToDB(vals);
                    int id = DBConnection.idQuery(true); //need to query id from db
                    Customer cus = new Customer(id, name, address, postalCode, phone, fldId);
                    Customer.addCustomer(cus);
                    Popup.displayInformation("Customer", "Add");
                }
                else {return;}
            }
            DBDataLoader.loadAllReports();
            switchToCustomers();
        }
        catch (Exception e) {
            Popup.displayError(2);
        }

    }
}
