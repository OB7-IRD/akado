package fr.ird.driver.observe.dao.data.ps.common;

import fr.ird.driver.observe.ObserveTestH2ServerDatabaseResource;
import fr.ird.driver.observe.service.ObserveService;
import org.junit.Rule;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VersionDaoServerTest extends AbstractVersionDaoTest {
    @Rule
    public ObserveTestH2ServerDatabaseResource resource = new ObserveTestH2ServerDatabaseResource();

    @Override
    protected ObserveService service() {
        return resource.getService();
    }
}