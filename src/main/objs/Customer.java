package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and modifies customer objects which are
 * copies of customers stored in the application database.
 */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int fldId;
    //A list of all customers
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>Customer</em> class.
     * @param id The customer id
     * @param name The customer name
     * @param address The customer address
     * @param postalCode The customer postal code
     * @param phoneNumber The customer phone number
     * @param fldId The customer division id
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int fldId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.fldId = fldId;
    }

    /**
     * This method returns the customer phone number.
     * @return Returns the customer phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method sets the customer phone number.
     * @param phoneNumber The phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method returns the customer id.
     * @return Returns the customer id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the customer id.
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the customer name.
     * @return Returns the customer name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the customer name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the customer address.
     * @return Returns the customer address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method sets the customer address.
     * @param address The address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method returns the customer postal code.
     * @return Returns the customer postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * This method sets the customer postal code.
     * @param postalCode The postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * This method returns the division id.
     * @return Returns the division id.
     */
    public int getFldId() {
        return fldId;
    }

    /**
     * This method sets the division id.
     * @param fldId The division id to set
     */
    public void setFldId(int fldId) {this.fldId = fldId;}

    /**
     * This method adds a customer to the <em>allCustomers</em> list.
     * @param customer The customer to add
     */
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    /**
     * This method removes a customer to the <em>allCustomers</em> list.
     * @param customer The customer to remove
     */
    public static void remove(Customer customer) {allCustomers.remove(customer);}

    /**
     * This method returns a customer from the <em>allCustomers</em>
     * list based on the customer id.
     * @param id The id of the customer to return
     * @return Returns a customer object.
     */
    public static Customer findCustomer(int id) {
        for (Customer customer: allCustomers) {
            if (id == customer.id) {return customer;}
        }
        return null;
    }

    /**
     * This method returns a customer from the <em>allCustomers</em>
     * list based on the customer name.
     * @param name The name of the customer to return
     * @return Returns a customer object.
     */
    public static Customer findCustomer(String name) {
        for (Customer customer: allCustomers) {
            if (name.equals(customer.name)) {return customer;}
        }
        return null;
    }

    /**
     * Returns the <em>allCustomers</em> list containing all customers.
     * @return Returns a list of all customers.
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }
}
