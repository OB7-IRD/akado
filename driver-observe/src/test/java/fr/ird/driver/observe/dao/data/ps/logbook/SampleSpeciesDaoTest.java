package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.ObserveTestH2DatabaseResource;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleSpeciesDaoTest {
    @Rule
    public ObserveTestH2DatabaseResource resource = new ObserveTestH2DatabaseResource();

    @Test
    public void findById() {
        SampleSpecies result = resource.getService().getDaoSupplier().getPsLogbookSampleSpeciesDao().findById(Ids.get(SampleSpecies.class));
        Assert.assertNotNull(result);
    }

}