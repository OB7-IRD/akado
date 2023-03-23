package fr.ird.driver.observe.dao.referential;

import fr.ird.driver.observe.service.ObserveService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class AbstractReferentialCacheTest {

    protected abstract ObserveService service();

    @Test
    public void load() {
        ReferentialCache referentialCache = new ReferentialCache();
        referentialCache.load(service().getDaoSupplier());
        referentialCache.cache().values().stream().flatMap(m -> m.values().stream()).forEach(Assert::assertNotNull);
    }

}