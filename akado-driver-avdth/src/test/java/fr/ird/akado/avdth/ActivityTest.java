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

import fr.ird.akado.avdth.activity.EEZInspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.avdth.activity.FishingContextInspector;
import fr.ird.akado.avdth.activity.OperationtInspector;
import fr.ird.akado.avdth.activity.PositionInEEZInspector;
import fr.ird.akado.avdth.activity.PositionInspector;
import fr.ird.akado.avdth.activity.QuadrantInspector;
import fr.ird.akado.avdth.activity.WeightInspector;
import fr.ird.akado.avdth.activity.WeightingSampleInspector;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.avdth.result.Result;
import fr.ird.common.OTUtils;
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.FishingContext;
import fr.ird.driver.avdth.business.SchoolType;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.ActivityDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.service.AvdthService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * This test class checks all activities inspectors.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 6 juin 2014
 */
public class ActivityTest extends TestCase {

    public final static boolean DELETE = true;

    private final static String AVDTH_DB_TEST = "/akado_avdth_test_350.mdb";
    private final static String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    private final String avdthDatabasePath;
    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";

    public ActivityTest() throws AvdthDriverException {
        avdthDatabasePath = JDBC_ACCES_PROTOCOL + ActivityTest.class.getResource(AVDTH_DB_TEST).getFile();
    }

    @BeforeClass
    public void before() {

    }

    @AfterClass
    public void after() {

    }

    @Override
    public void setUp() throws Exception {
//        AkadoAvdthProperties.getService().init();

        AAProperties.DATE_FORMAT_XLS = "dd/mm/yyyy hh:mm";
        AAProperties.STANDARD_DIRECTORY = Path.of(System.getProperty("java.io.tmpdir")).resolve(""+System.currentTimeMillis()).toString();//ActivityTest.class.getResource("/db").getFile();

        AAProperties.SHP_COUNTRIES_PATH = ActivityTest.class.getResource("/shp/countries.shp").getFile();
//        System.out.println(AAProperties.SHP_COUNTRIES_PATH);
        AAProperties.SHP_OCEAN_PATH = ActivityTest.class.getResource("/shp/IHOSeasAndOceans.shp").getFile();
        AAProperties.SHP_HARBOUR_PATH = ActivityTest.class.getResource("/shp/harbour.shp").getFile();
        AAProperties.SHP_EEZ_PATH = ActivityTest.class.getResource("/shp/eez.shp").getFile();
//        System.out.println(AAProperties.SHP_OCEAN_PATH);
        AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;

        if (!GISHandler.getService().exists()) {
            System.out.println("Create the database! " + AAProperties.STANDARD_DIRECTORY);
            GISHandler.getService().init(AAProperties.STANDARD_DIRECTORY,
                    AAProperties.SHP_COUNTRIES_PATH,
                    AAProperties.SHP_OCEAN_PATH,
                    AAProperties.SHP_HARBOUR_PATH,
                    AAProperties.SHP_EEZ_PATH);
            GISHandler.getService().create(true);
        }

        AvdthService.getService()
                .init(avdthDatabasePath, JDBC_ACCESS_DRIVER, "", "");

    }

    @Override
    public void tearDown() throws AvdthDriverException {
        AvdthService.getService().close();
    }

//    public void testEEZ2() throws AvdthDriverException {
//        PositionsInEEZInspector inspector = new PositionsInEEZInspector();
//        List<Activity> activities = (new ActivityDAO()).findAllActivities();
//        assertNotNull(activities);
//        assertEquals(916, activities.size());
//
//        DateTime start = DateTime.now();
//        PositionsInEEZInspector.activityPositionAndEEZInconsistent(activities.subList(0, 10));
//        DateTime end = DateTime.now();
//        LogService.getService().logApplicationDebug("10 points: " + Seconds.secondsBetween(start, end).getSeconds() + " seconds.");
//
//        start = DateTime.now();
//        PositionsInEEZInspector.activityPositionAndEEZInconsistent(activities.subList(0, 100));
//        end = DateTime.now();
//        LogService.getService().logApplicationDebug("100 points: " + Seconds.secondsBetween(start, end).getSeconds() + " seconds.");
//
//        start = DateTime.now();
//        PositionsInEEZInspector.activityPositionAndEEZInconsistent(activities.subList(0, 500));
//        end = DateTime.now();
//        LogService.getService().logApplicationDebug("500 points: " + Seconds.secondsBetween(start, end).getSeconds() + " seconds.");
//
//        start = DateTime.now();
//        PositionsInEEZInspector.activityPositionAndEEZInconsistent(activities);
//        end = DateTime.now();
//        LogService.getService().logApplicationDebug("All points: " + Seconds.secondsBetween(start, end).getSeconds() + " seconds.");
//
//    }

