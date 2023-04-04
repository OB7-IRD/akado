package fr.ird.common;

import io.ultreia.java4all.util.sql.SqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
                if (!ignoreSQLException(
                        ((SQLException) e).
                                getSQLState())) {

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

    public static <O> Optional<O> findFirstFirst(Connection connection, SqlQuery<O> query) {
        try (PreparedStatement statement = query.prepareQuery(connection); ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return Optional.of(query.prepareResult(rs));
            }
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        }
        return Optional.empty();
    }

    public static <O> List<O> findList(Connection connection, SqlQuery<O> query) {
        List<O> result = new LinkedList<>();
        try (PreparedStatement statement = query.prepareQuery(connection); ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                O row = query.prepareResult(rs);
                result.add(row);
            }
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        }
        return result;
    }
}
