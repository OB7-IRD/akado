package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.logbook.WellActivity;
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
public class WellDaoTest {
    public static final int WELL_COUNT = 1;
    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource(Path.of(new File("").getAbsolutePath()).resolve("target").resolve("observe-test"));

    @Test
    public void findById() {
        WellActivity result = resource.getService().getDaoSupplier().getPsLogbookWellActivityDao().findById(Ids.get(WellActivity.class));
        Assert.assertNotNull(result);
    }

    @Test
    public void count() {
        WellDao dao = resource.getService().getDaoSupplier().getPsLogbookWellDao();
        long actual = dao.count();
        Assert.assertEquals(WELL_COUNT, actual);
    }

}