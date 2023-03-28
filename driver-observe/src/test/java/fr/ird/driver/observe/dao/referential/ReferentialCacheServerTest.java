package fr.ird.driver.observe.dao.referential;

import fr.ird.driver.observe.ObserveTestH2ServerDatabaseResource;
import fr.ird.driver.observe.service.ObserveService;
import org.junit.Rule;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ReferentialCacheServerTest extends AbstractReferentialCacheTest {

    @Rule
    public ObserveTestH2ServerDatabaseResource resource = new ObserveTestH2ServerDatabaseResource();

    @Override
    protected ObserveService service() {
        return resource.getService();
    }
}