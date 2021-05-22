package main.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This interface allows for the <em>DBDataInserter</em> lambda function
 * to be passed as a parameter to another class method.
 */
public interface Insertable {

    public void apply(PreparedStatement prepStmt, String[] vals) throws SQLException;

}
