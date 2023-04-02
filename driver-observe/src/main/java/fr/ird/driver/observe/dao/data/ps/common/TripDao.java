package fr.ird.driver.observe.dao.data.ps.common;

import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.DataQuality;
import fr.ird.driver.observe.business.referential.common.Harbour;
import fr.ird.driver.observe.business.referential.common.Ocean;
import fr.ird.driver.observe.business.referential.common.Person;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.ps.common.AcquisitionStatus;
import fr.ird.driver.observe.business.referential.ps.common.Program;
import fr.ird.driver.observe.business.referential.ps.logbook.WellContentStatus;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.DaoSupplier;
import fr.ird.driver.observe.dao.data.AbstractDataDao;
import fr.ird.driver.observe.dao.referential.ReferentialCache;
import io.ultreia.java4all.lang.Strings;
import io.ultreia.java4all.util.Dates;
import io.ultreia.java4all.util.TimeLog;
import io.ultreia.java4all.util.sql.SqlQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TripDao extends AbstractDataDao<Trip> {
    private static final Logger log = LogManager.getLogger(TripDao.class);
    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " startDate,\n" +
            /* 07 */ " endDate,\n" +
            /* 08 */ " formsUrl,\n" +
            /* 09 */ " reportsUrl,\n" +
            /* 10 */ " historicalData,\n" +
            /* 11 */ " ersId,\n" +
            /* 12 */ " timeAtSea,\n" +
            /* 13 */ " fishingTime,\n" +
            /* 14 */ " landingTotalWeight,\n" +
            /* 15 */ " localMarketTotalWeight,\n" +
            /* 16 */ " loch,\n" +
            /* 17 */ " generalComment,\n" +
            /* 18 */ " logbookComment,\n" +
            /* 19 */ " ocean,\n" +
            /* 20 */ " vessel,\n" +
            /* 21 */ " logbookProgram,\n" +
            /* 22 */ " observer,\n" +
            /* 23 */ " captain,\n" +
            /* 24 */ " logbookDataEntryOperator,\n" +
            /* 25 */ " departureHarbour,\n" +
            /* 26 */ " landingHarbour,\n" +
            /* 27 */ " logbookDataQuality,\n" +
            /* 28 */ " departureWellContentStatus,\n" +
            /* 29 */ " landingWellContentStatus,\n" +
            /* 30 */ " logbookAcquisitionStatus,\n" +
            /* 31 */ " targetWellsSamplingAcquisitionStatus,\n" +
            /* 32 */ " landingAcquisitionStatus,\n" +
            /* 33 */ " localMarketAcquisitionStatus,\n" +
            /* 34 */ " localMarketWellsSamplingAcquisitionStatus,\n" +
            /* 35 */ " localMarketSurveySamplingAcquisitionStatus\n" +
            " FROM ps_common.Trip main WHERE ";

    public TripDao() {
        super(Trip.class, Trip::new, QUERY, WHERE_BY_ID_CLAUSE);
    }

    @Override
    public Trip findById(String id) {
        long t0 = TimeLog.getTime();
        Trip result = super.findById(id);
        log.info("Trip ({}) loaded (duration: {})", id, Strings.convertTime(TimeLog.getTime() - t0));
        return result;
    }

    public List<Trip> findTrips(List<Vessel> vessels, List<Country> countries, Date start, Date end) throws ObserveDriverException {
        long t0 = TimeLog.getTime();
        List<Trip> result = new ArrayList<>();
        List<Vessel> vesselsForCountries = new ArrayList<>();
        if (countries != null && !countries.isEmpty()) {
            //FIXME on FlagCountry or FleetCountry ?
            vesselsForCountries = referentialCache().getSome(Vessel.class, v -> countries.contains(v.getFlagCountry()));
        }
        if (vessels == null || vessels.isEmpty()) {
            vessels = referentialCache().getAll(Vessel.class);
        }
        if (!vessels.isEmpty() && !vesselsForCountries.isEmpty()) {
            vessels.retainAll(vesselsForCountries);
        }
        for (Vessel b : vessels) {
            result.addAll(findTrips(b, start, end));
        }
        log.info("{} trip(s) loaded (duration: {})", result.size(), Strings.convertTime(TimeLog.getTime() - t0));
        return result;
    }

    public List<Trip> findTrips(Vessel vessel, Date start, Date end) throws ObserveDriverException {
        SqlQuery<Trip> query = SqlQuery.wrap(c -> {
            PreparedStatement statement = c.prepareStatement(queryBase + "main.vessel = ? AND main.endDate BETWEEN ? AND ? ORDER BY main.endDate");
            statement.setString(1, vessel.getTopiaId());
            statement.setDate(2, new java.sql.Date(start == null ? Dates.createDate(1900, 1, 1).getTime() : start.getTime()));
            statement.setDate(3, new java.sql.Date(end == null ? new Date().getTime() : end.getTime()));
            return statement;
        }, this::load);
        return findList(query);
    }

    public long count() {
        SqlQuery<Long> query = SqlQuery.wrap("SELECT COUNT(*) FROM ps_common.Trip", rs -> rs.getLong(1));
        return count(query);
    }

    public Date lastLandingDate() {
        SqlQuery<Date> query = SqlQuery.wrap("SELECT DISTINCT(main.endDate) FROM ps_common.Trip main ORDER BY main.endDate DESC", rs -> rs.getDate(1));
        return findFirstOrNull(query);
    }

    public Date firstLandingDate() {
        SqlQuery<Date> query = SqlQuery.wrap("SELECT DISTINCT(main.endDate) FROM ps_common.Trip main ORDER BY main.endDate ASC", rs -> rs.getDate(1));
        return findFirstOrNull(query);
    }

    /**
     * Find the previous trip of a vessel for the specified landing date.
     *
     * @param vessel      the query vessel
     * @param landingDate the query landing date
     * @return the previous trip or null
     * @throws ObserveDriverException if any error
     */
    public Trip findPreviousTrip(Vessel vessel, Date landingDate) throws ObserveDriverException {
        SqlQuery<Trip> query = SqlQuery.wrap(c -> {
            PreparedStatement statement = c.prepareStatement(queryBase + "main.vessel = ? AND main.endDate < ? ORDER BY main.endDate DESC");
            statement.setString(1, Objects.requireNonNull(vessel).getTopiaId());
            statement.setDate(2, new java.sql.Date(Objects.requireNonNull(landingDate).getTime()));
            return statement;
        }, this::load);
        return findFirstOrNull(query);
    }

    @Override
    protected void fill(Trip result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setStartDate(rs.getDate(6));
        result.setEndDate(rs.getDate(7));
        result.setFormsUrl(rs.getString(8));
        result.setReportsUrl(rs.getString(9));
        result.setHistoricalData(rs.getBoolean(10));
        result.setErsId(rs.getString(11));
        result.setTimeAtSea(rs.getInt(12));
        result.setFishingTime(rs.getInt(13));
        result.setLandingTotalWeight(rs.getFloat(14));
        result.setLocalMarketTotalWeight(rs.getFloat(15));
        result.setLoch(rs.getInt(16));
        result.setGeneralComment(rs.getString(17));
        result.setLogbookComment(rs.getString(18));

        ReferentialCache referentialCache = referentialCache();
        result.setOcean(referentialCache.lazyReferential(Ocean.class, rs.getString(19)));
        result.setVessel(referentialCache.lazyReferential(Vessel.class, rs.getString(20)));
        result.setLogbookProgram(referentialCache.lazyReferential(Program.class, rs.getString(21)));
        result.setObserver(referentialCache.lazyReferential(Person.class, rs.getString(22)));
        result.setCaptain(referentialCache.lazyReferential(Person.class, rs.getString(23)));
        result.setLogbookDataEntryOperator(referentialCache.lazyReferential(Person.class, rs.getString(24)));
        result.setDepartureHarbour(referentialCache.lazyReferential(Harbour.class, rs.getString(25)));
        result.setLandingHarbour(referentialCache.lazyReferential(Harbour.class, rs.getString(26)));
        result.setLogbookDataQuality(referentialCache.lazyReferential(DataQuality.class, rs.getString(27)));
        result.setDepartureWellContentStatus(referentialCache.lazyReferential(WellContentStatus.class, rs.getString(28)));
        result.setLandingWellContentStatus(referentialCache.lazyReferential(WellContentStatus.class, rs.getString(29)));
        result.setLogbookAcquisitionStatus(referentialCache.lazyReferential(AcquisitionStatus.class, rs.getString(30)));
        result.setTargetWellsSamplingAcquisitionStatus(referentialCache.lazyReferential(AcquisitionStatus.class, rs.getString(31)));
        result.setLandingAcquisitionStatus(referentialCache.lazyReferential(AcquisitionStatus.class, rs.getString(32)));
        result.setLocalMarketAcquisitionStatus(referentialCache.lazyReferential(AcquisitionStatus.class, rs.getString(33)));
        result.setLocalMarketWellsSamplingAcquisitionStatus(referentialCache.lazyReferential(AcquisitionStatus.class, rs.getString(34)));
        result.setLocalMarketSurveySamplingAcquisitionStatus(referentialCache.lazyReferential(AcquisitionStatus.class, rs.getString(35)));

        DaoSupplier daoSupplier = daoSupplier();
        String tripId = result.getTopiaId();
        result.setLogbookRoute(daoSupplier.getPsLogbookRouteDao().lazySetByParentId(tripId));
        result.setWell(daoSupplier.getPsLogbookWellDao().lazySetByParentId(tripId));
        result.setLogbookSample(daoSupplier.getPsLogbookSampleDao().lazySetByParentId(tripId));
        result.setLanding(daoSupplier.getPsLandingLandingDao().lazyListByParentId(tripId));
        result.setLocalmarketBatch(daoSupplier.getPsLocalmarketBatchDao().lazySetByParentId(tripId));
        result.setLocalmarketSurvey(daoSupplier.getPsLocalmarketSurveyDao().lazySetByParentId(tripId));
        result.setLocalmarketSample(daoSupplier.getPsLocalmarketSampleDao().lazySetByParentId(tripId));
    }

}
