/*
 * Copyright (C) 2015 Observatoire thonier, IRD
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

import static fr.ird.common.Utils.round;
import fr.ird.akado.avdth.anapo.activity.AnapoActivityConsistentInspector;
import fr.ird.akado.avdth.anapo.vms.AnapoInspector;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.GISHandler;
import fr.ird.akado.avdth.result.Result;
import fr.ird.akado.core.utils.WGS84;
import fr.ird.common.OTUtils;
import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.anapo.service.ANAPOService;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Ocean;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.ActivityDAO;
import fr.ird.driver.avdth.service.AvdthService;

import java.nio.file.Path;
import java.util.List;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertNotNull;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static fr.ird.common.OTUtils.convertLongitude;

/**
 * This test class checks the Anapo functionalities.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 20 juillet 2015
 *
 */
public class AnapoTest extends TestCase {

    public final static boolean DELETE = true;

    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";
    private static final String AVDTH_DB_TEST = "/France_OA_2015_01_03.mdb";
    private static final String ANAPO_DB_TEST = "/BD_Vms_ANAPO_2015_T1_etendue.mdb";
    private static final String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    private final String avdthDatabasePath;
    private final String anapoDatabasePath;

    public AnapoTest() {
        avdthDatabasePath = JDBC_ACCES_PROTOCOL + AnapoTest.class.getResource(AVDTH_DB_TEST).getFile();
        anapoDatabasePath = JDBC_ACCES_PROTOCOL + AnapoTest.class.getResource(ANAPO_DB_TEST).getFile();
    }

    @BeforeClass
    public void before() {

    }

    @AfterClass
    public void after() {

    }

    @Override
    public void setUp() throws Exception {
        AAProperties.ANAPO_INSPECTOR = AAProperties.ACTIVE_VALUE;

        //        AkadoAvdthProperties.getService().init();
        AAProperties.DATE_FORMAT_XLS = "dd/mm/yyyy hh:mm";
        AAProperties.STANDARD_DIRECTORY = Path.of(System.getProperty("java.io.tmpdir")).resolve(""+System.currentTimeMillis()).toString();//ActivityTest.class.getResource("/db").getFile();

        AAProperties.SHP_COUNTRIES_PATH = ActivityTest.class.getResource("/shp/countries.shp").getFile();
//        System.out.println(AAProperties.SHP_COUNTRIES_PATH);
        AAProperties.SHP_OCEAN_PATH = ActivityTest.class.getResource("/shp/IHOSeasAndOceans.shp").getFile();
        AAProperties.SHP_HARBOUR_PATH = ActivityTest.class.getResource("/shp/harbour.shp").getFile();
//        System.out.println(AAProperties.SHP_OCEAN_PATH);
        AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;

        if (!GISHandler.getService().exists()) {
            System.out.println("Create the database!");
                GISHandler.getService().init(AAProperties.STANDARD_DIRECTORY ,
                        AAProperties.SHP_COUNTRIES_PATH,
                        AAProperties.SHP_OCEAN_PATH,
                        AAProperties.SHP_HARBOUR_PATH,
                        AAProperties.SHP_EEZ_PATH);
            GISHandler.getService().create();
        }
        AvdthService.getService()
                .init(avdthDatabasePath, JDBC_ACCESS_DRIVER, "", "");
        ANAPOService.getService()
                .init(anapoDatabasePath, JDBC_ACCESS_DRIVER, "", "");

    }

    @Override
    public void tearDown() throws AvdthDriverException {
        AvdthService.getService().close();
    }

    public void test1() throws AvdthDriverException {
        AnapoInspector inspector = new AnapoInspector();

        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        assertNotNull(activities);

        Results results = new Results();
        for (Activity activity : activities) {
            inspector.set(activity);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (Result r : results) {
//            System.out.println(r.getMessage());
        }
    }

    public void testAnapoActivityConsistentInspector() throws AvdthDriverException {
        AnapoActivityConsistentInspector inspector = new AnapoActivityConsistentInspector();

        List<Activity> activities = (new ActivityDAO()).findAllActivities();
        assertNotNull(activities);

        Results results = new Results();
//        for (Activity activity : activities) {
        inspector.set(activities);
        results.addAll(inspector.execute());
//        }
        assertNotNull(results);
        //assertEquals(1892, results.size());
//        for (Result r : results) {
//            System.out.println(r.getMessage());
//        }
    }

    public void testDistance() {
        PosVMS pvms = new PosVMS(427, new DateTime(), Ocean.ATLANTIQUE, 1441, -1715);
        Activity a = new Activity();
        a.setLatitude(1441);
        a.setLongitude(1714);
        a.setQuadrant(4);

        assertEquals(0.965, round(AnapoInspector.calculateDistanceProximity(pvms, a), 3));
    }

    public void testD() {
        assertEquals(1441, OTUtils.degreesDecimalToDegreesMinutes(14.68));
        assertEquals(1716, OTUtils.degreesDecimalToDegreesMinutes(17.26));

        assertEquals(1441, OTUtils.degreesDecimalToDegreesMinutes(14.68));
        assertEquals(1714, OTUtils.degreesDecimalToDegreesMinutes(17.24));

        double lat1 = OTUtils.convertLatitude(OTUtils.signLatitude(4, 1441));
        double lon1 = convertLongitude(OTUtils.signLongitude(4, 1716));
        double lat2 = OTUtils.convertLatitude(1441);
        double lon2 = convertLongitude(-1714);
        System.out.println(lat1 + " " + lon1);
        System.out.println(lat2 + " " + lon2);
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        System.out.println(lat1 + " " + lon1);
        System.out.println(lat2 + " " + lon2);
        double res = AnapoInspector.CONVERT_DIST_MILES * WGS84.distanceVolOiseauEntre2PointsAvecPrecision(lat1, lon1, lat2, lon2);
        assertEquals(1.93, round(res, 2));
    }
}
