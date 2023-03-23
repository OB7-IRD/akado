package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.Well;
import fr.ird.driver.observe.business.referential.ps.logbook.WellSamplingConformity;
import fr.ird.driver.observe.business.referential.ps.logbook.WellSamplingStatus;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.data.AbstractDataDao;
import fr.ird.driver.observe.dao.referential.ReferentialCache;
import io.ultreia.java4all.util.sql.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellDao extends AbstractDataDao<Well> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " well,\n" +
            /* 07 */ " wellVessel,\n" +
            /* 08 */ " wellFactory,\n" +
            /* 09 */ " wellSamplingConformity,\n" +
            /* 10 */ " wellSamplingStatus\n" +
            " FROM ps_logbook.Well main WHERE ";
    private static final String BY_PARENT = "main.trip = ? ORDER BY main.well";

    public WellDao() {
        super(Well.class, Well::new, QUERY, BY_PARENT);
    }

    public long count() {
        SqlQuery<Long> query = SqlQuery.wrap("SELECT COUNT(*) FROM ps_logbook.Well", rs -> rs.getLong(1));
        return count(query);
    }

    @Override
    protected void fill(Well result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setWell(rs.getString(6));
        result.setWellVessel(rs.getString(7));
        result.setWellFactory(rs.getString(8));
        ReferentialCache referentialCache = referentialCache();
        result.setWellSamplingConformity(referentialCache.lazyReferential(WellSamplingConformity.class, rs.getString(9)));
        result.setWellSamplingStatus(referentialCache.lazyReferential(WellSamplingStatus.class, rs.getString(10)));
        result.setWellActivity(daoSupplier().getPsLogbookWellActivityDao().lazySetByParentId(result.getTopiaId()));
    }
}
