package fr.ird.driver.observe.dao.data.ps.common;

import fr.ird.driver.observe.ObserveTestH2ServerDatabaseResource;
import fr.ird.driver.observe.service.ObserveService;
import org.junit.Rule;

import java.io.File;
import java.nio.file.Path;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TripDaoServerTest extends AbstractTripDaoTest {

    @Rule
    public ObserveTestH2ServerDatabaseResource resource = new ObserveTestH2ServerDatabaseResource(Path.of(new File("").getAbsolutePath()).resolve("target").resolve("observe-test"));

    @Override
    protected ObserveService service() {
        return resource.getService();
    }
}