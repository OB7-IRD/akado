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

import fr.ird.akado.avdth.result.Result;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.sample.DistributionInspector;
import fr.ird.akado.avdth.sample.well.ActivityConsistentInspector;
import fr.ird.akado.avdth.sample.LDLFInspector;
import fr.ird.akado.avdth.sample.LengthClassInspector;
import fr.ird.akado.avdth.sample.LittleBigInspector;
import fr.ird.akado.avdth.sample.MeasureInspector;
import fr.ird.akado.avdth.sample.well.PositionActivityConsistentInspector;
import fr.ird.akado.avdth.sample.SampleWithoutMeasureInspector;
import fr.ird.akado.avdth.sample.SampleWithoutSpeciesInspector;
import fr.ird.akado.avdth.sample.SampleWithoutTripInspector;
import fr.ird.akado.avdth.sample.SpeciesInspector;
import fr.ird.akado.avdth.sample.SuperSampleNumberConsistentInspector;
import fr.ird.akado.avdth.sample.WeightSampleInspector;
import fr.ird.akado.avdth.sample.WeightingInspector;
import static fr.ird.akado.avdth.sample.WeightingInspector.weightedWeight;
import fr.ird.akado.avdth.sample.WellNumberConsistentInspector;
import fr.ird.akado.core.common.GISHandler;
import fr.ird.akado.avdth.sample.HarbourInspector;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.SampleDAO;
import fr.ird.driver.avdth.service.AvdthService;

import java.nio.file.Path;
import java.util.List;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import org.joda.time.DateTime;

/**
 * This test class checks all samples inspectors.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 6 juin 2014
 */
public class SampleTest extends TestCase {

    public final static boolean DELETE = true;
    private static final String AVDTH_DB_TEST = "/akado_avdth_test_350.mdb";
    private static final String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    private static final String JDBC_ACCES_PROTOCOL = "jdbc:Access:///";
    private final String avdthDatabasePath;

    public SampleTest() throws AvdthDriverException {
        avdthDatabasePath = JDBC_ACCES_PROTOCOL + ActivityTest.class.getResource(AVDTH_DB_TEST).getFile();
    }

    @Override
    public void setUp() throws Exception {
        AvdthService.getService().init(avdthDatabasePath, JDBC_ACCESS_DRIVER, "", "");
//        AkadoProperties.getService().init();
        AAProperties.DATE_FORMAT_XLS = "dd/mm/yyyy hh:mm";
        AAProperties.STANDARD_DIRECTORY = Path.of(System.getProperty("java.io.tmpdir")).resolve(""+System.currentTimeMillis()).toString();//ActivityTest.class.getResource("/db").getFile();

        AAProperties.SHP_COUNTRIES_PATH = ActivityTest.class.getResource("/shp/countries.shp").getFile();
        AAProperties.SHP_OCEAN_PATH = ActivityTest.class.getResource("/shp/IHOSeasAndOceans.shp").getFile();
        AAProperties.SHP_HARBOUR_PATH = ActivityTest.class.getResource("/shp/harbour.shp").getFile();
        AAProperties.SHP_EEZ_PATH = ActivityTest.class.getResource("/shp/eez.shp").getFile();
        AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;

        if (!GISHandler.getService().exists()) {

            GISHandler.getService().init(AAProperties.STANDARD_DIRECTORY,
                    AAProperties.SHP_COUNTRIES_PATH,
                    AAProperties.SHP_OCEAN_PATH,
                    AAProperties.SHP_HARBOUR_PATH,
                    AAProperties.SHP_EEZ_PATH);
            GISHandler.getService().create();
        }

    }

    @Override
    public void tearDown() throws AvdthDriverException {
        AvdthService.getService().close();
    }

    public void testDistribution() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        DistributionInspector inspector = new DistributionInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (Result r : results) {
            System.out.println(r.getMessage());
        }
        results.exportToXLS(AAProperties.STANDARD_DIRECTORY);
        assertEquals(12, results.size());
    }

    public void testLDFL() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        LDLFInspector inspector = new LDLFInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (Result r : results) {
            System.out.println(r.getMessage());
        }
        results.exportToXLS(AAProperties.STANDARD_DIRECTORY);
        assertEquals(13, results.size());
    }

    public void testWeighting() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        WeightingInspector inspector = new WeightingInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        int w1count = 0;
        int w2count = 0;
        int w3count = 0;

        for (Sample s : samples) {
            double weight = s.getGlobalWeight();
            if (weight == 0) {
                weight = s.getMinus10Weight() + s.getPlus10Weight();
            }
            double weightedWeight = weightedWeight(s);
            if (weight > 100) {
                w1count++;
            }
//            if (weightedWeight >= weight) {
//
//                w2count++;
//            }
            if (weightedWeight < weight && !((weightedWeight / weight) >= 0.95)) {
                w3count++;
            }
        }
        assertEquals(0, w1count);
