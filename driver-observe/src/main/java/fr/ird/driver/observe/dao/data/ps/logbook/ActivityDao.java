package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.common.DataQuality;
import fr.ird.driver.observe.business.referential.common.FpaZone;
import fr.ird.driver.observe.business.referential.common.Wind;
import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import fr.ird.driver.observe.business.referential.ps.common.ReasonForNoFishing;
import fr.ird.driver.observe.business.referential.ps.common.ReasonForNullSet;
import fr.ird.driver.observe.business.referential.ps.common.SchoolType;
import fr.ird.driver.observe.business.referential.ps.common.VesselActivity;
import fr.ird.driver.observe.business.referential.ps.logbook.InformationSource;
import fr.ird.driver.observe.business.referential.ps.logbook.SetSuccessStatus;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.DaoSupplier;
import fr.ird.driver.observe.dao.data.AbstractDataDao;
import fr.ird.driver.observe.dao.referential.ReferentialCache;
import io.ultreia.java4all.util.sql.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ActivityDao extends AbstractDataDao<Activity> {
    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " comment,\n" +
            /* 07 */ " time,\n" +
            /* 08 */ " latitude,\n" +
            /* 09 */ " longitude,\n" +
            /* 10 */ " latitudeOriginal,\n" +
            /* 11 */ " longitudeOriginal,\n" +
            /* 12 */ " originalDataModified,\n" +
            /* 13 */ " vmsDivergent,\n" +
            /* 14 */ " positionCorrected,\n" +
            /* 15 */ " number,\n" +
            /* 16 */ " setCount,\n" +
            /* 17 */ " seaSurfaceTemperature,\n" +
            /* 18 */ " windDirection,\n" +
            /* 19 */ " totalWeight,\n" +
            /* 20 */ " currentSpeed,\n" +
            /* 21 */ " currentDirection,\n" +
            /* 22 */ " vesselActivity,\n" +
            /* 23 */ " wind,\n" +
            /* 24 */ " schoolType,\n" +
            /* 25 */ " fpaZone,\n" +
            /* 26 */ " dataQuality,\n" +
            /* 27 */ " informationSource,\n" +
            /* 28 */ " reasonForNoFishing,\n" +
            /* 29 */ " setSuccessStatus,\n" +
            /* 30 */ " reasonForNullSet\n" +
            " FROM ps_logbook.Activity main WHERE ";

    private static final String BY_PARENT = "main.route = ? ORDER BY main.number";

    public ActivityDao() {
        super(Activity.class, Activity::new, QUERY, BY_PARENT);
    }

    public long count() {
        SqlQuery<Long> query = SqlQuery.wrap("SELECT COUNT(*) FROM ps_logbook.Activity", rs -> rs.getLong(1));
        return count(query);
    }

    @Override
    protected void fill(Activity result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setTime(rs.getTime(7));
        result.setLatitude(getFloat(rs, 8));
        result.setLongitude(getFloat(rs, 9));
        result.setLatitudeOriginal(getFloat(rs, 10));
        result.setLongitudeOriginal(getFloat(rs, 11));
        result.setOriginalDataModified(rs.getBoolean(12));
        result.setVmsDivergent(rs.getBoolean(13));
        result.setPositionCorrected(rs.getBoolean(14));
        result.setNumber(rs.getInt(15));
        result.setSetCount(rs.getInt(16));
        result.setSeaSurfaceTemperature(rs.getFloat(17));
        result.setWindDirection(rs.getInt(18));
        result.setTotalWeight(rs.getFloat(19));
        result.setCurrentSpeed(rs.getFloat(20));
        result.setCurrentDirection(rs.getInt(21));
        ReferentialCache referentialCache = referentialCache();
        result.setVesselActivity(referentialCache.lazyReferential(VesselActivity.class, rs.getString(22)));
        result.setWind(referentialCache.lazyReferential(Wind.class, rs.getString(23)));
        result.setSchoolType(referentialCache.lazyReferential(SchoolType.class, rs.getString(24)));
        result.setFpaZone(referentialCache.lazyReferential(FpaZone.class, rs.getString(25)));
        result.setDataQuality(referentialCache.lazyReferential(DataQuality.class, rs.getString(26)));
        result.setInformationSource(referentialCache.lazyReferential(InformationSource.class, rs.getString(27)));
        result.setReasonForNoFishing(referentialCache.lazyReferential(ReasonForNoFishing.class, rs.getString(28)));
        result.setSetSuccessStatus(referentialCache.lazyReferential(SetSuccessStatus.class, rs.getString(29)));
        result.setReasonForNullSet(referentialCache.lazyReferential(ReasonForNullSet.class, rs.getString(30)));
        DaoSupplier daoSupplier = daoSupplier();
        String activityId = result.getTopiaId();
        result.setCatches(daoSupplier.getPsLogbookCatchDao().lazyListByParentId(activityId));
        result.setFloatingObject(daoSupplier.getPsLogbookFloatingObjectDao().lazySetByParentId(activityId));
        result.setObservedSystem(lazyReferentialSet(activityId, "ps_logbook", "activity_observedSystem", "activity", "observedSystem", ObservedSystem.class));
    }

    public Date firstActivityDate() {
        SqlQuery<Date> query = SqlQuery.wrap("SELECT DISTINCT(r.date) FROM ps_logbook.Route r WHERE (SELECT count(*) FROM ps_logbook.Activity a WHERE a.route = r.topiaId) > 0 ORDER BY r.date ASC", rs -> rs.getDate(1));
        return findFirstOrNull(query);

    }

    public Date lastActivityDate() {
        SqlQuery<Date> query = SqlQuery.wrap("SELECT DISTINCT(r.date) FROM ps_logbook.Route r WHERE (SELECT count(*) FROM ps_logbook.Activity a WHERE a.route = r.topiaId) > 0 ORDER BY r.date DESC", rs -> rs.getDate(1));
        return findFirstOrNull(query);
    }
}
