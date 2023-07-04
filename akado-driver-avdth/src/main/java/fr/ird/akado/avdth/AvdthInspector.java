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

import fr.ird.akado.avdth.activity.PositionInEEZInspector;
import fr.ird.akado.avdth.activity.PositionInspector;
import fr.ird.akado.avdth.result.AVDTHMessage;
import fr.ird.akado.avdth.result.InfoResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.object.Resume;
import fr.ird.akado.avdth.selector.FlagSelector;
import fr.ird.akado.avdth.selector.VesselSelector;
import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.DataBaseInspectorTask;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.AkadoException;
import fr.ird.akado.core.selector.TemporalSelector;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.common.list.comprehesion.Func;
import fr.ird.common.message.Message;
import fr.ird.driver.anapo.common.exception.ANAPODriverException;
import fr.ird.driver.anapo.service.ANAPOService;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.business.Well;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.VersionDAO;
import fr.ird.driver.avdth.service.AvdthService;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.ird.akado.core.common.AAProperties.KEY_DATE_FORMAT_XLS;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_COUNTRIES_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_HARBOUR_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_OCEAN_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_STANDARD_DIRECTORY;
import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import static fr.ird.common.ListUtils.map;

/**
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 21 mai 2014
 * @since 2.0
 */
public class AvdthInspector extends DataBaseInspector {
    private static final Logger log = LogManager.getLogger(AvdthInspector.class);
    public static final String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";

    private String exportDirectoryPath;
    private static final int VERSION_AVDTH_COMPATIBILITY = 35;

    /**
     * List all trips inspectors.
     */
    public static final List<Inspector<?>> ALL_TRIP_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.trip");
    /**
     * List all trips (with partial landings) inspectors.
     */
    public static final List<Inspector<?>> ALL_METATRIP_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.metatrip");
    /**
     * List all inspectors ANAPO applied on activity.
     */
    public static final List<Inspector<?>> ALL_ANAPO_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.anapo.vms");
    /**
     * List all inspectors applied applied on all activities.
     */
    public static final List<Inspector<?>> ALL_ANAPO_VMS_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.anapo.activity");
    /**
     * List all activities inspectors.
     */
    public static final List<Inspector<?>> ALL_ACTIVITY_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.activity");
    /**
     * List all activities inspectors.
     */
    public static final List<Inspector<?>> ALL_ACTIVITIES_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.activities");
    /**
     * List all samples inspectors.
     */
    public static final List<Inspector<?>> ALL_SAMPLE_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.sample");
    /**
     * List all wells inspectors.
     */
    public static final List<Inspector<?>> ALL_WELL_INSPECTORS = ReflectionsUtils.loadClassesFromPackage("fr.ird.akado.avdth.well");

    Resume r = new Resume();

