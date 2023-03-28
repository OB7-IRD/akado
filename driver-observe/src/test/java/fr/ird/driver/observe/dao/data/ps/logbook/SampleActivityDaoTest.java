package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.logbook.SampleActivity;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleActivityDaoTest {
    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource();

    @Test
    public void findById() {
        SampleActivity result = resource.getService().getDaoSupplier().getPsLogbookSampleActivityDao().findById(Ids.get(SampleActivity.class));
        Assert.assertNotNull(result);
    }

}