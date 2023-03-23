package fr.ird.driver.observe.dao.data.ps.landing;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.landing.Landing;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class LandingDaoTest {
    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource(Path.of(new File("").getAbsolutePath()).resolve("target").resolve("observe-test"));

    @Test
    public void findById() {
        Landing result = resource.getService().getDaoSupplier().getPsLandingLandingDao().findById(Ids.get(Landing.class));
        Assert.assertNotNull(result);
    }

}