/*
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.akado.avdth;

import fr.ird.akado.avdth.metatrip.RaisingFactorInspector;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.trip.ActivityInspector;
import fr.ird.akado.avdth.trip.FishingTimeInspector;
import fr.ird.akado.avdth.trip.HarbourInspector;
import fr.ird.akado.avdth.trip.LandingConsistentInspector;
import fr.ird.akado.avdth.trip.LandingTotalWeightInspector;
import fr.ird.akado.avdth.trip.RecoveryTimeInspector;
import fr.ird.akado.avdth.trip.TemporalLimitInspector;
import fr.ird.akado.avdth.trip.TimeAtSeaInspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.AkadoMessage;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.TripDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.service.AvdthService;
import io.ultreia.java4all.util.sql.conf.JdbcConfigurationBuilder;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Assert;

import java.nio.file.Path;
import java.util.List;

/**
 * This test class checks all trips inspectors.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 6 juin 2014
 * @since 2.0
 */
public class TripTest extends TestCase {

    public final static boolean DELETE = true;
    private static final String AVDTH_DB_TEST = "/akado_avdth_test_350.mdb";
    private static final String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    private static final String JDBC_ACCESS_PROTOCOL = "jdbc:Access:///";
    private final String avdthDatabasePath;

    public TripTest() {
        avdthDatabasePath = JDBC_ACCESS_PROTOCOL + ActivityTest.class.getResource(AVDTH_DB_TEST).getFile();
    }

    @Override
    public void setUp() throws AvdthDriverException {
        AvdthService.getService().init(avdthDatabasePath, JDBC_ACCESS_DRIVER, "", "");
        AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;
    }

    @Override
    public void tearDown() {
        AvdthService.getService().close();
    }

    public void testRaisingFactorPartialLanding() throws AvdthDriverException {
        Vessel vessel = (new VesselDAO()).findVesselByCode(491);
        List<Trip> trips = (new TripDAO()).findTrips(vessel, null, null);
        assertNotNull(trips);
        assertEquals(2, trips.size());

        double catchesWeight = 0;
//        double localMarketWeight = 0;
        double landingWeight = 0;
        for (Trip trip : trips) {
            catchesWeight += RaisingFactorInspector.catchesWeight(trip);
            landingWeight += trip.getTotalLandingWeight();
//            localMarketWeight += trip.getLocalMarketWeight();
        }

        assertEquals(596d, catchesWeight);
//        assertEquals(56.560001373291016, localMarketWeight);
        assertEquals(566.2120056152344, landingWeight);

        assertEquals(0.9500201436497221, landingWeight / catchesWeight);
//        assertEquals(1.044919474812962, (landingWeight + localMarketWeight) / catchesWeight);

        RaisingFactorInspector inspector = new RaisingFactorInspector();
        inspector.set(trips);

        Results results = inspector.execute();
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(0, results.size());
    }

    public void testRaisingFactorPartialLanding2() throws AvdthDriverException {
        Vessel vessel = (new VesselDAO()).findVesselByCode(401);
        List<Trip> trips = (new TripDAO()).findTrips(vessel, null, null);
        assertNotNull(trips);
        assertEquals(2, trips.size());

        double catchesWeight = 0;
//        double localMarketWeight = 0;
        double landingWeight = 0;
        for (Trip trip : trips) {
            catchesWeight += RaisingFactorInspector.catchesWeight(trip);
            landingWeight += trip.getTotalLandingWeight();
//            localMarketWeight += trip.getLocalMarketWeight();
        }

        assertEquals(976d, catchesWeight);
//        assertEquals(56.560001373291016, localMarketWeight);
        assertEquals(1048.9220275878906, landingWeight);

        assertEquals(1.0747151922007077, landingWeight / catchesWeight);
//        assertEquals(1.044919474812962, (landingWeight + localMarketWeight) / catchesWeight);

        RaisingFactorInspector inspector = new RaisingFactorInspector();
        inspector.set(trips);

        Results results = inspector.execute();
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(1, results.size());
    }

