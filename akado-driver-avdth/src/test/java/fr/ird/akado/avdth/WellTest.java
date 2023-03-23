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

import fr.ird.akado.avdth.common.AAProperties;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.well.ActivityConsistent;
import fr.ird.akado.avdth.well.WellWithoutTripInspector;
import fr.ird.akado.avdth.well.WellWithoutWellPlanInspector;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.business.Well;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.dao.WellDAO;
import fr.ird.driver.avdth.service.AvdthService;
import java.util.List;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * This test class checks all wells inspectors.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 19 juin 2014
 */
public class WellTest extends TestCase {

    public final static boolean DELETE = true;
    private static final String AVDTH_DB_TEST = "/akado_avdth_test_350.mdb";
    private static final String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";
    private final String avdthDatabasePath;

    public WellTest() throws AvdthDriverException {
        avdthDatabasePath = JDBC_ACCES_PROTOCOL + ActivityTest.class.getResource(AVDTH_DB_TEST).getFile();
    }

    @Override
    public void setUp() throws Exception {
        AvdthService.getService().init(avdthDatabasePath, JDBC_ACCESS_DRIVER, "", "");
//        AkadoProperties.getService().init();
        AAProperties.DATE_FORMAT_XLS = "dd/mm/yyyy hh:mm";
        AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;
    }

    @Override
    public void tearDown() throws AvdthDriverException {
        AvdthService.getService().close();
    }

    public void testWellWithoutTrip() throws AvdthDriverException {
        Vessel vessel = (new VesselDAO()).findVesselByCode(703);
        List<Well> wells = (new WellDAO()).findWell(vessel, null, null);
        assertNotNull(wells);

        assertEquals(18, wells.size());
        WellWithoutTripInspector inspector = new WellWithoutTripInspector();
        Results results = new Results();
        for (Well well : wells) {
            inspector.set(well);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(2, results.size());

    }

    public void testWellWithoutWellPlan() throws AvdthDriverException {
        Vessel vessel = (new VesselDAO()).findVesselByCode(703);
        List<Well> wells = (new WellDAO()).findWell(vessel, null, null);
        assertNotNull(wells);

        assertEquals(18, wells.size());
        WellWithoutWellPlanInspector inspector = new WellWithoutWellPlanInspector();
        Results results = new Results();
        for (Well well : wells) {
            inspector.set(well);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(1, results.size());
    }

    public void testWellWithoutWellPlanAllWells() throws AvdthDriverException {
        List<Well> wells = (new WellDAO()).findAllWells();
        assertNotNull(wells);

        WellWithoutWellPlanInspector inspector = new WellWithoutWellPlanInspector();
        Results results = new Results();
        for (Well well : wells) {
            inspector.set(well);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(15, results.size());
    }

    public void testActivityConsistent() throws AvdthDriverException {
        List<Well> wells = (new WellDAO()).findAllWells();
        assertNotNull(wells);

        assertEquals(141, wells.size());
        ActivityConsistent inspector = new ActivityConsistent();
        Results results = new Results();
        for (Well well : wells) {
            inspector.set(well);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(14, results.size());
    }

}
