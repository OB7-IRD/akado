package fr.ird.driver.observe;

import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assume;
import org.junit.rules.ExternalResource;

import java.sql.SQLException;
import java.util.Objects;

/**
 * To get access to a observe database on Postgresql.
 * <p>
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveTestPGDatabaseResource extends ExternalResource {
    private static final Logger log = LogManager.getLogger(ObserveTestH2DatabaseResource.class);
    protected final JdbcConfiguration configuration;
    private final ObserveService service = ObserveService.getService();

    public ObserveTestPGDatabaseResource(JdbcConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    @Override
    protected void before() throws SQLException {
        service.init(configuration);
        try {
            service.open();
        } catch (Exception e) {
            log.error(e);
            Assume.assumeFalse("Could not open data source", true);
        }
    }

    public ObserveService getService() {
        return service;
    }

    @Override
    protected void after() {
        service.close();
    }
}
