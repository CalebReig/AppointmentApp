package main.objs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates and modifies first level division objects which are
 * copies of first level divisions stored in the application database.
 */
public class FLD {

    private int id;
    private String name;
    private int countryId;
    //A list of all first level divisions
    private static ObservableList<FLD> allFLDs = FXCollections.observableArrayList();

    /**
     * This is a constructor method.
     * It creates an instance of the <em>FLD</em> class.
     * @param id The division id
     * @param name The division name
     * @param countryId The country id
     */
    public FLD(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * This method returns the division id.
     * @return Returns the division id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the division id.
     * @param id The id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns the division name.
     * @return Returns the division name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the division name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the country id.
     * @return Returns the country id.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * This method adds a division to the <em>allFLDs</em> list.
     * @param fld The division to add
     */
    public static void addFLD(FLD fld) {allFLDs.add(fld);}

    /**
     * This method returns a division from the <em>allFLDs</em>
     * list based on the division id.
     * @param id The id of the division to return
     * @return Returns a division (FLD) object.
     */
    public static FLD findFLD(int id) {
        for (FLD fld: allFLDs) {
            if (id == fld.id) {return fld;}
        }
        return null;
    }

    /**
     * This method returns a division from the <em>allFLDs</em>
     * list based on the division name.
     * @param name The name of the division to return
     * @return Returns a division (FLD) object.
     */
    public static FLD findFLD(String name) {
        for (FLD fld: allFLDs) {
            if (name.equals(fld.name)) {return fld;}
        }
        return null;
    }

    /**
     * This method returns a list of divisions associated with a given country.
     * @param countryName The name of the country
     * @return Returns a list of divisions associated with a country.
     */
    public static ObservableList<FLD> findCountryFLDs(String countryName) {
        ObservableList<FLD> countryFLDs = FXCollections.observableArrayList();
        for (FLD fld: allFLDs) {
            Country country = Country.findCountry(fld.countryId);
            if (country.getName().equals(countryName)) {
                countryFLDs.add(fld);
            }
        }
        return countryFLDs;
    }
}
