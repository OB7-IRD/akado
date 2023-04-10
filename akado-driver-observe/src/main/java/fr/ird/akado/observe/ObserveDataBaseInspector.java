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
package fr.ird.akado.observe;

import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.AkadoException;
import fr.ird.akado.core.selector.TemporalSelector;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.observe.inspector.activity.ObserveActivityInspector;
import fr.ird.akado.observe.inspector.activity.PositionInEEZInspector;
import fr.ird.akado.observe.inspector.activity.PositionInspector;
import fr.ird.akado.observe.inspector.anapo.ObserveAnapoActivityInspector;
import fr.ird.akado.observe.inspector.anapo.ObserveAnapoActivityListInspector;
import fr.ird.akado.observe.inspector.metatrip.ObserveTripListInspector;
import fr.ird.akado.observe.inspector.sample.ObserveSampleInspector;
import fr.ird.akado.observe.inspector.trip.ObserveTripInspector;
import fr.ird.akado.observe.inspector.well.ObserveWellInspector;
import fr.ird.akado.observe.result.InfoResult;
import fr.ird.akado.observe.result.ObserveMessage;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.object.Resume;
import fr.ird.akado.observe.selector.FlagSelector;
import fr.ird.akado.observe.selector.VesselSelector;
import fr.ird.akado.observe.task.ActivityTask;
import fr.ird.akado.observe.task.AnapoTask;
import fr.ird.akado.observe.task.ObserveDataBaseInspectorTask;
import fr.ird.akado.observe.task.SampleTask;
import fr.ird.akado.observe.task.TripTask;
import fr.ird.akado.observe.task.WellTask;
import fr.ird.driver.anapo.common.exception.ANAPODriverException;
import fr.ird.driver.anapo.service.ANAPOService;
import fr.ird.driver.observe.business.ObserveVersion;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.Version;
import io.ultreia.java4all.util.sql.conf.JdbcConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.ird.akado.core.common.AAProperties.KEY_DATE_FORMAT_XLS;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_COUNTRIES_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_HARBOUR_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_OCEAN_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_STANDARD_DIRECTORY;
import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import static fr.ird.common.DateTimeUtils.convertDate;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveDataBaseInspector extends DataBaseInspector {
    private static final Logger log = LogManager.getLogger(ObserveDataBaseInspector.class);
    private static final String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    private final List<FlagSelector> flagSelectors;
    private final List<VesselSelector> vesselSelectors;
    Resume r = new Resume();
    private String exportDirectoryPath;

    public static TemporalSelector getTemporalSelector() {
        return temporalSelector;
    }

    public ObserveDataBaseInspector(JdbcConfiguration configuration, Path baseDirectory) throws Exception {
        DateTime currentDateTime = DateTime.now();
        if (AAProperties.isResultsEnabled()) {
            exportDirectoryPath = baseDirectory.resolve("_akado_result_" + currentDateTime.getYear() + currentDateTime.getMonthOfYear() + currentDateTime.getDayOfMonth() + "_" + currentDateTime.getHourOfDay() + currentDateTime.getMinuteOfHour()).toString();
            if (!(new File(exportDirectoryPath)).exists()) {
                Files.createDirectories(new File(exportDirectoryPath).toPath());
            }
            log.info("The results will be write in the directory " + exportDirectoryPath);
        }
        log.debug("CONFIGURATION PROPERTIES " + CONFIGURATION_PROPERTIES);
        loadProperties();
        prepare();
        setResults(new Results());
        getAkadoMessages().setBundleProperties(ObserveMessage.AKADO_OBSERVE_BUNDLE_PROPERTIES);

        flagSelectors = new ArrayList<>();
        vesselSelectors = new ArrayList<>();
        if (AAProperties.VESSEL_SELECTED != null && !"".equals(AAProperties.VESSEL_SELECTED)) {
            log.info("Vessel selection : " + AAProperties.VESSEL_SELECTED);
            String[] codeList = AAProperties.VESSEL_SELECTED.split("\\s*\\|\\s*");
            for (String code : codeList) {
                addVesselConstraint(code);
            }
        }
        boolean anapoConfigurationEnabled = AAProperties.ANAPO_DB_URL != null && !"".equals(AAProperties.ANAPO_DB_URL);
        boolean anapoEnabled = AAProperties.isAnapoInspectorEnabled();
        ObserveService.getService().init(configuration);
        ObserveService.getService().open();
        if (anapoEnabled) {
            if (!anapoConfigurationEnabled) {
                throw new ANAPODriverException("The connection to ANAPO database has failed. The database isn't set correctly.");
            }
            ANAPOService.getService().init(AAProperties.PROTOCOL_JDBC_ACCESS + AAProperties.ANAPO_DB_URL, JDBC_ACCESS_DRIVER, AAProperties.ANAPO_USER, AAProperties.ANAPO_PASSWORD);
        }
        r.setDatabaseName(configuration.getJdbcConnectionUrl());

        List<Inspector<?>> inspectors = new LinkedList<>();
        if (AAProperties.isTripInspectorEnabled()) {
            inspectors.addAll(ObserveTripInspector.loadInspectors());
            inspectors.addAll(ObserveTripListInspector.loadInspectors());
        }
        if (AAProperties.isActivityInspectorEnabled()) {
            List<ObserveActivityInspector> activityInspectors = ObserveActivityInspector.loadInspectors();
            if (!AAProperties.isPositionInspectorEnabled()) {
                // Remove PositionInspector
                activityInspectors.removeIf(i -> i instanceof PositionInspector);
                // Remove PositionInEEZInspector
                activityInspectors.removeIf(i -> i instanceof PositionInEEZInspector);
            }
            inspectors.addAll(activityInspectors);
        }
        if (anapoEnabled) {
            inspectors.addAll(ObserveAnapoActivityInspector.loadInspectors());
            inspectors.addAll(ObserveAnapoActivityListInspector.loadInspectors());
        }
        if (AAProperties.isSampleInspectorEnabled()) {
            inspectors.addAll(ObserveSampleInspector.loadInspectors());
        }
        if (AAProperties.isWellInspectorEnabled()) {
            inspectors.addAll(ObserveWellInspector.loadInspectors());
        }
        log.info("Found {} inspector(s) to apply.", inspectors.size());
        setInspectors(inspectors);
    }

    @Override
    public void info() {

        r.setTripCount((int) ObserveService.getService().getDaoSupplier().getPsCommonTripDao().count());
        r.setFirstDateOfTrip(convertDate(ObserveService.getService().getDaoSupplier().getPsCommonTripDao().firstLandingDate()));
        r.setLastDateOfTrip(convertDate(ObserveService.getService().getDaoSupplier().getPsCommonTripDao().lastLandingDate()));
        r.setActivityCount((int) ObserveService.getService().getDaoSupplier().getPsLogbookActivityDao().count());
        r.setFirstDateOfActivity(convertDate(ObserveService.getService().getDaoSupplier().getPsLogbookRouteDao().firstDate()));
        r.setLastDateOfActivity(convertDate(ObserveService.getService().getDaoSupplier().getPsLogbookRouteDao().lastDate()));
        r.setSampleCount((int) ObserveService.getService().getDaoSupplier().getPsLogbookSampleDao().count());
        r.setWellCount((int) ObserveService.getService().getDaoSupplier().getPsLogbookWellDao().count());
        InfoResult info = new InfoResult(r, MessageDescriptions.I_0001_DATABASE_INFO);

        List<Object> infos = new ArrayList<>();
        infos.add(r.getTripCount());
        infos.add(DATE_FORMATTER.print(r.getFirstDateOfTrip()));
        infos.add(DATE_FORMATTER.print(r.getLastDateOfTrip()));
        infos.add(r.getActivityCount());
        infos.add(DATE_FORMATTER.print(r.getFirstDateOfActivity()));
        infos.add(DATE_FORMATTER.print(r.getLastDateOfActivity()));
        infos.add(r.getSampleCount());
        infos.add(r.getWellCount());
        info.setMessageParameters(infos);
        getAkadoMessages().add(info.getMessage());
        log.info(r);
    }

    @Override
    public Results getResults() {
        return (Results) super.getResults();
    }

    @Override
    public void validate() throws Exception {
        Version observeVersion = ObserveService.getService().getDaoSupplier().getVersionDao().getVersionNumber();
        if (!observeVersion.equals(ObserveVersion.VERSION_OBSERVE_COMPATIBILITY)) {
            ObserveMessage message = new ObserveMessage(MessageDescriptions.E_0002_DATABASE_NOT_COMPATIBLE, List.of(observeVersion, ObserveVersion.VERSION_OBSERVE_COMPATIBILITY));
            throw new AkadoException(message.getContent());
        }
        List<Trip> tripList = getTripsToValidate();
        List<ObserveDataBaseInspectorTask<?>> tasks = new ArrayList<>();
        if (AAProperties.isAkadoInspectorEnabled()) {
            if (AAProperties.isTripInspectorEnabled()) {
                tasks.add(new TripTask(exportDirectoryPath, tripList, getInspectors(), getResults()));
            }
            if (AAProperties.isActivityInspectorEnabled()) {
                tasks.add(new ActivityTask(exportDirectoryPath, tripList, getInspectors(), getResults()));
            }
            if (AAProperties.isWellInspectorEnabled()) {
                tasks.add(new WellTask(exportDirectoryPath, tripList, getInspectors(), getResults()));
            }
            if (AAProperties.isSampleInspectorEnabled()) {
                tasks.add(new SampleTask(exportDirectoryPath, tripList, getInspectors(), getResults()));
            }
        }
        if (AAProperties.isAnapoInspectorEnabled()) {
            tasks.add(new AnapoTask(exportDirectoryPath, tripList, getInspectors(), getResults()));
        }
//        ExecutorService exec = Executors.newFixedThreadPool(AAProperties.NB_PROC);
        for (ObserveDataBaseInspectorTask<?> task : tasks) {
            task.run();
//            exec.submit(task);
        }
//        exec.shutdown();
//        boolean done = exec.awaitTermination(120, TimeUnit.MINUTES);
//        if (!done) {
//            log.error("Executor service not done after 2 hours...");
//        }
    }

    private List<Trip> getTripsToValidate() {
        return ObserveService.getService().getDaoSupplier().getPsCommonTripDao().findTrips(
                vesselSelectors.stream().map(VesselSelector::get).collect(Collectors.toList()),
                flagSelectors.stream().map(FlagSelector::get).collect(Collectors.toList()),
                getTemporalSelector().getStart() == null ? null : getTemporalSelector().getStart().toDate(),
                getTemporalSelector().getEnd() == null ? null : getTemporalSelector().getEnd().toDate()
        );
    }

    public void addFlagConstraint(String flag) {
        flagSelectors.add(new FlagSelector(flag));
    }

    public void addVesselConstraint(String code) {
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
            AAProperties.TRIP_INSPECTOR = CONFIGURATION_PROPERTIES.getProperty(AAProperties.KEY_TRIP_INSPECTOR);
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
        ObserveService.getService().close();
    }

}