    public void testRaisingFactor() throws AvdthDriverException {
        Vessel vessel = (new VesselDAO()).findVesselByCode(703);
        List<Trip> trips = (new TripDAO()).findTrips(vessel, null, null);
        assertNotNull(trips);
        assertEquals(3, trips.size());
        Trip trip = trips.get(0);
        double catchesWeight = RaisingFactorInspector.catchesWeight(trip);
        assertEquals(0.29915323565083163, trip.getTotalLandingWeight() / catchesWeight);
//        assertEquals(0.29915323565083163, (trip.getTotalLandingWeight() + trip.getLocalMarketWeight()) / catchesWeight);
        trip = trips.get(1);
        catchesWeight = RaisingFactorInspector.catchesWeight(trip);
        assertEquals(535.906005859375, trip.getTotalLandingWeight() / catchesWeight);
//        assertEquals(556.326005935669, (trip.getTotalLandingWeight() + trip.getLocalMarketWeight()) / catchesWeight);

        RaisingFactorInspector inspector = new RaisingFactorInspector();
        inspector.set(trips);

        Results results = inspector.execute();
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(2, results.size());

//        inspector = new RaisingFactorInspector();
//        inspector.set((new TripDAO()).findAllTrips());
//        results = inspector.execute();
//        
//        for (int i = 0; i < results.size(); i++) {
//            System.out.println(results.get(i).getMessage());
//        }
//        assertEquals(6, results.size());
    }

    public void testActivity() throws AvdthDriverException {
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(490, new DateTime(2014, 02, 05, 0, 0));
        assertNotNull(trip);

        ActivityInspector inspector = new ActivityInspector();
        inspector.set(trip);
        Results results = inspector.execute();

        assertEquals(1, results.size());

        trip = (new TripDAO()).findTripByVesselIdAndDate(401, new DateTime(2014, 01, 06, 0, 0));
        assertNotNull(trip);

        inspector = new ActivityInspector();
        inspector.set(trip);

        results = inspector.execute();
        assertEquals(1, results.size());

        results = new Results();
        for (Trip t : (new TripDAO()).findAllTrips()) {
            assertNotNull(t);
            inspector = new ActivityInspector();
            inspector.set(t);
            results.addAll(inspector.execute());
        }
        assertEquals(3, results.size());

    }

    public void testFishingTime() throws AvdthDriverException {
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(401, new DateTime(2014, 01, 06, 0, 0));
        assertNotNull(trip);

        FishingTimeInspector inspector = new FishingTimeInspector();
        inspector.set(trip);
        Results results = inspector.execute();
        assertNotNull(results);
        assertNotSame(trip.getFishingTime(), FishingTimeInspector.fishingTimeExpected(trip));
        assertEquals(1, results.size());

        trip = (new TripDAO()).findTripByVesselIdAndDate(483, new DateTime(2014, 01, 21, 0, 0));
        assertNotNull(trip);

        inspector = new FishingTimeInspector();
        inspector.set(trip);
        results = inspector.execute();
        assertNotNull(results);

        assertEquals(0, results.size());

        results = new Results();
        for (Trip t : (new TripDAO()).findAllTrips()) {
            assertNotNull(t);
            inspector = new FishingTimeInspector();
            inspector.set(t);
            results.addAll(inspector.execute());
        }
//        for (int i = 0; i < results.size(); i++) {
//            System.out.println(results.get(i).get());
//        }
        assertEquals(4, results.size());

    }

    public void testLandingTotalWeight() throws AvdthDriverException {
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(483, new DateTime(2014, 01, 21, 0, 0));
        assertNotNull(trip);

        LandingTotalWeightInspector inspector = new LandingTotalWeightInspector();
        inspector.set(trip);
        Results results = inspector.execute();
        assertNotNull(results);

        assertEquals(1, results.size());

        trip = (new TripDAO()).findTripByVesselIdAndDate(401, new DateTime(2014, 01, 6, 0, 0));
        assertNotNull(trip);

        inspector.set(trip);
        results = inspector.execute();
        assertNotNull(results);

        assertEquals(0, results.size());

        results = new Results();
        for (Trip t : (new TripDAO()).findAllTrips()) {
            assertNotNull(t);
            inspector = new LandingTotalWeightInspector();
            inspector.set(t);
            results.addAll(inspector.execute());
        }
        for (int i = 0; i < results.size(); i++) {

//            System.out.println(results.get(i).get());
            System.out.println(results.get(i).getMessage().toString());
        }
        assertEquals(2, results.size());

    }

