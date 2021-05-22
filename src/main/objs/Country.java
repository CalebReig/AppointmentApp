package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and modifies country objects which are
 * copies of countries stored in the application database.
 */
public class Country {

    private int id;
    private String name;
    //A list of all countries
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>Country</em> class.
     *
     * @param id   The country id
     * @param name The country name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This method returns the country name.
     *
     * @return Returns the country name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the country name.
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the country id.
     *
     * @return Returns the country id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the country id.
     *
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the <em>allCountries</em> list containing all countries.
     *
     * @return Returns a list of all countries.
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /**
     * This method adds a country to the <em>allCountries</em> list.
     *
     * @param country The country to add
     */
    public static void addCountry(Country country) {
        allCountries.add(country);
    }

    /**
     * This method returns a country from the <em>allCountries</em>
     * list based on the country id.
     * @param id The id of the country to return
     * @return Returns a country object.
     */
    public static Country findCountry(int id) {
        for (Country country : allCountries) {
            if (id == country.id) {
                return country;
            }
        }
        return null;
    }
}
