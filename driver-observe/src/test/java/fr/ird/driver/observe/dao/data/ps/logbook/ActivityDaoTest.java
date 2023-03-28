package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import io.ultreia.java4all.util.Dates;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ActivityDaoTest {
    public static final int ACTIVITY_COUNT = 3;
    public static final Date FIRST_ACTIVITY_DATE = Dates.createDate(30, ACTIVITY_COUNT, 2019);
    public static final Date LAST_ACTIVITY_DATE = Dates.createDate(1, 4, 2019);
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

    @Test
    public void firstActivityDate() {
        Date result = resource.getService().getDaoSupplier().getPsLogbookActivityDao().firstActivityDate();
        Assert.assertEquals(FIRST_ACTIVITY_DATE, result);
    }

    @Test
    public void lastActivityDate() {
        Date result = resource.getService().getDaoSupplier().getPsLogbookActivityDao().lastActivityDate();
        Assert.assertEquals(LAST_ACTIVITY_DATE, result);
    }
}