    public void testRecoveryTime() throws AvdthDriverException {
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(427, new DateTime(2014, 01, 16, 0, 0));
        assertNotNull(trip);

        RecoveryTimeInspector inspector = new RecoveryTimeInspector();
        inspector.set(trip);
        Results results = inspector.execute();
        assertNotNull(results);
        assertEquals(1, results.size());

        results = new Results();
        for (Trip t : (new TripDAO()).findAllTrips()) {
            assertNotNull(t);
            inspector = new RecoveryTimeInspector();
            inspector.set(t);
            results.addAll(inspector.execute());
        }
//        for (int i = 0; i < results.size(); i++) {
//            System.out.println(results.get(i).get());
//        }
        assertEquals(2, results.size());

    }

    public void testTemporalLimit() throws AvdthDriverException {
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(401, new DateTime(2014, 01, 06, 0, 0));
        assertNotNull(trip);

        TemporalLimitInspector inspector = new TemporalLimitInspector();
        inspector.set(trip);
        Results results = inspector.execute();
        assertNotNull(results);

        assertEquals(1, results.size());

        results = new Results();
        for (Trip t : (new TripDAO()).findAllTrips()) {
            assertNotNull(t);
            inspector = new TemporalLimitInspector();
            inspector.set(t);
            results.addAll(inspector.execute());
        }
        assertEquals(1, results.size());
    }

    public void testTimeAtSea() throws AvdthDriverException {
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(401, new DateTime(2014, 01, 06, 0, 0));
        assertNotNull(trip);

        TimeAtSeaInspector inspector = new TimeAtSeaInspector();
        inspector.set(trip);
        Results results = inspector.execute();
        assertNotNull(results);

        assertEquals(1, results.size());

        trip = (new TripDAO()).findTripByVesselIdAndDate(483, new DateTime(2014, 01, 21, 0, 0));
        assertNotNull(trip);

        inspector = new TimeAtSeaInspector();
        inspector.set(trip);
        results = inspector.execute();
        assertNotNull(results);

        assertEquals(0, results.size());

        results = new Results();
        for (Trip t : (new TripDAO()).findAllTrips()) {
            assertNotNull(t);
            inspector = new TimeAtSeaInspector();
            inspector.set(t);
            results.addAll(inspector.execute());
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).get());
        }
        assertEquals(4, results.size());

    }

    public void testLandingConsistent() throws AvdthDriverException {
        List<Trip> trips = (new TripDAO()).allTrips();
        assertNotNull(trips);

        LandingConsistentInspector inspector = new LandingConsistentInspector();
        Results results = new Results();
        for (Trip trip : trips) {
            inspector.set(trip);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        assertEquals(0, results.size());
    }

    public void testHarbourConsistent() throws AvdthDriverException {
        List<Trip> trips = (new TripDAO()).allTrips();
        assertNotNull(trips);

        HarbourInspector inspector = new HarbourInspector();
        Results results = new Results();
        for (Trip trip : trips) {
            inspector.set(trip);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(2, results.size());
    }

    public void testInfo() throws Exception {
        AAProperties.PROTOCOL_JDBC_ACCESS = JDBC_ACCESS_PROTOCOL;
        AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;
        AAProperties.TRIP_INSPECTOR = AAProperties.ACTIVE_VALUE;
        AAProperties.ACTIVITY_INSPECTOR = AAProperties.ACTIVE_VALUE;
        AAProperties.SAMPLE_INSPECTOR = AAProperties.ACTIVE_VALUE;
        AAProperties.WELL_INSPECTOR = AAProperties.ACTIVE_VALUE;
        Path directory = SampleTest.initStandardDirectory();
        SampleTest.initGIS(directory);
        AvdthInspector inspector = new AvdthInspector(new JdbcConfigurationBuilder().forDatabase(avdthDatabasePath, "", "", JDBC_ACCESS_DRIVER));
        inspector.info();
        Assert.assertEquals(1, inspector.getAkadoMessages().size());
        for (AkadoMessage akadoMessage : inspector.getAkadoMessages()) {
            log.info(akadoMessage.getContent());
        }
    }

    private static final Logger log = LogManager.getLogger(TripTest.class);

}
