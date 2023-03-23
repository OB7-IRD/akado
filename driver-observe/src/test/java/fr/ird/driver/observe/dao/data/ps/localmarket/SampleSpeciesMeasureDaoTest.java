package fr.ird.driver.observe.dao.data.ps.localmarket;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.localmarket.SampleSpeciesMeasure;
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
public class SampleSpeciesMeasureDaoTest {
    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource(Path.of(new File("").getAbsolutePath()).resolve("target").resolve("observe-test"));

    @Test
    public void findById() {
        SampleSpeciesMeasure result = resource.getService().getDaoSupplier().getPsLocalmarketSampleSpeciesMeasureDao().findById(Ids.get(SampleSpeciesMeasure.class));
        Assert.assertNotNull(result);
    }

}