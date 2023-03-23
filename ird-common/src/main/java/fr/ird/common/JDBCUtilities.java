package fr.ird.common;

import java.sql.SQLException;

/**
 * Miscellaneous class utility methods for JDBC.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 27 sept. 2013
 */
public class JDBCUtilities {

    /**
     * Affiche sur la sortie standard l'exception en paramètre en la formatant
     * préalablement.
     *
     * @param ex l'exception à afficher
     */
    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(
                        ((SQLException) e).
                        getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: "
                            + ((SQLException) e).getSQLState());

                    System.err.println("Error Code: "
                            + ((SQLException) e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    /**
     * Détermine quels sont les états SQL à ignorer.
     *
     * @param sqlState un état SQL
     * @return si l'état est à ignorer
     */
    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }
        // X0Y32: Jar file already exists in schema
        // 42Y55: Table already exists in schema
        return (sqlState.equalsIgnoreCase("X0Y32") || sqlState.equalsIgnoreCase("42Y55"));
    }
}