    public void testEEZ() throws AvdthDriverException {
        PositionInEEZInspector inspector = new PositionInEEZInspector();
        assertEquals("CIV", inspector.getEEZ(-5.43, 1.6));

        Activity a = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(
                401,
                new DateTime(2014, 01, 06, 0, 0),
                new DateTime(2013, 11, 21, 0, 0),
                1);
        LogService.getService().logApplicationInfo("=> " + a);
        assertNotNull(a);
        assertEquals("CIV", inspector.getEEZ(OTUtils.convertLongitude(4, 400),
                OTUtils.convertLatitude(4, 515)));
        assertFalse(PositionInEEZInspector.activityPositionAndEEZInconsistent(a));

        a = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(
                427,
                new DateTime(2014, 02, 15, 0, 0),
                new DateTime(2014, 02, 11, 0, 0),
                1);
        assertNotNull(a);
        assertEquals("GMB", inspector.getEEZ(OTUtils.convertLongitude(4, 1949),
                OTUtils.convertLatitude(4, 1318)));
        assertFalse(PositionInEEZInspector.activityPositionAndEEZInconsistent(a));

        a = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(
                401,
                new DateTime(2014, 01, 06, 0, 0),
                new DateTime(2013, 11, 22, 0, 0),
                1);
        assertNotNull(a);
        assertTrue(PositionInEEZInspector.activityPositionAndEEZInconsistent(a));
    }

    public void test1InLand() throws AvdthDriverException {
        PositionInspector inspector = new PositionInspector();
        assertEquals("Ghana", inspector.inLand(-1.5, 8.5));

//        assertEquals("Côte d'Ivoire", inspector.inLand(-4.0, 5.25));
        assertTrue(inspector.onCoastLine(-4.0, 5.25));
        assertFalse(inspector.onCoastLine(-4.0, 6.25));
    }

    public void test2InIndianOcean() throws AvdthDriverException {
        PositionInspector inspector = new PositionInspector();
        assertTrue(inspector.inIndianOcean(65, 12));
        assertFalse(inspector.inIndianOcean(-1.5, 8.5));

    }

    public void test3InAtlanticOcean() throws AvdthDriverException {
        PositionInspector inspector = new PositionInspector();
        assertTrue(inspector.inAtlanticOcean(4.5, 2.4));
        assertTrue(inspector.inAtlanticOcean(-4.0, 5.25));
        assertFalse(inspector.inAtlanticOcean(-1.5, 8.5));
        assertTrue(inspector.inAtlanticOcean(-79.71, 9.65));

    }

    public void test4InHarbour() throws AvdthDriverException {
        PositionInspector inspector = new PositionInspector();
        //In the buffer of Abidjan
        assertTrue(PositionInspector.inHarbour(-4.013, 5.287));
        //Not In the buffer of Abidjan
        assertFalse(PositionInspector.inHarbour(-4.0, 6.25));
        //In the buffer of Tema
        assertTrue(PositionInspector.inHarbour(-0.02, 5.63));
        //Not In the buffer of Tema
//        assertFalse(inspector.inHarbour(-4.0, 6.25));
    }

    public void testFishingContext() throws AvdthDriverException {
        Activity activity = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(401, new DateTime(2014, 01, 06, 0, 0),
                new DateTime(2013, 11, 27, 0, 0), 3);
        assertNotNull(activity);
        FishingContextInspector inspector = new FishingContextInspector();

        Results results = new Results();
        inspector.set(activity);
        results.addAll(inspector.execute());
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(0, results.size());

        activity = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(401, new DateTime(2014, 01, 06, 0, 0),
                new DateTime(2013, 12, 04, 0, 0), 1);
        assertNotNull(activity);
        inspector = new FishingContextInspector();

        results = new Results();
        inspector.set(activity);
        results.addAll(inspector.execute());
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(1, results.size());

        activity = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(401, new DateTime(2014, 01, 06, 0, 0),
                new DateTime(2013, 12, 06, 0, 0), 2);
        assertNotNull(activity);
        inspector = new FishingContextInspector();

        results = new Results();
        inspector.set(activity);
        results.addAll(inspector.execute());
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(1, results.size());

    }

    public void testAllFishingContext() throws AvdthDriverException {
        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        assertNotNull(activities);
        assertEquals(916, activities.size());

        FishingContextInspector inspector = new FishingContextInspector();

        Results results = new Results();
        for (Activity activity : activities) {
            inspector.set(activity);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }

        assertEquals(6, results.size());

        //Verifie la repartition des erreurs
        int fishingContextEmptyCount = 0;
        int fishingContextCount = 0;
        int fishingContextFreeSchoolCount = 0;
        int fishingContextArtificialSchoolCount = 0;
        for (Activity activity : activities) {

            if (FishingContextInspector.artificialSchoolAndEmpty(activity.getSchoolType(), activity.getFishingContexts())) {
                fishingContextEmptyCount++;
            } else {
                for (FishingContext aa : activity.getFishingContexts()) {
                    fishingContextCount++;
                    if (activity.getSchoolType().getCode() == SchoolType.ARTIFICIAL_SCHOOL && !FishingContextInspector.fishingContextIsConsistentWithArtificialSchool(aa)) {
                        fishingContextArtificialSchoolCount++;
                    } else if (activity.getSchoolType().getCode() == SchoolType.FREE_SCHOOL && FishingContextInspector.fishingContextIsConsistentWithArtificialSchool(aa)) {
                        fishingContextFreeSchoolCount++;
                    }
                }
            }
        }
        assertEquals(213, fishingContextCount);

        assertEquals(1, fishingContextEmptyCount);

        assertEquals(3, fishingContextArtificialSchoolCount);
        assertEquals(3, fishingContextFreeSchoolCount);

    }

