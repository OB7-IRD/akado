package fr.ird.driver.observe.service;

import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.DaoSupplier;
import fr.ird.driver.observe.dao.referential.ReferentialCache;
import io.ultreia.java4all.lang.Strings;
import io.ultreia.java4all.util.TimeLog;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import io.ultreia.java4all.util.sql.conf.JdbcConfigurationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveService {
    /**
     * H2 database url on server.
     */
    public static final String H2_SERVER_URL = "jdbc:h2:tcp://127.0.1.1:9093/%s";
    /**
     * H2 database url on file.
     */
    public static final String H2_LOCAL_URL =
            "jdbc:h2:file:%s;" +
                    // on peut aussi utiliser file, socket
                    "FILE_LOCK=file;" +
                    //1 or 2 is needed to restore avec crash
                    // 0: logging is disabled (faster),
                    // 1: logging of the data is enabled, but logging of the index
                    // changes is disabled (default), 2: logging of both data and index
                    // changes are enabled
                    "LOG=0;" +
                    // on peut aussi utiliser hsqldb, mysql ou postgresql
                    "MODE=postgresql;" +
                    //"MODE=hsqldb;" +
                    // Sets the default lock timeout (in milliseconds) in this
                    // database that is used for the new sessions.
                    "DEFAULT_LOCK_TIMEOUT=100;" +
                    // -1: the database is never closed until the close delay is set to
                    // some other rev or SHUTDOWN is called., 0: no delay (default; the
                    // database is closed if the last connection to it is closed)., n:
                    // the database is left open for n second after the last connection
                    // is closed.
                    "DB_CLOSE_DELAY=0;" +
                    // 0: no locking (should only be used for testing),
                    // 1: table level locking (default),
                    // 2: table level locking with garbage collection (if the
                    // application does not close all connections).
                    // LOCK_MODE 3 (READ_COMMITTED). Table level locking, but only when
                    // writing (no read locks).
                    "LOCK_MODE=3;" +
                    // Levels: 0=off, 1=error, 2=info, 3=debug.
                    "TRACE_LEVEL_FILE=0;" +
                    // on system.out: 0=off, 1=error, 2=info, 3=debug.
                    "TRACE_LEVEL_SYSTEM_OUT=0;" +
                    // maximumn cache to improve performance...
                    "CACHE_SIZE=65536;" +
                    // avoid timeout on reading tables (see http://stackoverflow.com/questions/4162557/timeout-error-trying-to-lock-table-in-h2)
                    "MVCC=true";
    /**
     * Extension of observe H2 database backup file.
     */
    public static final String SQL_GZ_EXTENSION = "sql.gz";
    /**
     * Extension of Sql script.
     */
    public static final String SQL_EXTENSION = "sql";
    private static final Logger log = LogManager.getLogger(ObserveService.class);
    /**
     * Unique instance of the service.
     */
    private static final ObserveService SERVICE = new ObserveService();
    /**
     * Contains the jdbc configuration.
     */
    private JdbcConfiguration configuration;
    /**
     * Contains the database connection, once the service was opened via method {@link #open()}.
     * <p>
     * After invoking method {@link #close()}, the connection will be again {@code null}.
     */
    private Connection connection;
    /**
     * Referential cache (lazy loading).
     */
    private ReferentialCache referentialCache;
    /**
     * Dao supplier (lazy loading).
     */
    private DaoSupplier daoSupplier;

    public static ObserveService getService() {
        return SERVICE;
    }

    public static String removeExtension(String fileName) {
        if (fileName.endsWith("." + SQL_GZ_EXTENSION)) {
            return fileName.substring(0, fileName.length() - 1 - SQL_GZ_EXTENSION.length());
        }
        if (fileName.endsWith("." + SQL_EXTENSION)) {
            return fileName.substring(0, fileName.length() - 1 - SQL_EXTENSION.length());
        }
        throw new IllegalArgumentException(String.format("Can't remove extension from file %s", fileName));
    }

    public static JdbcConfiguration createH2DatabaseConfiguration(Path directory) {
        Path dbPath = directory.resolve("observe");
        return new JdbcConfigurationBuilder().forDatabase(String.format(H2_LOCAL_URL, dbPath), "sa", "sa");
    }

    public static Path createH2DatabaseFromBackupPath(Path backupFile) {
        String dbPathName = String.format("%1$s-%2$tY_%2$tm_%2$td__%2$tH-%2$tM-%2$tS", removeExtension(backupFile.toFile().getName()), new Date());
        return backupFile.getParent().resolve(dbPathName);
    }

    public static JdbcConfiguration createH2DatabaseFromBackup(Path dbPath, Path backupFile) {
        JdbcConfiguration configuration = createH2DatabaseConfiguration(dbPath);
        ObserveService service = getService();
        try {
            service.loadH2Database(backupFile, configuration);
        } finally {
            service.close();
        }
        return configuration;
    }

    private ObserveService() {
    }

    public void loadH2Database(Path backupFile, JdbcConfiguration configuration) {
        Path dbPath = createH2DatabaseFromBackupPath(backupFile);
        log.info("Observe db path: {}", dbPath);
        init(configuration);
        open();
        String sql = String.format("RUNSCRIPT FROM '%s'", backupFile);
        if (backupFile.toFile().getName().endsWith("." + SQL_GZ_EXTENSION)) {
            sql += " COMPRESSION GZIP";
        }
        long t0 = TimeLog.getTime();
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new ObserveDriverException(String.format("Could not create observe database from backup file: %s", backupFile), e);
        }
        log.info("observe database created (at {})! (duration: {})", dbPath, Strings.convertTime(TimeLog.getTime() - t0));
    }

    public void init(JdbcConfiguration configuration) throws ObserveDriverException {
        if (!isClosed()) {
            throw new IllegalStateException("You can init service, because it is not closed!!!");
        }
        this.configuration = Objects.requireNonNull(configuration);
        this.connection = null;
    }

    public Connection getConnection() {
        return Objects.requireNonNull(connection, "The connection is null, please call ObserverService.open method before.");
    }

    public boolean isClosed() {
        return connection == null;
    }

    public void open() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(configuration.getJdbcConnectionUrl(), configuration.getJdbcConnectionUser(), configuration.getJdbcConnectionPassword());
            }
            // try a ping
            try (PreparedStatement statement = connection.prepareStatement("SELECT 1;")) {
                statement.execute();
            }
        } catch (SQLException e) {
            throw new ObserveDriverException("Could not open service", e);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new ObserveDriverException("Could not close service", e);
        } finally {
            clearInternalStates();
        }
    }

    public DaoSupplier getDaoSupplier() {
        if (daoSupplier == null) {
            daoSupplier = new DaoSupplier();
        }
        return daoSupplier;
    }

    public ReferentialCache getReferentialCache() throws ObserveDriverException {
        if (referentialCache == null) {
            referentialCache = new ReferentialCache();
            referentialCache.load(getDaoSupplier());
        }
        return referentialCache;
    }

    private void clearInternalStates() {
        if (referentialCache != null) {
            referentialCache.clear();
            referentialCache = null;
        }
        if (daoSupplier != null) {
            daoSupplier = null;
        }
    }
}
