package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.ps.common.WeightCategory;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WeightCategoryDao extends AbstractI18nReferentialDao<WeightCategory> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " minWeight,\n" +
            /* 19 */ " maxWeight,\n" +
            /* 20 */ " meanWeight,\n" +
            /* 21 */ " landing,\n" +
            /* 22 */ " logbook,\n" +
            /* 23 */ " wellPlan,\n" +
            /* 24 */ " species\n" +
            " FROM ps_common.WeightCategory";

    public WeightCategoryDao() {
        super(WeightCategory.class, QUERY, WeightCategory::new);
    }

    @Override
    protected void fill(WeightCategory result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setMinWeight(getFloat(rs, 18));
        result.setMaxWeight(getFloat(rs, 19));
        result.setMeanWeight(getFloat(rs, 20));
        result.setAllowLanding(rs.getBoolean(21));
        result.setAllowLogbook(rs.getBoolean(22));
        result.setAllowWellPlan(rs.getBoolean(23));
        result.setSpecies(referentialCache().lazyReferential(Species.class, rs.getString(24)));
    }
}