    /**
     * @param configuration jdbc configuration
     * @throws Exception if any error
     */
    public AvdthInspector(JdbcConfiguration configuration) throws Exception {

        String url = configuration.getJdbcConnectionUrl();
        // Test l'export des resultats au fil de l'eau
        String databasePath = url.substring(AAProperties.PROTOCOL_JDBC_ACCESS.length());
        String pathExport = new File(databasePath).getParent();
        String name = new File(databasePath).getName();
        String dbName = name.substring(0, name.lastIndexOf('.'));
        DateTime currentDateTime = DateTime.now();
        if (AAProperties.isResultsEnabled()) {
            exportDirectoryPath = pathExport + File.separator + dbName + "_akado_result_" + currentDateTime.getYear() + currentDateTime.getMonthOfYear() + currentDateTime.getDayOfMonth() + "_" + currentDateTime.getHourOfDay() + currentDateTime.getMinuteOfHour();
            if (!(new File(exportDirectoryPath)).exists()) {
                new File(exportDirectoryPath).mkdirs();
            }
            log.info("The results will be write in the directory " + exportDirectoryPath);
        }
        log.debug("CONFIGURATION PROPERTIES " + CONFIGURATION_PROPERTIES);
        loadProperties();
        prepare();
        setResults(new Results());
        getAkadoMessages().setBundleProperties(Constant.AKADO_AVDTH_BUNDLE_PROPERTIES);

        flagSelectors = new ArrayList<>();
        vesselSelectors = new ArrayList<>();
        try {
            AvdthService.getService().init(configuration.getJdbcConnectionUrl(), JDBC_ACCESS_DRIVER, configuration.getJdbcConnectionUser(), configuration.getJdbcConnectionPassword());
            if (AAProperties.isAnapoInspectorEnabled()) {

                if (AAProperties.ANAPO_DB_URL == null || "".equals(AAProperties.ANAPO_DB_URL)) {
                    throw new ANAPODriverException("The connection to ANAPO database has failed. The database isn't set correctly.");
                }
                ANAPOService.getService().init(AAProperties.PROTOCOL_JDBC_ACCESS + AAProperties.ANAPO_DB_URL, JDBC_ACCESS_DRIVER, AAProperties.ANAPO_USER, AAProperties.ANAPO_PASSWORD);

            }
        } catch (AvdthDriverException ex) {
            log.error(ex.getMessage(), ex);
        }
        r.setDatabaseName(url.substring(url.lastIndexOf(File.separator) + 1));
        if (AAProperties.isTripInspectorEnabled()) {
            this.inspectors.addAll(AvdthInspector.ALL_TRIP_INSPECTORS);
            this.inspectors.addAll(AvdthInspector.ALL_METATRIP_INSPECTORS);
        }

        if (AAProperties.isActivityInspectorEnabled()) {
            this.inspectors.addAll(AvdthInspector.ALL_ACTIVITY_INSPECTORS);
//            this.inspectors.addAll(AvdthInspector.ALL_ACTIVITIES_INSPECTORS);
        }
        if (AAProperties.isAnapoInspectorEnabled()) {
            this.inspectors.addAll(AvdthInspector.ALL_ANAPO_INSPECTORS);
            this.inspectors.addAll(AvdthInspector.ALL_ANAPO_VMS_INSPECTORS);
        }
        if (AAProperties.isSampleInspectorEnabled()) {
            this.inspectors.addAll(AvdthInspector.ALL_SAMPLE_INSPECTORS);
        }
        if (AAProperties.isWellInspectorEnabled()) {
            this.inspectors.addAll(AvdthInspector.ALL_WELL_INSPECTORS);
        }
    }

    @Override
    public void info() {
//        DateTimeZone timeZone = DateTimeZone.forID("Paris/Europe");

        r.setTripCount(AvdthService.getService().getTripDAO().count());
        r.setFirstDateOfTrip(AvdthService.getService().getTripDAO().firstLandingDate());
        r.setLastDateOfTrip(AvdthService.getService().getTripDAO().lastLandingDate());

        r.setActivityCount(AvdthService.getService().getActivityDAO().count());
        r.setFirstDateOfActivity(AvdthService.getService().getActivityDAO().firstActivityDate());
        r.setLastDateOfActivity(AvdthService.getService().getActivityDAO().lastActivityDate());

        r.setSampleCount(AvdthService.getService().getSampleDAO().count());
        r.setWellCount(AvdthService.getService().getWellDAO().count());
        InfoResult info = new InfoResult();
        info.set(r);
//
        List<Object> infos = new ArrayList<>();
        infos.add(AvdthService.getService().getTripDAO().count());
        infos.add(DATE_FORMATTER.print(AvdthService.getService().getTripDAO().firstLandingDate()));
        infos.add(DATE_FORMATTER.print(AvdthService.getService().getTripDAO().lastLandingDate()));
        infos.add(AvdthService.getService().getActivityDAO().count());
        infos.add(DATE_FORMATTER.print(AvdthService.getService().getActivityDAO().firstActivityDate()));
        infos.add(DATE_FORMATTER.print(AvdthService.getService().getActivityDAO().lastActivityDate()));
        infos.add(AvdthService.getService().getSampleDAO().count());
        infos.add(AvdthService.getService().getWellDAO().count());
        info.setMessageType(Message.INFO);
        info.setMessageCode(Constant.CODE_INFO_DATABASE);
        info.setMessageLabel(Constant.LABEL_INFO_DATABASE);
        info.setMessageParameters(infos);
        getAkadoMessages().add(info.getMessage());
        log.info(r.toString());

    }

    private class ActivityTask extends AvdthDataBaseInspectorTask {

