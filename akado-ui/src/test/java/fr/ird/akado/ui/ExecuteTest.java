/*
 * Copyright (C) 2015 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.ui;

import fr.ird.akado.avdth.AvdthInspector;
import fr.ird.akado.core.AkadoCore;
import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.common.AkadoMessage;
import fr.ird.akado.core.common.MessageAdapter;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.dao.HarbourDAO;
import fr.ird.driver.avdth.service.AvdthService;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import io.ultreia.java4all.util.sql.conf.JdbcConfigurationBuilder;
import junit.framework.TestCase;
import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class ExecuteTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        AkadoAvdthProperties.getService().init();
    }

    @Override
    protected void tearDown() throws Exception {

    }

    public void testNop() {

    }

    public void doubleExecution() {

        Trip trip = null;
        try {

            task("C:\\Users\\jlebranc\\Documents\\Julien_OA_2015_01_07-corrigee.mdb");

            AvdthService.getService().open();
            Vessel b = AvdthService.getService().getVesselDAO().findVesselByCode(803);
            trip = new Trip(b, new DateTime());
            trip.setLandingHarbour(new HarbourDAO().findHarbourByCode(2));
            trip.setDepartureHarbour(new HarbourDAO().findHarbourByCode(2));
            System.out.println("Add trip...");
            AvdthService.getService().save(trip);
            AvdthService.getService().close();
//            com.hxtt.sql.access.AccessDriver.releaseAll();

            System.out.println("Sleep...");
            Thread.sleep(60000);

//            Enumeration<Driver> drivers = DriverManager.getDrivers();
//
//            while (drivers.hasMoreElements()) {
//                Driver d = drivers.nextElement();
//
//                try {
//                    if (d.getClass().getName().equals(AkadoAvdthProperties.JDBC_ACCESS_DRIVER)) {
//                        System.out.println("Deregister Access Driver");
//                        DriverManager.deregisterDriver(d);
//                   
//                        System.out.println("Register Access Driver");
//                        DriverManager.registerDriver(d);
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
            System.out.println("Run a new task");
            task("C:\\Users\\jlebranc\\Documents\\Julien_OA_2015_01_07-corrigee.mdb");

        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ExecuteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ExecuteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ExecuteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ExecuteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ExecuteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExecuteTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (trip != null) {
                AvdthService.getService().open();
                AvdthService.getService().delete(trip);
            }
        }

    }
    DataBaseInspector inspector;

    private void task(String path) throws Exception {

        JdbcConfiguration configuration = new JdbcConfigurationBuilder().forDatabase(AkadoAvdthProperties.PROTOCOL_JDBC_ACCESS + path, "", "");
        AkadoCore akado = new AkadoCore();
        inspector = new AvdthInspector(configuration);
        if (!akado.addDataBaseValidator(inspector)) {
            throw new Exception("Error during the AVDTHValidator creation");
        }

        inspector.getAkadoMessages().addMessageListener(new MessageAdapter() {
            @Override
            public void messageAdded(AkadoMessage m) {
                System.out.println(m.getContent());
            }
        });
        inspector.info();
        akado.execute();
    }

}
