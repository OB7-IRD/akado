/* 
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth;

import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.ElementaryCatch;
import fr.ird.driver.avdth.business.FishingContext;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleSpecies;
import fr.ird.driver.avdth.business.SampleSpeciesFrequency;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Well;
import fr.ird.driver.avdth.business.WellPlan;
import fr.ird.driver.avdth.common.AvdthUtils;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.ActivityDAO;
import fr.ird.driver.avdth.dao.ElementaryCatchDAO;
import fr.ird.driver.avdth.dao.FishingContextDAO;
import fr.ird.driver.avdth.dao.HarbourDAO;
import fr.ird.driver.avdth.dao.SampleDAO;
import fr.ird.driver.avdth.dao.TripDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.dao.WellDAO;
import fr.ird.driver.avdth.service.AvdthService;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 13 juin 2014
 */
public class ConsistentTest extends TestCase {

    public final static boolean DELETE = true;

    public static String AVDTH_DB_TEST = "/France_OA_2013_Complete_V35.mdb";
    private final String avdthDatabasePath;
    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";

    public ConsistentTest() {
        avdthDatabasePath = JDBC_ACCES_PROTOCOL + ConsistentTest.class.getResource(AVDTH_DB_TEST).getFile();
    }

    @BeforeClass
    public void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public void tearDownAfterClass() throws Exception {

    }

    @Override
    public void setUp() throws AvdthDriverException {
        AvdthService.getService().init(avdthDatabasePath, "com.hxtt.sql.access.AccessDriver", "", "");
    }

    @Override
    public void tearDown() {
        AvdthService.getService().close();
    }

    public void testBuildExtendedTrip() throws AvdthDriverException {
        List<Trip> trips = (new TripDAO()).allTrips();
        assertNotNull(trips);
        assertEquals(103, trips.size());

        List<List<Trip>> extendedTrips = AvdthUtils.buildExtendedTrips(trips);
        assertNotNull(extendedTrips);
        for (List<Trip> l : extendedTrips) {
            System.out.println("********************");
            for (Trip t : l) {
                System.out.println(t.getID());
            }
        }
        assertEquals(87, extendedTrips.size());
        List<Trip> tripsTMP = new ArrayList<>();
        for (List<Trip> l : extendedTrips) {
            tripsTMP.addAll(l);
        }
        assertEquals(trips.size(), tripsTMP.size());

        trips = (new TripDAO()).findTrips((new VesselDAO()).findVesselByCode(491), null, null);
        assertNotNull(trips);
        assertEquals(13, trips.size());

        extendedTrips = AvdthUtils.buildExtendedTrips(trips);
        assertNotNull(extendedTrips);
        assertEquals(10, extendedTrips.size());

        assertEquals(1, extendedTrips.get(0).size());
        assertEquals(2, extendedTrips.get(3).size());
    }

    public void testConsistentTrip() throws AvdthDriverException {
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(401, new DateTime(2013, 1, 31, 0, 0));
        assertNotNull(trip);
        System.out.println("TRIP " + trip);

        assertEquals((new HarbourDAO()).findHarbourByCode(2), trip.getDepartureHarbour());
        assertEquals((new HarbourDAO()).findHarbourByCode(2), trip.getLandingHarbour());
        assertEquals(33.055f, trip.getLocalMarketWeight());
        assertEquals(259.2070007324219d, trip.getTotalLandingWeight());
        assertEquals(1, trip.getFlagCaleVide());
        assertEquals(6500, trip.getLoch());

        assertEquals(41, trip.getActivites().size());

        Activity activity = (new ActivityDAO()).findActivityByTripAndDateOfActivityAndNum(trip, new DateTime(2012, 12, 26, 0, 0), 1);
        assertEquals(activity, trip.getActivites().get(0));

        activity = (new ActivityDAO()).findActivityByTripAndDateOfActivityAndNum(trip, new DateTime(2012, 12, 28, 0, 0), 1);
        assertEquals(2, activity.getElementaryCatchs().size());

        int count = 0;
        for (Activity a : trip.getActivites()) {
            count += a.getElementaryCatchs().size();
        }
        assertEquals(31, count);
    }

    public void testConsistentSample() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findSampleByVessefIdAndDate(401, new DateTime(2013, 01, 31, 0, 0));
        assertNotNull(samples);
        assertEquals(2, samples.size());

        Sample sample = (new SampleDAO()).findSampleByVessefIdAndDate(401, new DateTime(2013, 01, 31, 0, 0), 20);
        assertNotNull(sample);
        assertEquals(4, sample.getSampleSpecies().size());
        SampleSpecies sampleSpecies = sample.getSampleSpecies().get(0);
        assertNotNull(sampleSpecies);
        assertEquals(1, sampleSpecies.getSpecies().getCode());
        assertEquals(2, sampleSpecies.getLdlf());
        assertEquals(13, sampleSpecies.getSampleSpeciesFrequencys().size());
        for (SampleSpeciesFrequency sampleSpeciesFrequency : sample.getSampleSpecies().get(0).getSampleSpeciesFrequencys()) {
            if (sampleSpeciesFrequency.getLengthClass() == 40) {
                assertEquals(1, sampleSpeciesFrequency.getNumber());
            }
            if (sampleSpeciesFrequency.getLengthClass() == 44 || sampleSpeciesFrequency.getLengthClass() == 45) {
                assertEquals(3, sampleSpeciesFrequency.getNumber());
            }
        }
    }

    public void testConsistentWell() throws AvdthDriverException {
        List<Well> wells = (new WellDAO()).findWells(401, new DateTime(2013, 01, 31, 0, 0));
        assertNotNull(wells);
        assertEquals(6, wells.size());

        Well well = (new WellDAO()).findWell(401, new DateTime(2013, 01, 31, 0, 0), 2, 2);
        assertNotNull(well);
        assertNotNull(well.getWellPlans());
        assertEquals(3, well.getWellPlans().size());

        WellPlan wellPlan = well.getWellPlans().get(0);
        assertNotNull(wellPlan);
        assertEquals(362, wellPlan.getNumber());
        assertEquals(1, wellPlan.getSpecies().getCode());
        assertEquals(2, wellPlan.getWeightCategory().getCode());
        assertEquals(35d, wellPlan.getWeight());
    }

    public void testConsistentActivity() throws AvdthDriverException {
        Activity activity = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(401, new DateTime(2013, 1, 31, 0, 0), new DateTime(2012, 12, 28, 0, 0), 1);
        assertNotNull(activity);

        assertNotNull(activity.getFishingContexts());
        assertEquals(1, activity.getFishingContexts().size());

        assertNotNull(activity.getElementaryCatchs());
        assertEquals(2, activity.getElementaryCatchs().size());

    }

    public void testConsistentFishingContext() throws AvdthDriverException {
        List<FishingContext> fishingContexts = (new FishingContextDAO()).findFishingContextByActivity(401, new DateTime(2013, 1, 31, 0, 0), new DateTime(2012, 12, 28, 0, 0), 1);
        assertNotNull(fishingContexts);
        assertEquals(1, fishingContexts.size());

    }

    public void testConsistentElementaryCatch() throws AvdthDriverException {
        List<ElementaryCatch> elementaryCatchs = (new ElementaryCatchDAO()).findAllCatchesOfActivity(401, new DateTime(2013, 1, 31, 0, 0), new DateTime(2012, 12, 28, 0, 0), 1);
        assertNotNull(elementaryCatchs);
        assertEquals(2, elementaryCatchs.size());

    }
}
