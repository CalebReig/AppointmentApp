package main.db;

import main.Main;
import main.objs.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import static main.db.DBConnection.queryDB;


/**
 * This class provides functionality to load data from the application database.
 */
public class DBDataLoader {

    private static final String customerQuery = "select Customer_ID, Customer_Name, Address, Postal_Code, " +
            "Phone, Division_ID from customers";
    private static final String appointmentQuery = "select Appointment_ID, Title, Description, Location," +
            " Type, Start, End, User_ID, Contact_ID, Customer_ID from appointments order by Start";
    private static final String contactQuery = "select Contact_ID, Contact_Name, Email from contacts";
    private static final String countryQuery = "select Country_ID, Country from countries";
    private static final String FLDQuery = "select Division_ID, Division, COUNTRY_ID from first_level_divisions";
    private static final String userQuery = "select User_ID, User_Name from users";

    private static final String monthlyReportQuery = "select Year(Start) as Year, Month(Start) as Month, Type, Count(*) as " +
            "\"Total Appoinments\" from appointments group by Year, Month, Type";
    private static final String contactReportQuery = "select con.Contact_Name as Contact, apt.Appointment_ID as \"Appointment ID\"," +
            " apt.Title, apt.Description, apt.Start, apt.End, apt.Customer_ID as \"Customer ID\", apt.Type from appointments apt inner join contacts" +
            " con on apt.Contact_ID = con.Contact_ID order by con.Contact_Name, apt.Start";
    private static final String countryAndFLDReportQuery = "select c.Country, f.Division, count(distinct(cus.Customer_ID)) as \"Total Customers\"," +
            " count(a.Appointment_ID) as \"Total Appointments\" from appointments a inner join customers cus on a.Customer_ID = cus.Customer_ID " +
            "inner join first_level_divisions f on cus.Division_ID = f.Division_ID inner join countries c on f.COUNTRY_ID = c.COUNTRY_ID group by " +
            "Country, Division";


    /**
     * This is a lambda function for loading customer data.
     */
    public static Loadable loadCustomers = rs -> {
        try {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String postalCode = rs.getString(4);
            String phone = rs.getString(5);
            int fldId = rs.getInt(6);
            Customer customer = new Customer(id, name, address, postalCode, phone, fldId);
            Customer.addCustomer(customer);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This is a lambda function for loading appointment data.
     */
    public static Loadable loadAppointments = rs -> {
        try {
            int id = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type = rs.getString(5);
            Timestamp startTime = rs.getTimestamp(6);
            Timestamp endTime = rs.getTimestamp(7);
            ZonedDateTime utcStart = ZonedDateTime.of(startTime.toLocalDateTime(), Main.UTC);
            ZonedDateTime utcEnd = ZonedDateTime.of(endTime.toLocalDateTime(), Main.UTC);
            ZonedDateTime localStart = utcStart.withZoneSameInstant(Main.LOCALTIMEZONE);
            ZonedDateTime localEnd = utcEnd.withZoneSameInstant(Main.LOCALTIMEZONE);
            LocalDateTime localStartDT = localStart.toLocalDateTime();
            LocalDateTime localEndDT = localEnd.toLocalDateTime();
            Timestamp localStartTS = Timestamp.valueOf(localStartDT);
            Timestamp localEndTS = Timestamp.valueOf(localEndDT);
            int userId = rs.getInt(8);
            int contactId = rs.getInt(9);
            int customerId = rs.getInt(10);
            Appointment appointment = new Appointment(id, title, description, location,
                    type, localStartTS, localEndTS, userId, contactId, customerId);
            Appointment.addAppointment(appointment);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This is a lambda function for loading contact data.
     */
    public static Loadable loadContacts = rs -> {
        try {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            Contact contact = new Contact(id, name, email);
            Contact.addContact(contact);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This is a lambda function for loading country data.
     */
    public static Loadable loadCountries = rs -> {
        try {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            Country country = new Country(id, name);
            Country.addCountry(country);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This is a lambda function for loading first level division data.
     */
    public static Loadable loadFLDs = rs -> {
        try {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int countryId = rs.getInt(3);
            FLD fld = new FLD(id, name, countryId);
            FLD.addFLD(fld);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This is a lambda function for loading user data.
     */
    public static Loadable loadUsers = rs -> {
        try {
            int id = rs.getInt(1);
            String username = rs.getString(2);
            User user = new User(id, username);
            User.addUser(user);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };


    /**
     * This is a lambda function for loading a monthly report of appointment metrics.
     */
    public static Loadable loadMonthlyReport = rs -> {
        try {
            int year = rs.getInt(1);
            int month = rs.getInt(2);
            String type = rs.getString(3);
            int total = rs.getInt(4);
            MonthReport rpt = new MonthReport(year, month, type, total);
            MonthReport.addReport(rpt);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This is a lambda function for loading a contact schedules report.
     */
    public static Loadable loadContactReport = rs -> {
        try {
            String name = rs.getString(1);
            int appointmentId = rs.getInt(2);
            String title = rs.getString(3);
            String description = rs.getString(4);
            Timestamp start = rs.getTimestamp(5);
            Timestamp end = rs.getTimestamp(6);
            int customerId = rs.getInt(7);
            String type = rs.getString(8);
            ContactReport rpt = new ContactReport(name, appointmentId, title, type, description, start, end, customerId);
            ContactReport.addReport(rpt);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This is a lambda function for loading country/first level division metrics report.
     */
    public static Loadable loadCountryAndFLDReport = rs -> {
        try {
            String country = rs.getString(1);
            String fld = rs.getString(2);
            int totalCustomers = rs.getInt(3);
            int totalAppointments = rs.getInt(4);
            CountryAndFLDReport rpt = new CountryAndFLDReport(country, fld, totalCustomers, totalAppointments);
            CountryAndFLDReport.addReport(rpt);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    };

    /**
     * This method loads all the reports for the application from the database
     */
    public static void loadAllReports() {
        MonthReport.reset();
        ContactReport.reset();
        CountryAndFLDReport.reset();
        new Thread(() -> queryDB(monthlyReportQuery, loadMonthlyReport)).start();
        new Thread(() -> queryDB(contactReportQuery, loadContactReport)).start();
        new Thread(() -> queryDB(countryAndFLDReportQuery, loadCountryAndFLDReport)).start();
    }

    /**
     * This method loads all the data needed by the application to system memory
     * from the database for fast access.
     * discussion of lambda
     * Lambda functions are used in this method to create separate threads that
     * each execute a query increasing performance of the application.
     */
    public static void loadData(){
        new Thread(() -> queryDB(appointmentQuery, loadAppointments)).start();
        new Thread(() -> queryDB(customerQuery, loadCustomers)).start();
        new Thread(() -> queryDB(contactQuery, loadContacts)).start();
        new Thread(() -> queryDB(countryQuery, loadCountries)).start();
        new Thread(() -> queryDB(FLDQuery, loadFLDs)).start();
        new Thread(() -> queryDB(userQuery, loadUsers)).start();
        loadAllReports();
    }
}
