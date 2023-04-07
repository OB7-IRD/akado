package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ActivityDaoTest {
    public static final int ACTIVITY_COUNT = 3;

    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource();

    @Test
    public void findById() {
        Activity result = resource.getService().getDaoSupplier().getPsLogbookActivityDao().findById(Ids.get(Activity.class));
        Assert.assertNotNull(result);
    }

    @Test
    public void count() {
        long result = resource.getService().getDaoSupplier().getPsLogbookActivityDao().count();
        Assert.assertEquals(ACTIVITY_COUNT, result);
    }

}