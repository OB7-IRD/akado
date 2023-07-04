package fr.ird.driver.observe.dao.data.ps.common;

import fr.ird.driver.observe.Ids;
import fr.ird.driver.observe.business.Entity;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.localmarket.Batch;
import fr.ird.driver.observe.business.data.ps.localmarket.Survey;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.FloatingObject;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.data.ps.logbook.Well;
import fr.ird.driver.observe.business.data.ps.logbook.WellActivity;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.dao.EntityNotFoundException;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.Dates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class AbstractTripDaoTest {

    public static final Date LANDING_DATE = Dates.createDate(8, 4, 2019);
    private final Logger log = LogManager.getLogger(getClass());

    protected abstract ObserveService service();

    @Test
    public void findById() {
        TripDao dao = service().getDaoSupplier().getPsCommonTripDao();
        Trip trip = dao.findById(Ids.get(Trip.class));
        Assert.assertNotNull(trip);

        // ps_landing
        assertSize(trip.getLanding(), 2);

        // ps_logbook

        assertSize(trip.getLogbookRoute(), 2);

        Route logbookRoute = getFirst(trip.getLogbookRoute());
        assertSize(logbookRoute.getActivity(), 1);

        Activity logbookActivity = getFirst(logbookRoute.getActivity());
        assertSize(logbookActivity.getObservedSystem(), 2);
        assertSize(logbookActivity.getCatches(), 2);
        assertSize(logbookActivity.getFloatingObject(), 1);

        FloatingObject logbookFloatingObject = getFirst(logbookActivity.getFloatingObject());
        assertSize(logbookFloatingObject.getFloatingObjectPart(), 1);
        assertSize(logbookFloatingObject.getTransmittingBuoy(), 1);

        assertSize(trip.getWell(), 1);
        Well logbookWell = getFirst(trip.getWell());
        assertSize(logbookWell.getWellActivity(), 1);

        WellActivity logbookWellActivity = getFirst(logbookWell.getWellActivity());
        assertSize(logbookWellActivity.getWellActivitySpecies(), 1);

        assertSize(trip.getLogbookSample(), 2);

        Sample logbookSample = getFirst(trip.getLogbookSample());
        assertSize(logbookSample.getSampleActivity(), 1);
        assertSize(logbookSample.getSampleSpecies(), 1);

        SampleSpecies logbookSampleSpecies = getFirst(logbookSample.getSampleSpecies());
        assertSize(logbookSampleSpecies.getSampleSpeciesMeasure(), 1);

        // ps_localmarket
        assertSize(trip.getLocalmarketBatch(), 2);
        Batch localmarketBatch = getFirst(trip.getLocalmarketBatch());
        Assert.assertNotNull(localmarketBatch.getPackaging());
        Assert.assertNotNull(localmarketBatch.getSpecies());
        Assert.assertNotNull(localmarketBatch.getSurvey());

        assertSize(trip.getLocalmarketSurvey(), 1);

        Survey localmarketSurvey = getFirst(trip.getLocalmarketSurvey());
        assertSize(localmarketSurvey.getSurveyPart(), 3);

        assertSize(trip.getLocalmarketSample(), 2);

        fr.ird.driver.observe.business.data.ps.localmarket.Sample localmarketSample = getFirst(trip.getLocalmarketSample());
        assertSize(localmarketSample.getSampleSpecies(), 1);

        fr.ird.driver.observe.business.data.ps.localmarket.SampleSpecies localmarketSampleSpecies = getFirst(localmarketSample.getSampleSpecies());
        assertSize(localmarketSampleSpecies.getSampleSpeciesMeasure(), 2);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFound() {
        TripDao dao = service().getDaoSupplier().getPsCommonTripDao();
        Trip trip = dao.findById(Ids.get(Trip.class) + System.nanoTime());
        Assert.assertNull(trip);
    }

    @Test
    public void findTrips() {

        String vesselId = Ids.get(Vessel.class);
        List<Vessel> vessel = List.of(service().getReferentialCache().get(Vessel.class, vesselId));
        Date start;
        Date end;
        TripDao dao = service().getDaoSupplier().getPsCommonTripDao();
        List<Trip> actual = dao.findTrips(vessel, null, null, null);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());

        actual = dao.findTrips(vessel, null, null, LANDING_DATE);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());

        actual = dao.findTrips(vessel, null, LANDING_DATE, null);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());

        actual = dao.findTrips(vessel, null, LANDING_DATE, LANDING_DATE);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());

        end = new Date();
        actual = dao.findTrips(vessel, null, null, end);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());

        actual = dao.findTrips(vessel, null, LANDING_DATE, end);
        Assert.assertNotNull(actual);
        Assert.assertEquals(1, actual.size());

        start = new Date();
        actual = dao.findTrips(vessel, null, start, end);
        Assert.assertNotNull(actual);
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void count() {
        TripDao dao = service().getDaoSupplier().getPsCommonTripDao();
        long actual = dao.count();
        Assert.assertEquals(1, actual);
    }

    @Test
    public void lastLandingDate() {
        TripDao dao = service().getDaoSupplier().getPsCommonTripDao();
        Date actual = dao.lastLandingDate();
        Assert.assertEquals(LANDING_DATE, actual);
    }

    @Test
    public void firstLandingDate() {
        TripDao dao = service().getDaoSupplier().getPsCommonTripDao();
        Date actual = dao.firstLandingDate();
        Assert.assertEquals(LANDING_DATE, actual);
    }

    @Test
    public void findPreviousTrip() {
        String vesselId = Ids.get(Vessel.class);
        Vessel vessel = service().getReferentialCache().get(Vessel.class, vesselId);

        TripDao dao = service().getDaoSupplier().getPsCommonTripDao();
        Trip actual = dao.findPreviousTrip(vessel, LANDING_DATE);
        Assert.assertNull(actual);
    }

    private <E extends Entity> E getFirst(Collection<E> entities) {
        E result = entities.iterator().next();
        log.debug("{}: {}", result.getClass().getName(), result.getTopiaId());
//        System.out.println(result.getClass().getName() + ".id=" + result.getTopiaId());
        return result;
    }

    private void assertSize(Collection<? extends Entity> collection, int expectedSize) {
        getFirst(collection);
        Assert.assertNotNull(collection);
        Assert.assertEquals(expectedSize, collection.size());
        // try to load the hole collection to fire lazy loaded
        collection.parallelStream().forEach(Assert::assertNotNull);
    }
}
