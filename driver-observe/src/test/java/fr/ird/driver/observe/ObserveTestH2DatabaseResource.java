package fr.ird.driver.observe;

import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.ExternalResource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
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

    public ObserveTestH2DatabaseResource() {
        this(Path.of(new File("").getAbsolutePath()).resolve("target").resolve("observe-test"));
    }

    public ObserveTestH2DatabaseResource(Path dbPath) {
        this.dbPath = Objects.requireNonNull(dbPath);
        this.databasePath = dbPath.resolve("observe.mv.db");
    }

    @Override
    protected void before() throws SQLException {
        boolean exist = isExist();
        JdbcConfiguration configuration = ObserveService.createH2DatabaseConfiguration(databasePath.getParent());
        if (!exist) {
            log.info("Will create observe-test database at: {}", databasePath);
            String sqlScript = Objects.requireNonNull(getClass().getClassLoader().getResource("observe-v9_1_0-RC-2-akado.sql")).getFile();
            log.info("Use script to create observe-test database: {}", sqlScript);
            service.loadH2Database(Path.of(sqlScript), configuration);
        } else {
            service.init(configuration);
            service.open();
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
