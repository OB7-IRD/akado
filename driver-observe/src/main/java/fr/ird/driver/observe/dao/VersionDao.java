package fr.ird.driver.observe.dao;

import fr.ird.driver.observe.business.ObserveVersion;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.Version;
import io.ultreia.java4all.util.sql.SqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VersionDao {

    /**
     * The connection used to access database.
     */
    protected final Connection connection;

    public VersionDao() {
        this.connection = ObserveService.getService().getConnection();
    }

    public Version getVersionNumber() {

        SqlQuery<ObserveVersion> query = SqlQuery.wrap("SELECT version, date FROM common.database_version", rs -> new ObserveVersion(rs.getString(1), rs.getDate(2)));

        List<ObserveVersion> versions = new LinkedList<>();
        try (PreparedStatement statement = query.prepareQuery(connection)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ObserveVersion version = query.prepareResult(resultSet);
                    versions.add(version);
                }
            }

        } catch (SQLException e) {
            // try with legacy version
            return getLegacyVersionNumber();
        }

        if (versions.isEmpty()) {
            return null;
        }
        versions.sort(Comparator.comparing(ObserveVersion::getVersion).reversed());
        return versions.get(0).getVersion();
    }

    public Version getLegacyVersionNumber() {

        SqlQuery<ObserveVersion> query = SqlQuery.wrap("SELECT version FROM public.tms_version", rs -> new ObserveVersion(rs.getString(1), null));

        List<ObserveVersion> versions = new LinkedList<>();
        try (PreparedStatement statement = query.prepareQuery(connection)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ObserveVersion version = query.prepareResult(resultSet);
                    versions.add(version);
                }
            }
        } catch (SQLException e) {
            throw new ObserveDriverException(e);
        }
        if (versions.isEmpty()) {
            return null;
        }
        return versions.get(0).getVersion();
    }

}
