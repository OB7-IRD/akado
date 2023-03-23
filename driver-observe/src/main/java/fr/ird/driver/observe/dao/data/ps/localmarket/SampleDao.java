package fr.ird.driver.observe.dao.data.ps.localmarket;

import fr.ird.driver.observe.business.data.ps.localmarket.Sample;
import fr.ird.driver.observe.business.referential.ps.common.SampleType;
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
public class SampleDao extends AbstractDataDao<Sample> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " number,\n" +
            /* 07 */ " date,\n" +
            /* 08 */ " comment,\n" +
            /* 09 */ " sampleType\n" +
            " FROM ps_localmarket.Sample main WHERE ";

    private static final String BY_PARENT = "main.trip = ? ORDER BY main.number";

    public SampleDao() {
        super(Sample.class, Sample::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(Sample result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setNumber(rs.getString(6));
        result.setDate(rs.getDate(7));
        result.setComment(rs.getString(8));
        ReferentialCache referentialCache = referentialCache();
        result.setSampleType(referentialCache.lazyReferential(SampleType.class, rs.getString(9)));
        String sampleId = result.getTopiaId();
        result.setSampleSpecies(daoSupplier().getPsLocalmarketSampleSpeciesDao().lazySetByParentId(sampleId));
        result.setWell(lazyStringSet(sampleId, "ps_localmarket", "Sample_well", "sample", "well"));
    }
}