    public void testWeight() throws AvdthDriverException {
        Activity activity = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(401, new DateTime(2014, 01, 06, 0, 0),
                new DateTime(2013, 11, 27, 0, 0), 3);
        assertNotNull(activity);

        WeightInspector inspector = new WeightInspector();
        inspector.set(activity);
        Results results = inspector.execute();
        assertNotNull(results);

        assertEquals(1, results.size());

        results = new Results();
        for (Activity activite : (new ActivityDAO()).findAllActivities()) {
            inspector = new WeightInspector();
            inspector.set(activite);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(3, results.size());
    }

    public void testOperation() throws AvdthDriverException {
        Vessel vessel = (new VesselDAO()).findVesselByCode(401);
        List<Activity> activities = (new ActivityDAO()).findActivities(vessel, null, null);
        assertNotNull(activities);
        assertEquals(154, activities.size());
        OperationtInspector inspector = new OperationtInspector();

        Results results = new Results();
        for (Activity activite : activities) {
            inspector.set(activite);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        assertEquals(1, results.size());

    }

    public void testOperationRechercheEtCapturePositive() throws AvdthDriverException {

        Activity activity = (new ActivityDAO()).
                findActivityByTripAndDateOfLandingAndNum(
                        401,
                        new DateTime(2014, 01, 06, 0, 0),
                        new DateTime(2013, 11, 21, 0, 0),
                        1);
        assertNotNull(activity);
        assertFalse(OperationtInspector.operationNumberConsistent(activity));
        assertTrue(OperationtInspector.activityAndOperationConsistent(activity));

    }

    public void testOperationAllActivities() throws AvdthDriverException {
        List<Activity> activities = (new ActivityDAO()).findAllActivities();

        assertNotNull(activities);
        assertEquals(916, activities.size());
        OperationtInspector inspector = new OperationtInspector();

        Results results = new Results();
        for (Activity activite : activities) {
            inspector.set(activite);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        assertEquals(1, results.size());
    }

    public void testPosition() throws AvdthDriverException {
        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        assertNotNull(activities);
        assertEquals(916, activities.size());
        PositionInspector inspector = new PositionInspector();

        Results results = new Results();

        Activity activity = (new ActivityDAO()).findActivityByTripAndDateOfLandingAndNum(401, new DateTime(2014, 01, 06, 0, 0),
                new DateTime(2013, 11, 22, 0, 0), 1);
        assertNotNull(activity);
        inspector.set(activity);
        results.addAll(inspector.execute());
        assertNotNull(results);

        assertEquals(1, results.size());

        results = new Results();
        for (Activity activite : activities.subList(10, 100)) {
            inspector.set(activite);

            Results tmp = inspector.execute();

            results.addAll(tmp);
        }
        assertNotNull(results);

        assertEquals(0, results.size());
    }

    public void testQuadrant() throws AvdthDriverException {
        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        assertNotNull(activities);
        assertEquals(916, activities.size());
        QuadrantInspector inspector = new QuadrantInspector();

        Results results = new Results();
        for (Activity activite : activities) {
            inspector.set(activite);
            Results tmp = inspector.execute();

            results.addAll(tmp);
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(4, results.size());
    }

    public void testFPAZone() throws AvdthDriverException {
        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        assertNotNull(activities);
        assertEquals(916, activities.size());
        EEZInspector inspector = new EEZInspector();

        Results results = new Results();
        for (Activity activite : activities) {
            inspector.set(activite);
            Results tmp = inspector.execute();

            results.addAll(tmp);
        }
        assertNotNull(results);
        assertEquals(326, results.size());
    }

    public void testExportWithJXLS() throws AvdthDriverException, Exception {
        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        assertNotNull(activities);
        List<Inspector<Activity>> inspectors = new ArrayList<>();
        inspectors.add(new QuadrantInspector());
        inspectors.add(new OperationtInspector());
        inspectors.add(new FishingContextInspector());

        Results results = new Results();
        for (Inspector<Activity> inspector : inspectors) {
            for (Activity activite : activities) {
                inspector.set(activite);
                results.addAll(inspector.execute());
            }
        }
        assertNotNull(results);

        results.exportToXLS(AAProperties.STANDARD_DIRECTORY);

    }

    public void testWeightingSample() throws AvdthDriverException {
        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        WeightingSampleInspector inspector = new WeightingSampleInspector();

        Results results = new Results();
        for (Activity activite : activities) {
            inspector.set(activite);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (Result result : results) {
            System.out.println(result.getMessage().getContent());
        }
        assertEquals(9, results.size());
        results.exportToXLS(AAProperties.STANDARD_DIRECTORY);

    }
}
