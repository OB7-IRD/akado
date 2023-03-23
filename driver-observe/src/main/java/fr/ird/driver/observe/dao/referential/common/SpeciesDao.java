package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Ocean;
import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.common.SpeciesGroup;
import fr.ird.driver.observe.business.referential.common.WeightMeasureType;
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
public class SpeciesDao extends AbstractI18nReferentialDao<Species> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " faoCode,\n" +
            /* 19 */ " scientificLabel,\n" +
            /* 20 */ " wormsId,\n" +
            /* 21 */ " minLength,\n" +
            /* 22 */ " maxLength,\n" +
            /* 23 */ " minWeight,\n" +
            /* 24 */ " maxWeight,\n" +
            /* 25 */ " speciesGroup,\n" +
            /* 26 */ " sizeMeasureType,\n" +
            /* 27 */ " weightMeasureType\n" +
            " FROM common.Species";

    public SpeciesDao() {
        super(Species.class, QUERY, Species::new);
    }

    @Override
    protected void fill(Species result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setFaoCode(rs.getString(18));
        result.setScientificLabel(rs.getString(19));
        result.setWormsId(rs.getLong(20));
        result.setMinLength(getFloat(rs, 21));
        result.setMaxLength(getFloat(rs, 22));
        result.setMinWeight(getFloat(rs, 23));
        result.setMaxWeight(getFloat(rs, 24));
        ReferentialCache referentialCache = referentialCache();
        result.setSpeciesGroup(referentialCache.lazyReferential(SpeciesGroup.class, rs.getString(25)));
        result.setSizeMeasureType(referentialCache.lazyReferential(SizeMeasureType.class, rs.getString(26)));
        result.setWeightMeasureType(referentialCache.lazyReferential(WeightMeasureType.class, rs.getString(27)));
        result.setOcean(lazyReferentialSet(result.getTopiaId(), "common", "species_ocean", "species", "ocean", Ocean.class));
    }
}