        public ActivityTask(String exportDirectoryPath, Results r) {
            super(r, exportDirectoryPath);
        }

        @Override
        public void inspect() {
            try {
                if (AAProperties.VESSEL_SELECTED != null && !"".equals(AAProperties.VESSEL_SELECTED)) {
                    log.info("Vessel selection : " + AAProperties.VESSEL_SELECTED);
                    String[] codeList = AAProperties.VESSEL_SELECTED.split("\\|");
                    for (String code : codeList) {
                        addVesselConstraint(Integer.valueOf(code));
                    }
                }

                List<Activity> activities = getActivitiesToValidate();
                log.info("Found {} activities to process", activities.size());
                log.info("Activities processing...");
                for (Activity a : activities) {
                    log.debug(a.getID());
//            System.out.println(getClass().getName() + " ActivityInspector=" + a);
                    for (Inspector i : getInspectors()) {
                        if (ALL_ACTIVITY_INSPECTORS.contains(i)) {
                            if (AAProperties.isPositionInspectorEnabled() || !((i instanceof PositionInspector) || (i instanceof PositionInEEZInspector))) {
                                i.set(a);
                                results.addAll(i.execute());
                            }
                        }
                    }
                }
                log.info("Activities list processing...");
                for (Inspector i : getInspectors()) {
                    if (ALL_ACTIVITIES_INSPECTORS.contains(i)) {
                        i.set(activities);
                        this.getResults().addAll(i.execute());
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }


        @Override
        protected void writeResults(String directoryPath, Results results) throws IOException {
            results.writeInActivitySheet(directoryPath);
            results.writeLogs(directoryPath);
        }
    }

    private class TripTask extends AvdthDataBaseInspectorTask {

        public TripTask(String exportDirectoryPath, Results r) {
            super(r, exportDirectoryPath);
        }

        @Override
        public void inspect() {
            try {

                List<Trip> trips = getTripsToValidate();
                log.info("Found {} trips to process", trips.size());
                log.info("Trip processing...");
                for (Trip m : trips) {
                    log.debug(m.getID());
                    for (Inspector i : getInspectors()) {
                        if (ALL_TRIP_INSPECTORS.contains(i)) {
                            i.set(m);
                            this.getResults().addAll(i.execute());
                        }
                    }
                }
                log.info("MetaTrip processing...");
                for (Inspector i : getInspectors()) {
                    if (ALL_METATRIP_INSPECTORS.contains(i)) {
                        i.set(trips);
                        this.getResults().addAll(i.execute());
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        @Override
        protected void writeResults(String directoryPath, Results results) throws IOException {
            results.writeInTripSheet(directoryPath);
            results.writeInMetaTripSheet(directoryPath);
            results.writeLogs(directoryPath);
        }
    }

    private class AnapoTask extends AvdthDataBaseInspectorTask {

        public AnapoTask(String exportDirectoryPath, Results r) {
            super(r, exportDirectoryPath);
        }

        @Override
        public void inspect() {
            try {
                if (AAProperties.VESSEL_SELECTED != null && !"".equals(AAProperties.VESSEL_SELECTED)) {
                    log.info("Vessel selection : " + AAProperties.VESSEL_SELECTED);
                    String[] codeList = AAProperties.VESSEL_SELECTED.split("\\|");
                    for (String code : codeList) {
                        addVesselConstraint(Integer.valueOf(code));
                    }
                }

                log.info("Anapo (on activities) processing...");
                for (Inspector i : ALL_ANAPO_INSPECTORS) {
                    if (AAProperties.ANAPO_DB_URL != null && AAProperties.isAnapoInspectorEnabled()) {
                        for (Activity activity : getActivitiesToValidate()) {
                            i.set(activity);
                            this.getResults().addAll(i.execute());
                        }
                    }
                }
                log.info("Anapo (on activities list) processing...");
                for (Inspector i : ALL_ANAPO_VMS_INSPECTORS) {
                    if (AAProperties.ANAPO_DB_URL != null && AAProperties.isAnapoInspectorEnabled()) {
                        i.set(getActivitiesToValidate());
                        this.getResults().addAll(i.execute());
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        @Override
        protected void writeResults(String directoryPath, Results results) throws IOException {
            results.writeInAnapoSheet(directoryPath);
            results.writeLogs(directoryPath);
        }
    }

    private class WellTask extends AvdthDataBaseInspectorTask {

        public WellTask(String exportDirectoryPath, Results r) {
            super(r, exportDirectoryPath);
        }

        @Override
        public void inspect() {
            try {
                List<Well> wells = getWellsToValidate();
                log.info("Found {} wells to process", wells.size());
                log.info("Well processing...");
                for (Well e : wells) {
                    log.debug(e.getID());
//            System.out.println(getClass().getName() + " Well=" + e);
                    for (Inspector i : getInspectors()) {
                        if (ALL_WELL_INSPECTORS.contains(i)) {
                            i.set(e);
                            this.getResults().addAll(i.execute());
                        }
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        @Override
        protected void writeResults(String directoryPath, Results results) throws IOException {
            results.writeInWellSheet(directoryPath);
            results.writeLogs(directoryPath);
        }
    }

    private class SampleTask extends AvdthDataBaseInspectorTask {

        public SampleTask(String exportDirectoryPath, Results r) {
            super(r, exportDirectoryPath);
        }

        @Override
        public void inspect() {
            try {

                List<Sample> samples = getSamplesToValidate();
                log.info("Found {} samples to process", samples.size());
                log.info("Sample processing...");
                for (Sample e : samples) {
                    log.debug(e.getID());
                    for (Inspector i : getInspectors()) {
                        if (ALL_SAMPLE_INSPECTORS.contains(i)) {
                            i.set(e);
                            this.getResults().addAll(i.execute());
                        }
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        @Override
        protected void writeResults(String directoryPath, Results results) throws IOException {
            results.writeInSampleSheet(directoryPath);
            results.writeLogs(directoryPath);
        }
    }

    @Override
    public void validate() throws Exception {
        int avdthVersion = new VersionDAO().getVersionNumber();
        if (avdthVersion != VERSION_AVDTH_COMPATIBILITY) {
            AVDTHMessage message = new AVDTHMessage(Constant.CODE_DATABASE_NOT_COMPATIBLE, Constant.LABEL_DATABASE_NOT_COMPATIBLE, List.of(VERSION_AVDTH_COMPATIBILITY), Message.ERROR);
            throw new AkadoException(message.getContent());
        }
        List<DataBaseInspectorTask> tasks = new ArrayList<>();
        Results results = (Results) getResults();
        if (AAProperties.isAkadoInspectorEnabled()) {
            tasks.add(new TripTask(exportDirectoryPath, results));
            tasks.add(new ActivityTask(exportDirectoryPath, results));
            tasks.add(new WellTask(exportDirectoryPath, results));
            tasks.add(new SampleTask(exportDirectoryPath, results));
        }
        tasks.add(new AnapoTask(exportDirectoryPath, results));
//        long start = System.currentTimeMillis();
        for (DataBaseInspectorTask task : tasks) {
            task.run();
        }

//        long stop = System.currentTimeMillis();

//        System.out.println((stop - start) + " ms");
        //System.out.println(getClass().getName() + " validate() done");
//        this.getAkadoMessages().log();
//        writeResultsInFile();
    }

    private List<Trip> getTripsToValidate() throws AvdthDriverException {
//        System.out.println(getClass().getName() + " getTripsToValidate()");
        return AvdthService.getService().getTripDAO().findTrips(
                map(vesselSelectors, new Func<VesselSelector, Vessel>() {
                    @Override
                    public Vessel apply(VesselSelector in) {
                        return in.get();
                    }
                }),
                map(flagSelectors, new Func<FlagSelector, Country>() {
                    @Override
                    public Country apply(FlagSelector in) {
                        return in.get();
                    }
                }),
                temporalSelector.getStart(),
                temporalSelector.getEnd()
                                                               );
    }

    private List<Sample> getSamplesToValidate() throws AvdthDriverException {
        return AvdthService.getService().getSampleDAO().findSample(
                map(vesselSelectors, new Func<VesselSelector, Vessel>() {
                    @Override
                    public Vessel apply(VesselSelector in) {
                        return in.get();
                    }
                }),
                map(flagSelectors, new Func<FlagSelector, Country>() {
                    @Override
                    public Country apply(FlagSelector in) {
                        return in.get();
                    }
                }),
                temporalSelector.getStart(),
                temporalSelector.getEnd()
                                                                  );
    }

    private List<Well> getWellsToValidate() throws AvdthDriverException {
        return AvdthService.getService().getWellDAO().findWell(
                map(vesselSelectors, new Func<VesselSelector, Vessel>() {
                    @Override
                    public Vessel apply(VesselSelector in) {
                        return in.get();
                    }
                }),
                map(flagSelectors, new Func<FlagSelector, Country>() {
                    @Override
                    public Country apply(FlagSelector in) {
                        return in.get();
                    }
                }),
                temporalSelector.getStart(),
                temporalSelector.getEnd()
                                                              );
    }

    private List<Activity> getActivitiesToValidate() throws AvdthDriverException {
        return AvdthService.getService().getActivityDAO().findActivities(
                map(vesselSelectors, new Func<VesselSelector, Vessel>() {
                    @Override
                    public Vessel apply(VesselSelector in) {
                        return in.get();
                    }
                }),
                map(flagSelectors, new Func<FlagSelector, Country>() {
                    @Override
                    public Country apply(FlagSelector in) {
                        return in.get();
                    }
                }),
                temporalSelector.getStart(),
                temporalSelector.getEnd()
                                                                        );
    }

    private final List<FlagSelector> flagSelectors;

    public void addFlagConstraint(String flag) {
        flagSelectors.add(new FlagSelector(flag));
    }

    private final List<VesselSelector> vesselSelectors;

    public void addVesselConstraint(Integer code) {
        vesselSelectors.add(new VesselSelector(code));
    }

    private void prepare() throws Exception {
        if (!GISHandler.getService().exists()) {
            loadProperties();
            GISHandler.getService().init(
                    AAProperties.STANDARD_DIRECTORY,
                    AAProperties.SHP_COUNTRIES_PATH,
                    AAProperties.SHP_OCEAN_PATH,
                    AAProperties.SHP_HARBOUR_PATH,
                    AAProperties.SHP_EEZ_PATH
                                        );
            GISHandler.getService().create();
        }
    }

    private void loadProperties() {
        if (AAProperties.STANDARD_DIRECTORY == null && CONFIGURATION_PROPERTIES != null) {
            AAProperties.STANDARD_DIRECTORY = DataBaseInspector.CONFIGURATION_PROPERTIES.getProperty(KEY_STANDARD_DIRECTORY);
            AAProperties.SHP_COUNTRIES_PATH = CONFIGURATION_PROPERTIES.getProperty(KEY_SHP_COUNTRIES_PATH);
            AAProperties.SHP_OCEAN_PATH = CONFIGURATION_PROPERTIES.getProperty(KEY_SHP_OCEAN_PATH);
            AAProperties.SHP_HARBOUR_PATH = CONFIGURATION_PROPERTIES.getProperty(KEY_SHP_HARBOUR_PATH);
            AAProperties.SHP_EEZ_PATH = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_SHP_EEZ_PATH);
            AAProperties.DATE_FORMAT_XLS = CONFIGURATION_PROPERTIES.getProperty(KEY_DATE_FORMAT_XLS);

            AAProperties.SAMPLE_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_SAMPLE_INSPECTOR);
            AAProperties.WELL_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_WELL_INSPECTOR);
            AAProperties.TRIP_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_WELL_INSPECTOR);
            AAProperties.POSITION_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_POSITION_INSPECTOR);
            AAProperties.ACTIVITY_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_ACTIVITY_INSPECTOR);

            AAProperties.WARNING_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_WARNING_INSPECTOR);
            AAProperties.ANAPO_DB_URL = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_ANAPO_DB_PATH);
            AAProperties.ANAPO_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_ANAPO_INSPECTOR);
            AAProperties.ANAPO_VMS_COUNTRY = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_ANAPO_VMS_COUNTRY);

            AAProperties.AKADO_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_AKADO_INSPECTOR);
        }
    }

    @Override
    public void close() {
        AvdthService.getService().close();
    }

    public static TemporalSelector getTemporalSelector() {
        return temporalSelector;
    }

}
