package fr.ird.driver.observe;

import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import io.ultreia.java4all.util.sql.conf.JdbcConfigurationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.Server;
import org.junit.Assume;

import java.sql.SQLException;

/**
 * To get access to a observe database on H2 Server.
 * <p>
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveTestH2ServerDatabaseResource extends ObserveTestH2DatabaseResource {

    private static final Logger log = LogManager.getLogger(ObserveTestH2ServerDatabaseResource.class);
    private Server server;

    public ObserveTestH2ServerDatabaseResource() {
    }

    @Override
    protected void before() throws SQLException {
        Assume.assumeTrue("observe-test database does not exist, can not execute server mode", isExist());

        server = Server.createTcpServer("-tcp",
                                        "-tcpAllowOthers",
                                        "-ifExists",
                                        "-baseDir", dbPath.toString(),
                                        "-tcpDaemon",
                                        "-tcpPort",
                                        "9093");

        log.info(String.format("Starting H2 server at %s", server.getURL()));
        server.start();

        JdbcConfiguration configuration = new JdbcConfigurationBuilder().forDatabase(String.format(ObserveService.H2_SERVER_URL, "observe"), "sa", "sa");
        getService().init(configuration);
        getService().open();
    }

    @Override
    protected void after() {
        super.after();
        if (server != null) {
            log.info(String.format("Stopping H2 server at %s", server.getURL()));
            server.stop();
        }
    }
}
