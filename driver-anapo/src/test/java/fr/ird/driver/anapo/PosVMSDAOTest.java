/**
 * Copyright (C) 2015 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.anapo;

import fr.ird.common.DateTimeUtils;
import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.anapo.service.ANAPOService;
import fr.ird.driver.anapo.common.exception.ANAPODriverException;
import fr.ird.driver.anapo.dao.PosVMSDAO;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 22 mai 2015
 *
 */
public class PosVMSDAOTest extends TestCase {

    public final static boolean DELETE = true;

    private static String ANAPO_DB_TEST = "/BD_Vms_ANAPO_2015_T1_test.mdb";
    private final String anapoDatabasePath;
    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";

    public PosVMSDAOTest() {
        anapoDatabasePath = JDBC_ACCES_PROTOCOL + PosVMSDAOTest.class.getResource(ANAPO_DB_TEST).getFile();
    }

    @BeforeClass
    public void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public void tearDownAfterClass() throws Exception {

    }

    @Override
    public void setUp() throws ANAPODriverException {

        ANAPOService.getService().init(anapoDatabasePath, "com.hxtt.sql.access.AccessDriver", "", "");
//        ANAPOService.getService().init(ANAPO_DB_TEST);
    }

    @Override
    public void tearDown() {
        ANAPOService.getService().close();
    }

    public void testActivityDAOFindActivitiesFRA() {
        List<PosVMS> positions;

        PosVMSDAO dao = new PosVMSDAO();

        DateTime date = new DateTime(2015, 1, 8, 0, 0);
        System.out.println(DateTimeUtils.convertFilteredDate(date) + " --- " + DateTimeUtils.convertFilteredDate(date.plusDays(1)));

        positions = dao.findAllPositions(491, date);

        for (PosVMS pos : positions) {
            System.out.println(pos);
        }

        assertEquals(24, positions.size());
    }
}
