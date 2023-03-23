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

import fr.ird.common.JDBCUtilities;
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.Harbour;
import fr.ird.driver.avdth.business.Operation;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SchoolType;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.business.VesselSizeCategory;
import fr.ird.driver.avdth.business.VesselType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.ActivityDAO;
import fr.ird.driver.avdth.dao.VesselSizeCategoryDAO;
import fr.ird.driver.avdth.dao.CountryDAO;
import fr.ird.driver.avdth.dao.HarbourDAO;
import fr.ird.driver.avdth.dao.OperationDAO;
import fr.ird.driver.avdth.dao.SampleDAO;
import fr.ird.driver.avdth.dao.SampleQualityDAO;
import fr.ird.driver.avdth.dao.SampleTypeDAO;
import fr.ird.driver.avdth.dao.SchoolTypeDAO;
import fr.ird.driver.avdth.dao.TripDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.dao.VesselTypeDAO;
import fr.ird.driver.avdth.service.AvdthService;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Cette classe permet de tester en écriture et lecture la couche AVDTH du
 * module.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 */
public class InsertTest extends TestCase {
    
    public final static boolean DELETE = true;
    public static String AVDTH_DB_TEST = "/avdth_350_test.mdb";
    private final String avdthDatabasePath;
    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";
    
    public InsertTest() {
        avdthDatabasePath = JDBC_ACCES_PROTOCOL + InsertTest.class.getResource(AVDTH_DB_TEST).getFile();
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
    
    public void testSample() throws AvdthDriverException {
        
        Sample sample = new Sample();
        sample.setVessel(new VesselDAO().findVesselByCode(401));
        sample.setLandingHarbour(new HarbourDAO().findHarbourByCode(2));
        sample.setLandingDate(new DateTime(2013, 01, 31, 0, 0));
        sample.setSampleNumber(10);
        sample.setSampleQuality((new SampleQualityDAO().findSampleQualityByCode(1)));
        sample.setSampleType((new SampleTypeDAO().findSampleTypeByCode(1)));
        sample.setGlobalWeight(52);
//        sample.setLandingHarbour((new HarbourDAO().findHarbourByCode(2)));
        sample.setMinus10Weight(5);
        
        SampleDAO sdao = new SampleDAO();
        try {
            
            sdao.insert(sample);
            
            Sample sampleTest = sdao.findSampleByVessefIdAndDate(401, new DateTime(2013, 01, 31, 0, 0), 10);
            
            assertEquals(sample.getVessel(), sampleTest.getVessel());
            assertEquals(sample.getLandingDate(), sampleTest.getLandingDate());
            assertEquals(sample.getLandingHarbour(), sampleTest.getLandingHarbour());
            assertEquals(sample.getSampleNumber(), sampleTest.getSampleNumber());
            assertEquals(sample.getSampleQuality(), sampleTest.getSampleQuality());
            assertEquals(sample.getSampleType(), sampleTest.getSampleType());
            System.out.println("---*> " + sample);
            System.out.println("---*> " + sampleTest);
            assertEquals(sample, sampleTest);
            AvdthService.getService().getConnection().rollback();
            
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
            assertFalse(true);
        } finally {
            assertTrue(sdao.delete(sample));
        }
        
    }
    
    public void testPort() throws AvdthDriverException {
        
        Harbour port = new Harbour();
        port.setName("SpatioPort de Mars");
        port.setLatitude(25);
        port.setLongitude(-33);
        port.setLocode("251238111949786");
        port.setComment("Ceci est un port virtuel de test.");
        port.setCountry((new CountryDAO()).findCountryByCode(2));
        
        HarbourDAO pdao = new HarbourDAO();
        try {
            
            pdao.insert(port);
            
            Harbour portTestRes = pdao.findHarbourByLocode("251238111949786");
            assertEquals(port.getComment(),
                    portTestRes.getComment());
            assertEquals(port.getName(),
                    portTestRes.getName());
            
            AvdthService.getService().getConnection().rollback();
            
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
            assertFalse(true);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            assertTrue(pdao.delete(port));
        }
        
    }
    
    public void testMaree() throws AvdthDriverException {
        
        DateTime dateDeLaMaree = new DateTime(2014, 8, 1, 0, 0);
        Vessel bateauDeLaMaree = (new VesselDAO()).findVesselByCode(225);
        
        Trip maree = new Trip(bateauDeLaMaree, dateDeLaMaree);
        maree.setLandingHarbour((new HarbourDAO()).findHarbourByCode(43));
        maree.setTimeAtSea(88);
        maree.setFishingTime(88);
        
        maree.setLocalMarketWeight(0);
        maree.setTotalLandingWeight(900);
        
        maree.setDepartureHarbour((new HarbourDAO()).findHarbourByCode(42));
        
        maree.setFlagCaleVide(0);
        maree.setNumeroMaree("20130504");
        
        TripDAO mdao = new TripDAO();
        
        try {
            
            assertTrue(AvdthService.getService().save(maree));
            
            System.out.println("Maree... findTripBy...");
            Trip mareeTestRes = mdao.findTripByVesselIdAndDate(225,
                    new DateTime(2014, 8, 1, 0, 0));
            assertNotNull(mareeTestRes);
            System.out.println("Egalité Marée : " + maree.equals(mareeTestRes));
            System.out.println("Maree : " + maree + " == " + maree.getLandingDate());
            System.out.println("MareeT: " + mareeTestRes + " == " + mareeTestRes.getLandingDate());
            System.out.println("Egalité bateau : " + bateauDeLaMaree.equals(mareeTestRes.getVessel()));
            System.out.println("Bateau : \n" + maree.getVessel());
            System.out.println("Bateau Test : \n" + mareeTestRes.getVessel());
            //            System.out.println("Egalité date d'arrivée : " + maree.getId().getDateArrivee().equals(mareeTestRes.getId().getDateArrivee()));
            assertEquals("Les bateaux ne sont pas identiques.", maree.getVessel(), mareeTestRes.getVessel());
            assertEquals("Les dates de débarquements ne sont pas identiques.", maree.getLandingDate(), mareeTestRes.getLandingDate());
            
            AvdthService.getService().getConnection().rollback();
            
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
        } finally {
            if (DELETE) {
                if (maree != null) {
                    assertTrue(AvdthService.getService().delete(maree));
                }
            }
            
        }
    }
    
    public void testBateau() throws AvdthDriverException {
        Vessel bateau = new Vessel();
        bateau.setYearService(2013);
        bateau.setCapacity(50.21f);
        bateau.setFleetCode(9997);
        bateau.setKeelCode(9989);
        bateau.setComment("nul");
        bateau.setValidityStart(new DateTime());
        bateau.setStatut(true);
        
        bateau.setLibelle("Enterprise");
        bateau.setLength(254845);
        bateau.setCommunautaryID("ITA000545540");
        
        bateau.setNationalID("XX545540");
        CountryDAO paysDAO = new CountryDAO();
        
        Country pays = paysDAO.findCountryByCode(2);
        bateau.setCountry(pays);
        bateau.setPower(526);
        
        bateau.setVitesseMaxDeProspection(2561.85f);
        
        VesselDAO bdao = new VesselDAO();
        
        VesselTypeDAO tbdao = new VesselTypeDAO();
        VesselType typeBateau;
        typeBateau = tbdao.getAllVesselType().get(0);
        bateau.setVesselType(typeBateau);
        
        VesselSizeCategoryDAO cdao = new VesselSizeCategoryDAO();
        VesselSizeCategory categorieBateau;
        List<VesselSizeCategory> listeCB = cdao.getAllVesselSizeCategory();
        System.out.println("Taille liste CB " + listeCB.size());
        categorieBateau = listeCB.get(0);
        
        bateau.setVesselSizeCategory(categorieBateau);
        
        try {
            bdao.insert(bateau);
            
            Vessel bateauTestRes = bdao.findVesselByCfrIdentifiant("ITA000545540");
            assertEquals(bateau.getLibelle(),
                    bateauTestRes.getLibelle());
            assertEquals(bateau.getKeelCode(),
                    bateauTestRes.getKeelCode());
            assertEquals(categorieBateau, bateauTestRes.getVesselSizeCategory());
            
            AvdthService.getService().getConnection().rollback();
            
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
            assertFalse(true);
        } finally {
            assertTrue(bdao.delete(bateau));
        }
    }
    
    public void testActivity() throws AvdthDriverException {
        int vesselCode = 218;
        
        DateTime dateDeLaMaree = new DateTime(2014, 8, 21, 0, 0);
        Vessel bateauDeLaMaree = (new VesselDAO()).findVesselByCode(vesselCode);
        String ersTN = bateauDeLaMaree.getCommunautaryID() + "-20130930";
        
        Trip maree = new Trip(bateauDeLaMaree, dateDeLaMaree);
        maree.setLandingHarbour((new HarbourDAO()).findHarbourByCode(43));
        maree.setTimeAtSea(285);
        maree.setFishingTime(188);
        
        maree.setLocalMarketWeight(0);
        maree.setTotalLandingWeight(1900);
        
        maree.setDepartureHarbour((new HarbourDAO()).findHarbourByCode(42));
        
        maree.setFlagCaleVide(0);
        maree.setNumeroMaree(ersTN);
        
        TripDAO mdao = new TripDAO();
        
        Activity activite = new Activity();
        activite.setLandingDate(maree.getLandingDate());
        activite.setVessel(maree.getVessel());
        DateTime dateActivite = new DateTime(2013, 9, 15, 13, 0);
        activite.setDate(dateActivite);
        activite.setNumber(1);
        
        activite.setCodeOcean(1);
        activite.setLatitude(25);
        activite.setLongitude(35);
        
        activite.setCatchWeight(256.2);
        
        OperationDAO operationDAO = new OperationDAO();
        Operation operation = operationDAO.findOperationByCode(1);
        activite.setOperation(operation);
        
        SchoolTypeDAO typeBancDAO = new SchoolTypeDAO();
        SchoolType typeBanc = typeBancDAO.findSchoolTypeByCode(3);
        activite.setSchoolType(typeBanc);
        ActivityDAO adao = new ActivityDAO();
        
        maree.getActivites().add(activite);
        
        try {
            
            assertTrue(AvdthService.getService().update(maree));
//            for (Activity a : adao.findActivities(bateauDeLaMaree, null, null)) {
//                System.out.println(a);
//            }
            assertNotNull(mdao.findTripByTripNumber(ersTN));
            
            Activity activiteRes = adao.findActivityByTripAndDateOfLandingAndNum(vesselCode, dateDeLaMaree, dateActivite, 1);
            System.out.println("--> " + dateActivite + " -- " + dateDeLaMaree);
            System.out.println("ActiviteInsert " + activite);
            System.out.println("ActiviteResult " + activiteRes);
            
            assertNotNull(activiteRes);
            assertEquals(25, activiteRes.getLatitude());
            assertEquals(35, activiteRes.getLongitude());
            assertEquals(256.2, activiteRes.getCatchWeight());
            
            assertEquals(operation, activiteRes.getOperation());
            assertEquals(typeBanc, activiteRes.getSchoolType());
            
            AvdthService.getService().getConnection().rollback();
            
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
            assertFalse(true);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            assertTrue(AvdthService.getService().delete(maree));
        }
        
    }
}
