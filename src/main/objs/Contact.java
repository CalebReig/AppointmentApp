package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and modifies contact objects which are
 * copies of contacts stored in the application database.
 */
public class Contact {

    private int id;
    private String name;
    private String email;
    //A list of all contacts
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>Contact</em> class.
     * @param id The contact id
     * @param name The contact name
     * @param email The contact email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * This method returns the contact id.
     * @return Returns the contact id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the contact id.
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the contact name.
     * @return Returns the contact name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the contact name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the <em>allContacts</em> list containing all contacts.
     * @return Returns a list of all contacts.
     */
    public static ObservableList<Contact> getAllContacts() {return allContacts;}

    /**
     * This method adds a contact to the <em>allContacts</em> list.
     * @param contact The contact to add
     */
    public static void addContact(Contact contact) {
        allContacts.add(contact);
    }

    /**
     * This method returns a contact from the <em>allContacts</em>
     * list based on the contact id.
     * @param id The id of the contact to return
     * @return Returns a contact object.
     */
    public static Contact findContact(int id) {
        for (Contact contact: allContacts) {
            if (id == contact.id) {return contact;}
        }
        return null;
    }

    /**
     * This method returns a contact from the <em>allContacts</em>
     * list based on the contact name.
     * @param name The name of the contact to return
     * @return Returns a contact object.
     */
    public static Contact findContact(String name) {
        for (Contact contact: allContacts) {
            if (name.equals(contact.name)) {return contact;}
        }
        return null;
    }
}
