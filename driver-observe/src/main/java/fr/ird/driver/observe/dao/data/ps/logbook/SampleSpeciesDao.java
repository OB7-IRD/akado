package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.data.AbstractDataDao;
import fr.ird.driver.observe.dao.referential.ReferentialCache;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleSpeciesDao extends AbstractDataDao<SampleSpecies> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " comment,\n" +
            /* 07 */ " startTime,\n" +
            /* 08 */ " endTime,\n" +
            /* 09 */ " subSampleNumber,\n" +
            /* 10 */ " measuredCount,\n" +
            /* 11 */ " totalCount,\n" +
            /* 12 */ " species,\n" +
            /* 13 */ " sizeMeasureType\n" +
            " FROM ps_logbook.SampleSpecies main WHERE ";
    private static final String BY_PARENT = "main.sample = ? ORDER BY main.sample_idx";

    public SampleSpeciesDao() {
        super(SampleSpecies.class, SampleSpecies::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(SampleSpecies result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setStartTime(rs.getTime(7));
        result.setEndTime(rs.getTime(8));
        result.setSubSampleNumber(rs.getInt(9));
        result.setMeasuredCount(rs.getInt(10));
        result.setTotalCount(rs.getInt(11));
        ReferentialCache referentialCache = referentialCache();
        result.setSpecies(referentialCache.lazyReferential(Species.class, rs.getString(12)));
        result.setSizeMeasureType(referentialCache.lazyReferential(SizeMeasureType.class, rs.getString(13)));
        result.setSampleSpeciesMeasure(daoSupplier().getPsLogbookSampleSpeciesMeasureDao().lazySetByParentId(result.getTopiaId()));
    }
}
