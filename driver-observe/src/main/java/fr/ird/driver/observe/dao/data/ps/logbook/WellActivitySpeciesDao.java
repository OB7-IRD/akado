package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.WellActivitySpecies;
import fr.ird.driver.observe.business.referential.common.Species;
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
public class WellActivitySpeciesDao extends AbstractDataDao<WellActivitySpecies> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " weight,\n" +
            /* 07 */ " count,\n" +
            /* 08 */ " setSpeciesNumber,\n" +
            /* 09 */ " species,\n" +
            /* 10 */ " weightCategory\n" +
            " FROM ps_logbook.WellActivitySpecies main WHERE ";
    private static final String BY_PARENT = "main.wellActivity = ? ORDER BY wellActivity_idx";

    public WellActivitySpeciesDao() {
        super(WellActivitySpecies.class, WellActivitySpecies::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(WellActivitySpecies result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setWeight(rs.getDouble(6));
        result.setCount(rs.getInt(7));
        result.setSetSpeciesNumber(rs.getInt(8));
        ReferentialCache referentialCache = referentialCache();
        result.setSpecies(referentialCache.lazyReferential(Species.class, rs.getString(9)));
        result.setWeightCategory(referentialCache.lazyReferential(WeightCategory.class, rs.getString(10)));
    }
}