//        assertEquals(46, w2count);
        assertEquals(0, w3count);
        assertEquals(0, results.size());

    }

    public void testLittleBig() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        LittleBigInspector inspector = new LittleBigInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (Result r : results) {
            System.out.println(r.getMessage());
        }
        results.exportToXLS(AAProperties.STANDARD_DIRECTORY);
        assertEquals(11, results.size());

    }

    public void testMeasure() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        MeasureInspector inspector = new MeasureInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

//        for (Result r : results) {
//            System.out.println(r.getMessage());
//        }
        assertEquals(1, results.size());
    }

    public void testSpecies() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        SpeciesInspector inspector = new SpeciesInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (Result r : results) {
            System.out.println(r.getMessage());
        }
//       results.exportToXLS(AAProperties.STANDARD_DIRECTORY);
        assertEquals(3, results.size());
    }

    public void testWeightSample() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        WeightSampleInspector inspector = new WeightSampleInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        assertEquals(1, results.size());
    }

    public void testLengthClass() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        LengthClassInspector inspector = new LengthClassInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    public void testActivityConsistent() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        ActivityConsistentInspector inspector = new ActivityConsistentInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

//        for (Result r : results) {
//            System.out.println(r.getMessage().displayMessage(Constant.AKADO_Avdth_BUNDLE_PROPERTIES, Locale.UK));
//        }
        assertEquals(4, results.size());
    }

    public void testPositionActivityConsistent() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        PositionActivityConsistentInspector inspector = new PositionActivityConsistentInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        assertEquals(2, results.size());
    }

    public void testWellNumberConsistent() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        WellNumberConsistentInspector inspector = new WellNumberConsistentInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

//        assertEquals(4, results.size());
        assertEquals(1, results.size());
    }

    public void testSuperSampleNumberConsistent() throws AvdthDriverException {
        Sample sample = (new SampleDAO()).findSampleByVessefIdAndDate(401, new DateTime(2014, 01, 9, 0, 0), 1);
        assertNotNull(sample);

        assertEquals(2, SuperSampleNumberConsistentInspector.count(sample.getSampleSpecies()));

        assertFalse(SuperSampleNumberConsistentInspector.checkIfHasOnlyOneSubSampling(sample));
        assertTrue(SuperSampleNumberConsistentInspector.checkIfHasManySubSampling(sample));

        sample = (new SampleDAO()).findSampleByVessefIdAndDate(703, new DateTime(2014, 01, 22, 0, 0), 20);
        assertNotNull(sample);

        assertEquals(0, SuperSampleNumberConsistentInspector.count(sample.getSampleSpecies()));

        assertTrue(SuperSampleNumberConsistentInspector.checkIfHasOnlyOneSubSampling(sample));
        assertFalse(SuperSampleNumberConsistentInspector.checkIfHasManySubSampling(sample));

        SuperSampleNumberConsistentInspector inspector = new SuperSampleNumberConsistentInspector();
        Results results = new Results();
        inspector = new SuperSampleNumberConsistentInspector();
        inspector.set(sample);
        results.addAll(inspector.execute());
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }

        assertEquals(1, results.size());
    }

    public void testAllSuperSampleNumberConsistent() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        SuperSampleNumberConsistentInspector inspector = new SuperSampleNumberConsistentInspector();
        Results results = new Results();
        for (Sample s : samples) {
//            System.out.println(s);
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getMessage());
        }
        assertEquals(14, results.size());
    }

    public void testSampleWithoutTrip() throws AvdthDriverException {

        List<Sample> samples = (new SampleDAO()).findAllSamples();
        assertNotNull(samples);
        assertEquals(46, samples.size());

        samples = (new SampleDAO()).findSampleByVessefIdAndDate(401, new DateTime(2014, 01, 9, 0, 0));
        assertNotNull(samples);
        assertEquals(1, samples.size());

        SampleWithoutTripInspector inspector = new SampleWithoutTripInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        assertEquals(1, results.size());

    }

    public void testSampleWithoutHarbour() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findSampleByVessefIdAndDate(551, new DateTime(2014, 02, 17, 0, 0));
        assertNotNull(samples);
        assertEquals(7, samples.size());

        HarbourInspector inspector = new HarbourInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);
        results.exportToXLS(AAProperties.STANDARD_DIRECTORY);
        assertEquals(2, results.size());

    }

    public void testSampleWithoutSpecies() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findSampleByVessefIdAndDate(703, new DateTime(2014, 01, 22, 0, 0));
        assertNotNull(samples);
        assertEquals(6, samples.size());

        SampleWithoutSpeciesInspector inspector = new SampleWithoutSpeciesInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        assertEquals(1, results.size());
    }

    public void testSampleWithoutMeasure() throws AvdthDriverException {
        List<Sample> samples = (new SampleDAO()).findSampleByVessefIdAndDate(401, new DateTime(2014, 01, 9, 0, 0));
        assertNotNull(samples);
        assertEquals(1, samples.size());

        SampleWithoutMeasureInspector inspector = new SampleWithoutMeasureInspector();
        Results results = new Results();
        for (Sample s : samples) {
            inspector.set(s);
            results.addAll(inspector.execute());
        }
        assertNotNull(results);

        assertEquals(1, results.size());

    }

}
