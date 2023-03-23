package fr.ird.driver.observe.dao.referential.ps.localmarket;

import fr.ird.driver.observe.business.referential.common.Harbour;
import fr.ird.driver.observe.business.referential.ps.localmarket.BatchComposition;
import fr.ird.driver.observe.business.referential.ps.localmarket.BatchWeightType;
import fr.ird.driver.observe.business.referential.ps.localmarket.Packaging;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;
import fr.ird.driver.observe.dao.referential.ReferentialCache;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class PackagingDao extends AbstractI18nReferentialDao<Packaging> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " startDate,\n" +
            /* 19 */ " endDate,\n" +
            /* 20 */ " meanWeight,\n" +
            /* 21 */ " batchComposition,\n" +
            /* 22 */ " batchWeightType,\n" +
            /* 23 */ " harbour\n" +
            " FROM ps_localmarket.Packaging";

    public PackagingDao() {
        super(Packaging.class, QUERY, Packaging::new);
    }

    @Override
    protected void fill(Packaging result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setStartDate(rs.getDate(18));
        result.setEndDate(rs.getDate(19));
        result.setMeanWeight(getFloat(rs, 20));
        ReferentialCache referentialCache = referentialCache();
        result.setBatchComposition(referentialCache.lazyReferential(BatchComposition.class, rs.getString(21)));
        result.setBatchWeightType(referentialCache.lazyReferential(BatchWeightType.class, rs.getString(22)));
        result.setHarbour(referentialCache.lazyReferential(Harbour.class, rs.getString(23)));
    }
}
