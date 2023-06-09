package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleDaoTest {
    public static final int SAMPLE_COUNT = 2;
    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource();

    @Test
    public void findById() {
        Sample result = resource.getService().getDaoSupplier().getPsLogbookSampleDao().findById(Ids.get(Sample.class));
        Assert.assertNotNull(result);
    }

    @Test
    public void count() {
        long result = resource.getService().getDaoSupplier().getPsLogbookSampleDao().count();
        Assert.assertEquals(SAMPLE_COUNT, result);
    }
}