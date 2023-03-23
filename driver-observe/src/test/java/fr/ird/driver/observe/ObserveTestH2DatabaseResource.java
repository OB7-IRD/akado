package fr.ird.driver.observe;

import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.lang.Strings;
import io.ultreia.java4all.util.TimeLog;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import io.ultreia.java4all.util.sql.conf.JdbcConfigurationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.ExternalResource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * To get access to a observe database on H2 (loaded from script) and located in {@link #dbPath}).
 * <p>
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveTestH2DatabaseResource extends ExternalResource {
    private static final Logger log = LogManager.getLogger(ObserveTestH2DatabaseResource.class);
    protected final Path dbPath;
    protected final Path databasePath;
    private final ObserveService service = ObserveService.getService();

    public ObserveTestH2DatabaseResource(Path dbPath) {
        this.dbPath = Objects.requireNonNull(dbPath);
        this.databasePath = dbPath.resolve("observe.mv.db");
    }


    @Override
    protected void before() throws SQLException {


        boolean exist = isExist();
        JdbcConfiguration configuration = new JdbcConfigurationBuilder().forDatabase(String.format(ObserveService.H2_LOCAL_URL, databasePath.getParent().resolve("observe")), "sa", "sa");
        service.init(configuration);
        service.open();
        if (!exist) {
            log.info("Will create observe-test database at: {}", databasePath);
            String sqlScript = Objects.requireNonNull(getClass().getClassLoader().getResource("observe-v9_1_0-RC-2-akado.sql")).getFile();
            log.info("Use script to create observe-test database: {}", sqlScript);
            long t0 = TimeLog.getTime();
            try (Statement statement = service.getConnection().createStatement()) {
                statement.execute(String.format("RUNSCRIPT FROM '%s';", sqlScript));
            }
            log.info("observe-test database created! (duration: {})", Strings.convertTime(TimeLog.getTime() - t0));
        }
    }

    protected boolean isExist() {
        return Files.exists(databasePath);
    }

    public ObserveService getService() {
        return service;
    }

    @Override
    protected void after() {
        service.close();
    }
}
