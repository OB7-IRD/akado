package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.Catch;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.common.WeightMeasureMethod;
import fr.ird.driver.observe.business.referential.ps.common.SpeciesFate;
import fr.ird.driver.observe.business.referential.ps.common.WeightCategory;
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
public class CatchDao extends AbstractDataDao<Catch> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " comment,\n" +
            /* 07 */ " weight,\n" +
            /* 08 */ " count,\n" +
            /* 09 */ " well,\n" +
            /* 10 */ " species,\n" +
            /* 11 */ " weightCategory,\n" +
            /* 12 */ " speciesFate,\n" +
            /* 13 */ " weightMeasureMethod\n" +
            " FROM ps_logbook.Catch main WHERE ";
    private static final String BY_PARENT = "main.activity = ? ORDER BY main.activity_idx";

    public CatchDao() {
        super(Catch.class, Catch::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(Catch result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setWeight(rs.getFloat(7));
        result.setCount(rs.getInt(8));
        result.setWell(rs.getString(9));
        ReferentialCache referentialCache = referentialCache();
        result.setSpecies(referentialCache.lazyReferential(Species.class, rs.getString(10)));
        result.setWeightCategory(referentialCache.lazyReferential(WeightCategory.class, rs.getString(11)));
        result.setSpeciesFate(referentialCache.lazyReferential(SpeciesFate.class, rs.getString(12)));
        result.setWeightMeasureMethod(referentialCache.lazyReferential(WeightMeasureMethod.class, rs.getString(13)));
    }
}
