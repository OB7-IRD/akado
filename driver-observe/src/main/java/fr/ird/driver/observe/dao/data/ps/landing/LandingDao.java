package fr.ird.driver.observe.dao.data.ps.landing;

import fr.ird.driver.observe.business.data.ps.landing.Landing;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.ps.common.WeightCategory;
import fr.ird.driver.observe.business.referential.ps.landing.Destination;
import fr.ird.driver.observe.business.referential.ps.landing.Fate;
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
public class LandingDao extends AbstractDataDao<Landing> {
    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " date,\n" +
            /* 07 */ " weight,\n" +
            /* 08 */ " species,\n" +
            /* 09 */ " weightCategory,\n" +
            /* 10 */ " destination,\n" +
            /* 11 */ " fate,\n" +
            /* 12 */ " fateVessel\n" +
            " FROM ps_landing.Landing main WHERE ";
    private static final String BY_PARENT = "main.trip = ? ORDER BY main.trip_idx";

    public LandingDao() {
        super(Landing.class, Landing::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(Landing result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setDate(rs.getDate(6));
        result.setWeight(getFloat(rs, 7));
        ReferentialCache referentialCache = referentialCache();
        result.setSpecies(referentialCache.lazyReferential(Species.class, rs.getString(8)));
        result.setWeightCategory(referentialCache.lazyReferential(WeightCategory.class, rs.getString(9)));
        result.setDestination(referentialCache.lazyReferential(Destination.class, rs.getString(10)));
        result.setFate(referentialCache.lazyReferential(Fate.class, rs.getString(11)));
        result.setFateVessel(referentialCache.lazyReferential(Vessel.class, rs.getString(12)));
    }
}
