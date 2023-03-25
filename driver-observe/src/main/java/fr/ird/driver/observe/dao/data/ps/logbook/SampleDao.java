package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.referential.ps.common.SampleType;
import fr.ird.driver.observe.business.referential.ps.logbook.SampleQuality;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.DaoSupplier;
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
public class SampleDao extends AbstractDataDao<Sample> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " number,\n" +
            /* 07 */ " comment,\n" +
            /* 08 */ " well,\n" +
            /* 09 */ " superSample,\n" +
            /* 10 */ " smallsWeight,\n" +
            /* 11 */ " bigsWeight,\n" +
            /* 12 */ " totalWeight,\n" +
            /* 13 */ " sampleType,\n" +
            /* 14 */ " sampleQuality\n" +
            " FROM ps_logbook.Sample main WHERE ";
    private static final String BY_PARENT = "main.trip = ? ORDER BY main.number, main.well";

    public SampleDao() {
        super(Sample.class, Sample::new, QUERY, BY_PARENT);
    }

    public long count() {
        SqlQuery<Long> query = SqlQuery.wrap("SELECT COUNT(*) FROM ps_logbook.Sample", rs -> rs.getLong(1));
        return count(query);
    }

    @Override
    protected void fill(Sample result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setNumber(getInteger(rs, 6));
        result.setComment(rs.getString(7));
        result.setWell(rs.getString(8));
        result.setSuperSample(rs.getBoolean(9));
        result.setSmallsWeight(getFloat(rs, 10));
        result.setBigsWeight(getFloat(rs, 11));
        result.setTotalWeight(getFloat(rs, 12));
        ReferentialCache referentialCache = referentialCache();
        result.setSampleType(referentialCache.lazyReferential(SampleType.class, rs.getString(13)));
        result.setSampleQuality(referentialCache.lazyReferential(SampleQuality.class, rs.getString(14)));
        DaoSupplier daoSupplier = daoSupplier();
        String sampleId = result.getTopiaId();
        result.setSampleActivity(daoSupplier.getPsLogbookSampleActivityDao().lazySetByParentId(sampleId));
        result.setSampleSpecies(daoSupplier.getPsLogbookSampleSpeciesDao().lazyListByParentId(sampleId));
    }
}
