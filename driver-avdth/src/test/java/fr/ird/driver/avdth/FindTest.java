/* 
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
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
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.ActivityDAO;
import fr.ird.driver.avdth.dao.CountryDAO;
import fr.ird.driver.avdth.dao.SampleDAO;
import fr.ird.driver.avdth.dao.TripDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.service.AvdthService;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Cette classe permet de tester en écriture et lecture la couche AVDTH du
 * module.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class FindTest extends TestCase {

    public final static boolean DELETE = true;

    public static String AVDTH_DB_TEST = "/France_OA_2013_Complete_V35.mdb";
    private final String avdthDatabasePath;
    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";

    public FindTest() {
        avdthDatabasePath = JDBC_ACCES_PROTOCOL + FindTest.class.getResource(AVDTH_DB_TEST).getFile();
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

    public void testActivityDAOFindActivitiesFRA() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        vessels.add((new VesselDAO()).findVesselByCode(225));
        vessels.add((new VesselDAO()).findVesselByCode(214));
        vessels.add((new VesselDAO()).findVesselByCode(551));
        countries.add((new CountryDAO()).findCountryByCode(1));
        ActivityDAO aO = new ActivityDAO();
        List<Activity> activities = aO.findActivities(vessels, countries, null, null);
        assertEquals(584, activities.size());
    }

    public void testActivityDAOFindActivitiesAllVesselsStartDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        countries.add((new CountryDAO()).findCountryByCode(1));
        ActivityDAO aO = new ActivityDAO();
        List<Activity> activities = aO.findActivities(vessels, countries, new DateTime(2013, 9, 1, 0, 0), null);
        assertEquals(1941, activities.size());
    }

    public void testActivityDAOFindActivitiesAllVesselsBetweenDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        countries.add((new CountryDAO()).findCountryByCode(1));
        ActivityDAO aO = new ActivityDAO();
        List<Activity> activities = aO.findActivities(vessels, countries, new DateTime(2013, 9, 1, 0, 0), new DateTime(2014, 1, 31, 0, 0));
//        for (Activity a : activities) {
//            System.out.println(">>>>> " + a.getMaree().getBateau().getCodeBateau() + " " + a.getMaree().getDateDebarquement() + " " + a.getDateActivite() + " " + a.getNumeroActivite());
//        }
        assertEquals(1846, activities.size());
    }

    public void testActivityDAOFindActivitiesOneVesselsBetweenDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        vessels.add((new VesselDAO()).findVesselByCode(551));
        countries.add((new CountryDAO()).findCountryByCode(1));
        ActivityDAO aO = new ActivityDAO();
        List<Activity> activities = aO.findActivities(vessels, countries, new DateTime(2013, 1, 3, 0, 0), new DateTime(2013, 2, 3, 0, 0));
//        for (Activity a : activities) {
//            System.out.println(">>>>> " + a.getMaree().getBateau().getCodeBateau() + " " + a.getMaree().getDateDebarquement() + " " + a.getDateActivite() + " " + a.getNumeroActivite());
//        }
        assertEquals(31, activities.size());
    }

    public void testActivityDAOFindActivitiesAllVesselsEndDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        countries.add((new CountryDAO()).findCountryByCode(1));
        ActivityDAO aO = new ActivityDAO();
        List<Activity> activities = aO.findActivities(vessels, countries, null, new DateTime(2013, 9, 1, 23, 59));
//        for (Activity a : activities) {
//            System.out.println(">>>>> " + a.getMaree().getBateau().getCodeBateau() + " " + a.getMaree().getDateDebarquement() + " " + a.getDateActivite() + " " + a.getNumeroActivite());
//        }
        assertEquals(3172, activities.size());
    }

    public void testActivityDAOFindActivitiesSPA() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        vessels.add((new VesselDAO()).findVesselByCode(225));
        vessels.add((new VesselDAO()).findVesselByCode(244));
        vessels.add((new VesselDAO()).findVesselByCode(551));
        countries.add((new CountryDAO()).findCountryByCode(4));
        ActivityDAO aO = new ActivityDAO();
        List<Activity> activities = aO.findActivities(vessels, countries, null, null);
        assertEquals(0, activities.size());
    }

    public void testTripDAOFindTrip() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        vessels.add((new VesselDAO()).findVesselByCode(401));
        vessels.add((new VesselDAO()).findVesselByCode(490));
        vessels.add((new VesselDAO()).findVesselByCode(244));
        vessels.add((new VesselDAO()).findVesselByCode(551));
        countries.add((new CountryDAO()).findCountryByCode(1));
        TripDAO tO = new TripDAO();
        List<Trip> trips = tO.findTrips(vessels, countries, null, null);
        assertEquals(29, trips.size());
    }

    //Date de départ de la marée
    public void testTripDAOFindTripAllVesselsStartDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        TripDAO tO = new TripDAO();
        List<Trip> trips = tO.findTrips(vessels, countries, new DateTime(2013, 9, 1, 0, 0), null);
        assertEquals(46, trips.size());
    }

    //Date de départ de la marée
    public void testTripDAOFindTripFiveVesselsStartDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        vessels.add((new VesselDAO()).findVesselByCode(482));
        vessels.add((new VesselDAO()).findVesselByCode(491));
        vessels.add((new VesselDAO()).findVesselByCode(551));
        vessels.add((new VesselDAO()).findVesselByCode(492));
        vessels.add((new VesselDAO()).findVesselByCode(703));
        TripDAO tO = new TripDAO();
        List<Trip> trips = tO.findTrips(vessels, countries, new DateTime(2013, 9, 1, 0, 0), null);
        assertEquals(26, trips.size());
    }

    public void testTripDAOFindTripAllVesselsStartDateSPA() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        countries.add((new CountryDAO()).findCountryByCode(4));
        TripDAO tO = new TripDAO();
        List<Trip> trips = tO.findTrips(vessels, countries, new DateTime(2013, 9, 1, 0, 0), null);
        assertEquals(0, trips.size());
    }

    public void testActivityDAOIsExistAnActivityFor() throws AvdthDriverException {
        Vessel v = (new VesselDAO()).findVesselByCode(401);
        DateTime d = new DateTime(2013, 1, 8, 0, 0);
        assertTrue(new ActivityDAO().isExistAnActivityFor(v, d));
        d = new DateTime(2014, 2, 21, 0, 0);
        assertFalse(new ActivityDAO().isExistAnActivityFor(v, d));
        v = (new VesselDAO()).findVesselByCode(572);
        assertFalse(new ActivityDAO().isExistAnActivityFor(v, d));
    }

    //Date de dbq de la marée
    public void testTripDAOFindTripAllVesselsEndDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        countries.add((new CountryDAO()).findCountryByCode(1));
        TripDAO tO = new TripDAO();
        List<Trip> trips = tO.findTrips(vessels, countries, null, new DateTime(2013, 9, 1, 0, 0));
        assertNotSame(103, trips.size());
        assertEquals(57, trips.size());
    }

    //Date de dbq de la marée
    public void testTripDAOFindTripAllVesselsBetweenDateFRA() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        countries.add((new CountryDAO()).findCountryByCode(1));
        TripDAO tO = new TripDAO();
        List<Trip> trips = tO.findTrips(vessels, countries, new DateTime(2014, 1, 1, 0, 0), new DateTime(2014, 5, 31, 0, 0));
        assertEquals(12, trips.size());
    }

    public void testTripDAOMisc() {
        TripDAO tO = new TripDAO();
        DateTime firstLanding = tO.firstLandingDate();
        assertEquals(new DateTime(2013, 01, 01, 0, 0), firstLanding);
        DateTime lastLanding = tO.lastLandingDate();
        assertEquals(new DateTime(2014, 02, 20, 0, 0), lastLanding);
        Integer count = tO.count();
        assertEquals(103, (int) count);
    }

    public void testActivityDAOMisc() {
        ActivityDAO aO = new ActivityDAO();
        DateTime firstActivity = aO.firstActivityDate();
        assertNotNull(firstActivity);
        assertEquals(new DateTime(2012, 11, 14, 0, 0), firstActivity);
        DateTime lastActivity = aO.lastActivityDate();
        assertEquals(new DateTime(2014, 02, 20, 0, 0), lastActivity);
        Integer count = aO.count();
        assertEquals(5100, (int) count);
    }

    public void testSampleDAOFindSampleAllVesselsStartDate() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        SampleDAO tO = new SampleDAO();
        List<Sample> samples = tO.findSample(vessels, countries, new DateTime(2013, 9, 1, 0, 0), null);
        assertEquals(151, samples.size());
    }

    public void testSampleDAOFindSample() throws AvdthDriverException {
        List<Vessel> vessels = new ArrayList<Vessel>();
        List<Country> countries = new ArrayList<Country>();
        vessels.add((new VesselDAO()).findVesselByCode(401));
        vessels.add((new VesselDAO()).findVesselByCode(490));
        vessels.add((new VesselDAO()).findVesselByCode(244));
        vessels.add((new VesselDAO()).findVesselByCode(551));
        countries.add((new CountryDAO()).findCountryByCode(1));
        SampleDAO tO = new SampleDAO();
        List<Sample> samples = tO.findSample(vessels, countries, null, null);
        assertEquals(93, samples.size());
    }
}
