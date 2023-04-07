package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
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
public class RouteDaoTest {
    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource();
    public static final Date FIRST_DATE = Dates.createDate(30, 3, 2019);
    public static final Date LAST_DATE = Dates.createDate(1, 4, 2019);

    @Test
    public void findById() {
        Route result = resource.getService().getDaoSupplier().getPsLogbookRouteDao().findById(Ids.get(Route.class));
        Assert.assertNotNull(result);
    }

    @Test
    public void firstActivityDate() {
        Date result = resource.getService().getDaoSupplier().getPsLogbookRouteDao().firstDate();
        Assert.assertEquals(FIRST_DATE, result);
    }

    @Test
    public void lastActivityDate() {
        Date result = resource.getService().getDaoSupplier().getPsLogbookRouteDao().lastDate();
        Assert.assertEquals(LAST_DATE, result);
    }

}