package fr.ird.driver.observe.dao.data.ps.localmarket;

import fr.ird.driver.observe.business.data.ps.localmarket.Batch;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.ps.localmarket.Buyer;
import fr.ird.driver.observe.business.referential.ps.localmarket.Packaging;
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
public class BatchDao extends AbstractDataDao<Batch> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " date,\n" +
            /* 07 */ " count,\n" +
            /* 08 */ " weight,\n" +
            /* 09 */ " weightComputedSource,\n" +
            /* 10 */ " origin,\n" +
            /* 11 */ " comment,\n" +
            /* 12 */ " species,\n" +
            /* 13 */ " packaging,\n" +
            /* 14 */ " buyer,\n" +
            /* 15 */ " survey\n" +
            " FROM ps_localmarket.Batch main WHERE ";
    private static final String BY_PARENT = "main.trip = ? ORDER BY main.topiaCreateDate";

    public BatchDao() {
        super(Batch.class, Batch::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(Batch result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setDate(rs.getDate(6));
        result.setCount(getInteger(rs, 7));
        result.setWeight(getFloat(rs, 8));
        result.setWeightComputedSource(rs.getString(9));
        result.setOrigin(rs.getString(10));
        result.setComment(rs.getString(11));
        ReferentialCache referentialCache = referentialCache();
        result.setSpecies(referentialCache.lazyReferential(Species.class, rs.getString(12)));
        result.setPackaging(referentialCache.lazyReferential(Packaging.class, rs.getString(13)));
        result.setBuyer(referentialCache.lazyReferential(Buyer.class, rs.getString(14)));
        result.setSurvey(daoSupplier().getPsLocalmarketSurveyDao().lazyFindById(rs.getString(15)));
    }
}
