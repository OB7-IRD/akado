package fr.ird.driver.observe.dao.data.ps.common;

import fr.ird.driver.observe.business.ObserveVersion;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.Version;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class AbstractVersionDaoTest {

    protected abstract ObserveService service();

    @Test
    public void testVersion() {
        Version versionNumber = service().getDaoSupplier().getVersionDao().getVersionNumber();
        Assert.assertNotNull(versionNumber);
        Assert.assertEquals(Version.valueOf(ObserveVersion.OBSERVE_MODEL_MAX_VERSION), versionNumber);
    }

    @Test(expected = ObserveDriverException.class)
    public void testLegacyVersion() {
        // Won't find it (we are not using a legacy observe database)
        service().getDaoSupplier().getVersionDao().getLegacyVersionNumber();
    }
}
