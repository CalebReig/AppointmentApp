package main.db;

import java.sql.ResultSet;

/**
 * This interface allows for the <em>DBDataLoader</em> lambda functions
 * to be passed as a parameter to another class method.
 */
public interface Loadable {

    public void apply(ResultSet rs);

}
