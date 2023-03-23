package fr.ird.driver.observe.dao.data.ps.localmarket;

import fr.ird.driver.observe.business.data.ps.localmarket.SampleSpecies;
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
            /* 07 */ " measuredCount,\n" +
            /* 08 */ " species,\n" +
            /* 09 */ " sizeMeasureType\n" +
            " FROM ps_localmarket.SampleSpecies main WHERE ";
    private static final String BY_PARENT = "main.sample = ? ORDER BY main.topiaCreateDate";

    public SampleSpeciesDao() {
        super(SampleSpecies.class, SampleSpecies::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(SampleSpecies result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setMeasuredCount(getInteger(rs, 7));
        ReferentialCache referentialCache = referentialCache();
        result.setSpecies(referentialCache.lazyReferential(Species.class, rs.getString(8)));
        result.setSizeMeasureType(referentialCache.lazyReferential(SizeMeasureType.class, rs.getString(9)));
        result.setSampleSpeciesMeasure(daoSupplier().getPsLocalmarketSampleSpeciesMeasureDao().lazySetByParentId(result.getTopiaId()));
    }
}
