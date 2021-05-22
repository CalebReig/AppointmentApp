package main.db;

import static main.db.DBConnection.insertValues;

/**
 * This class provides the functionality to insert specific values to tables of the
 * application database.
 */
public class DBDataInserter {

    private static final String addCustomerQuery = "insert into customers(Customer_Name," +
            " Address, Postal_Code, Phone, Division_ID, Last_Updated_By, Created_By) values " +
            "(?, ?, ?, ?, ?, ?, ?)";

    private static final String updateCustomerQuery = "update customers set Customer_Name = ?," +
            " Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Updated_By = ? " +
            "where Customer_ID = (select cast(? as unsigned))";

    private static final String addAppointmentQuery = "insert into appointments(" +
            "Title, Description, Location, Type, Start, End, Customer_ID, User_ID, " +
            "Contact_ID, Last_Updated_By, Created_By) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String updateAppointmentQuery = "update appointments set " +
            "Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
            "Customer_ID = ?, User_ID = ?, Contact_ID = ?, Last_Updated_By = ? " +
            "where Appointment_ID = (select cast(? as unsigned))";


    /**
     * This is a lambda function for creating a prepared statement to be queried.
     */
    public static Insertable insertData = (prepStmt, vals) -> {
        int count = 1;
        for (String value: vals) {
            prepStmt.setString(count, value);
            count++;
        }
        prepStmt.executeUpdate();
    };

    /**
     * This method inserts a new customer to the customer table of the application database.
     * @param vals The values for the columns of the customer to be added
     */
    public static void addCustomerToDB(String[] vals) {

        insertValues(addCustomerQuery, vals, insertData);
    }

    /**
     * This method inserts values into an existing customer in the application database.
     * @param vals The values for the columns of the customer to be updated
     */
    public static void updateCustomerToDB(String[] vals) {
        new Thread(() -> insertValues(updateCustomerQuery, vals, insertData)).start();
    }

    /**
     * This method inserts a new appointment to the appointment table of the application database.
     * @param vals The values for the columns of the appointment to be added
     */
    public static void addAppointmentToDB(String[] vals) {

        insertValues(addAppointmentQuery, vals, insertData);
    }

    /**
     * This method inserts values into an existing appointment in the application database.
     * @param vals The values for the columns of the appointment to be updated
     */
    public static void updateAppointmentToDB(String[] vals) {
        new Thread(() -> insertValues(updateAppointmentQuery, vals, insertData)).start();
    }
}
